import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

class Chessboard extends JPanel {
    public static final int ZEROX = 23;
    public static final int ZEROY = 7;
    public PieceFactory pieces;
    private Image image;
    
    public void drop(IPiece piece, int x, int y) {
        repaint();
        pieces.OverridePosition(new Point(x, y), piece);
        // this.board.put(new Point(x, y), piece);
    }
    
    public IPiece take(int x, int y) {
        clickPos = new Point(x, y);
        repaint();
        // return this.board.remove(new Point(x, y));
        return pieces.Remove(new Point(x, y));
    }
    
    private IPiece dragged = null;
    AffineTransform draggedTransform = null;
    private Point mouse = null;
    public static Point current = null;
    public static Point clickPos = null;
    
    public void paint(Graphics graphics) {
        graphics.drawImage(this.image, 0, 0, null);

        for (Entry<Point, IPiece> localEntry : pieces.GetAll()) {
            Point localPoint = localEntry.getKey();
            IPiece localIPiece = localEntry.getValue();
            current = localPoint;
            localIPiece.draw((Graphics2D)graphics);
        }

        if ((mouse != null) && (dragged != null)) {
            current = clickPos;
            // pieces.Draw(dragged, (Graphics2D)graphics);
        }
    }
    
    Chessboard() {
        AffineTransform localAffineTransform = new AffineTransform();
        localAffineTransform.translate(23.0D, 7.0D);
        localAffineTransform.scale(32.0D, 32.0D);

        pieces = new PieceFactory();

        pieces.Create(new Point(0, 2), 11, localAffineTransform);
        pieces.Create(new Point(0, 6), 0,  localAffineTransform);
        pieces.Create(new Point(1, 4), 6,  localAffineTransform);
        pieces.Create(new Point(1, 5), 5,  localAffineTransform);
        pieces.Create(new Point(3, 7), 1,  localAffineTransform);
        pieces.Create(new Point(4, 3), 6,  localAffineTransform);
        pieces.Create(new Point(4, 4), 7,  localAffineTransform);
        pieces.Create(new Point(5, 4), 6,  localAffineTransform);
        pieces.Create(new Point(5, 6), 0,  localAffineTransform);
        pieces.Create(new Point(6, 5), 0,  localAffineTransform);
        pieces.Create(new Point(7, 4), 0,  localAffineTransform);
        

        try {
            image = new ImageIcon("board3.png").getImage();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent paramAnonymousMouseEvent) {
                dragged = take(
                        (paramAnonymousMouseEvent.getX() - 23) / 32,
                        (paramAnonymousMouseEvent.getY() - 7) / 32);
                dragged = new AffineDecorator(dragged, new AffineTransform());
                mouse = paramAnonymousMouseEvent.getPoint();
            }
            public void mouseReleased(MouseEvent paramAnonymousMouseEvent) {
                drop(
                        Chessboard.this.dragged.getDecorated(),
                        (paramAnonymousMouseEvent.getX() - 23) / 32,
                        (paramAnonymousMouseEvent.getY() - 7) / 32);
                dragged = null;
                undo.setEnabled(true);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent paramAnonymousMouseEvent) {
                draggedTransform.setToTranslation(paramAnonymousMouseEvent.getX() - mouse.getX(), paramAnonymousMouseEvent.getY() - mouse.getY());                
                repaint();
            }
        });
    }

    JButton undo, redo;
    class UndoButton implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			System.out.println("UNDO");
			redo.setEnabled(true);
		}
	}
	class RedoButton implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			System.out.println("REDO");
		}
	}
    
    public static void main(String[] paramArrayOfString) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Chessboard board = new Chessboard();
        
        JToolBar bar = new JToolBar();
        try {
            board.undo = new JButton(new ImageIcon("undo.png"));
            board.redo = new JButton(new ImageIcon("redo.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        board.undo.addActionListener(board.new UndoButton());
        board.redo.addActionListener(board.new RedoButton());
        board.undo.setEnabled(false);
        board.redo.setEnabled(false);
        bar.add(board.undo);
        bar.add(board.redo);
        
        frame.add(bar, BorderLayout.PAGE_START);
        frame.add(board);
        
        frame.pack();
        frame.setVisible(true);
    }
}
