import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

public class PieceFactory {
    private HashMap<Point, IPiece> pieces = new HashMap<Point, IPiece>();

    // this.board.put(new Point(0, 2), new AffineDecorator(new Piece(11), localAffineTransform));


    public void OverridePosition(Point point, IPiece piece) {
        pieces.put(point, piece);
    }

    public IPiece Create(Point point, int index, AffineTransform affineTransform) {
        IPiece newPiece = new AffineDecorator(new Piece(index), affineTransform);
        pieces.put(point, newPiece);
        return newPiece;
    }

    public IPiece Remove(Point point) {
        return pieces.remove(point);
    }


    public IPiece get(Point point) {
        return pieces.get(point);
    }

    public void Draw(Point piecePos, Graphics2D graphics) {
        pieces.get(piecePos).draw(graphics);
    }

    // TODO: throw this out.
    public Set<Entry<Point, IPiece>> GetAll() {
        return pieces.entrySet();
    }
}
