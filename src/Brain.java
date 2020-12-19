
public interface Brain {
	
	
	public static class Move {
		public int x;
		public int y;
		public Piece piece;
		public double score;
	}
	
	public Brain.Move bestMove(Board board, int limitHeight, Brain.Move move);

}
