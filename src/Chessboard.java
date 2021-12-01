import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

class Chessboard extends JPanel {
    public static final int ZEROX = 23;
    public static final int ZEROY = 7;
    public PieceFactory pieces;
    private Image image;
    
    private Point draggedFrom = null;
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

        if ((mouse != null) && (draggedFrom != null)) {
            current = clickPos;
            // pieces.Draw(dragged, (Graphics2D)graphics);
        }
    }
    
    Chessboard() {
        AffineTransform localAffineTransform = new AffineTransform();
        localAffineTransform.translate(23.0D, 7.0D);
        localAffineTransform.scale(32.0D, 32.0D);

        pieces = new PieceFactory();
        DEBUG_SeedPieces(localAffineTransform);

        try {
            image = new ImageIcon("board3.png").getImage();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                repaint();
                draggedFrom = new Point(
                        (mouseEvent.getX() - 23) / 32,
                        (mouseEvent.getY() - 7) / 32);
                mouse = mouseEvent.getPoint();
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                repaint();
                pieces.MovePiece(draggedFrom, new Point(
                        (mouseEvent.getX() - 23) / 32,
                        (mouseEvent.getY() - 7) / 32));
                draggedFrom = null;
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
    
    void DEBUG_SeedPieces(AffineTransform affineTransform) {
        pieces.Create(new Point(0, 2), 11, affineTransform);
        pieces.Create(new Point(0, 6), 0,  affineTransform);
        pieces.Create(new Point(1, 4), 6,  affineTransform);
        pieces.Create(new Point(1, 5), 5,  affineTransform);
        pieces.Create(new Point(3, 7), 1,  affineTransform);
        pieces.Create(new Point(4, 3), 6,  affineTransform);
        pieces.Create(new Point(4, 4), 7,  affineTransform);
        pieces.Create(new Point(5, 4), 6,  affineTransform);
        pieces.Create(new Point(5, 6), 0,  affineTransform);
        pieces.Create(new Point(6, 5), 0,  affineTransform);
        pieces.Create(new Point(7, 4), 0,  affineTransform);
    }
}
