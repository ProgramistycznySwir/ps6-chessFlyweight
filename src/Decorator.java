import java.awt.Graphics2D;

class Decorator implements IPiece
{
    protected final IPiece piece;
    
    public int getIndex() {
        return this.piece.getIndex();
    }
    
    public IPiece getDecorated() {
        return this.piece;
    }
    
    protected Decorator(IPiece paramIPiece) {
        this.piece = paramIPiece;
    }
    
    public void draw(Graphics2D paramGraphics2D) {
        this.piece.draw(paramGraphics2D);
    }
    
    public void moveTo(int paramInt1, int paramInt2) {
        this.piece.moveTo(paramInt1, paramInt2);
    }
}
