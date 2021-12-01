import java.awt.Graphics2D;
import java.awt.Point;

interface IPiece {
    public static final int TILESIZE = 32;
    
    public int getIndex();
    public IPiece getDecorated();
    public void draw(Graphics2D paramGraphics2D, Point point);
    public void moveTo(int paramInt1, int paramInt2);
}
