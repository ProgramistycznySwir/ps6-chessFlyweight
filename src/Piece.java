import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

class Piece implements IPiece {
    private static final Image image = new ImageIcon("pieces4.png").getImage();
    private int index;
    
    public int getIndex() {
        return this.index;
    }
    
    public Piece(int paramInt1) {
        this.index = paramInt1;
    }
    
    public void draw(Graphics2D paramGraphics2D, Point point) {
        paramGraphics2D.drawImage(
                image,
                point.x,
                point.y,
                point.x + 1,
                point.y + 1,
                this.index * 32,
                0,
                (this.index + 1) * 32,
                32,
                null);
    }
    
    public void moveTo(int paramInt1, int paramInt2) {
        // It has to be implemented, soo, why not
    }
    
    public IPiece getDecorated() {
        return null;
    }
}
