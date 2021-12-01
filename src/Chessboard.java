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
    private Point mousePos = null;
    private Graphics boardGraphics;
    
    public void paint(Graphics graphics) {
        graphics.drawImage(this.image, 0, 0, null);

        pieces.DrawAll((Graphics2D)graphics);

        if ((mousePos != null) && (draggedFrom != null))
            pieces.DrawDragged(mousePos, (Graphics2D)graphics);
    }
    
    Chessboard() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(23.0D, 7.0D);
        affineTransform.scale(32.0D, 32.0D);

        pieces = new PieceFactory();
        DEBUG_SeedPieces(affineTransform);

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
                mousePos = mouseEvent.getPoint();
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
            public void mouseDragged(MouseEvent mouseEvent) {
                draggedTransform.setToTranslation(mouseEvent.getX() - mousePos.getX(), mouseEvent.getY() - mousePos.getY());
                pieces.DrawDragged(mouseEvent.getPoint(), (Graphics2D)boardGraphics);
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
