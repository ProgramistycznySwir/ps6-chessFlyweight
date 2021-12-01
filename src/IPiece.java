import java.awt.Graphics2D;

interface IPiece {
    public static final int TILESIZE = 32;
    
    public int getIndex();
    public IPiece getDecorated();
    public void draw(Graphics2D paramGraphics2D);
    public void moveTo(int paramInt1, int paramInt2);
}
