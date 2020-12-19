import java.awt.Color;
import java.awt.Point;

public enum PieceType {
	
	
	O(new Point[] {new Point(0,0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}, Color.decode("0xE39F02")),
	I(new Point[] {new Point(0,1), new Point(1, 1), new Point(2, 1), new Point(3, 1)}, Color.decode("#0F9BD7")),
	L(new Point[] {new Point(0,0), new Point(0, 1), new Point(1,0), new Point(2, 0)}, Color.decode("#2141C6")),
	T(new Point[] {new Point(1,0), new Point(0,1), new Point(1,1), new Point(2,1)}, Color.decode("#AF298A")),
	S(new Point[] {new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1)}, Color.decode("#59B101")),
	Z(new Point[] {new Point(1, 0), new Point(1, 1), new Point(0, 1), new Point(2, 0)}, Color.decode("#D70F37")),
	J(new Point[] {new Point(0,0), new Point(1, 0), new Point(2, 0), new Point(2, 1)}, Color.decode("#E35B02")),
	Empty,
	Dead(Color.GRAY);
	
	
	private final Point[] points;
	private final Color color;
	
	public static PieceType getRandomPieceType() {
		int l = PieceType.values().length - 2; // array of enum values and then length  minus the empty one and minus the ded one
		int randIndex = (int) (Math.random() * l);
		return PieceType.values()[randIndex];
	}
	
	PieceType() {
		this.points = null;
		this.color = Color.BLACK;
	}
	
	PieceType(Color color) {
		this.points = null;
		this.color = color;
	}
	
	PieceType(Point[] points, Color color) {
		this.points = points;
		this.color = color;
	}

	public Point[] getPoints() {
		return points;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getLength() {
		return points.length;
	}
	

	
	
	public static void main(String[] args) {
		// TEST
		System.out.println(PieceType.O.getPoints()[1].x); // it works
	}
}
