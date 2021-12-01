import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

class AffineDecorator extends Decorator {
    private AffineTransform transform;
    
    public AffineDecorator(IPiece paramIPiece, AffineTransform paramAffineTransform) {
        super(paramIPiece);
        this.transform = paramAffineTransform;
    }
    
    public void draw(Graphics2D paramGraphics2D) {
        AffineTransform localAffineTransform = paramGraphics2D.getTransform();
        paramGraphics2D.transform(this.transform);

        this.piece.draw(paramGraphics2D);
        paramGraphics2D.setTransform(localAffineTransform);
    }
}