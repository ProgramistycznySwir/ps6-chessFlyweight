import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

public class PieceFactory {
    private HashMap<Point, IPiece> pieces = new HashMap<Point, IPiece>();
    private IPiece dragged;

    // this.board.put(new Point(0, 2), new AffineDecorator(new Piece(11), localAffineTransform));

    public void MovePiece(Point from, Point to){
        dragged = pieces.remove(from);
        pieces.put(to, dragged);
    }
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
        pieces.get(piecePos).draw(graphics, piecePos);
    }
    public void DrawDragged(Point draggedPos, Graphics2D graphics) {
        dragged.draw(graphics, draggedPos);
    }
    public void DrawAll(Graphics2D graphics) {
        for(Entry<Point, IPiece> keyValue : pieces.entrySet())
            keyValue.getValue().draw(graphics, keyValue.getKey());
    }
}
