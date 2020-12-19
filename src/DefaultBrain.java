
public class DefaultBrain implements Brain{

	@Override
	public Brain.Move bestMove(Board board, int limitHeight, Move move) {
		if(move == null) {
			move = new Brain.Move();
		}
		
		double bestScore = 1e20;
		int bestX = 0;
		int bestY = 0;
		Piece bestPiece = null;
		
		Board boardcpy = new Board(board.copy2DArray(board.matrix), board.fallingPiece);
		moveToLeftWall(boardcpy);


		boolean result = true;
		while(result) {
			boardcpy.push();
			boardcpy.dropPiece();
			double score = this.evaluate(boardcpy);
			if(score < bestScore) {
				bestScore = score;
				bestX = boardcpy.fallingPiece.x;
				bestY = boardcpy.fallingPiece.y;
				bestPiece = boardcpy.fallingPiece;
			}
			boardcpy.pop();
			result = boardcpy.shiftRight();
			
		}
		

		
		
		move.x = bestX;
		move.y = bestY;
		move.piece = bestPiece;
		move.score = bestScore;
		return move;
		

	}
	
	public void moveToLeftWall(Board board) {
		boolean result = true;
		while(result) {
			result = board.shiftLeft();
		}
	}
	
	public double evaluate(Board board) {
		
		// get maxheight
		// get total height
		// get number of holes
		
		// return (8*maxHeight + 40*avgHeight + 1.25*holes);
		
		double averageHeight = board.getAverageHeight();
		int maxHeight = board.maxHeight();
		int holes = 0;
		boolean searchForHoles = false;
		
		for(int x = 0; x<10; x++) {
			searchForHoles = false;
			for(int y = 0; y < 20; y++) {
				
				if(board.matrix[y][x] != PieceType.Empty) {
					searchForHoles = true; // we found top of column
				}
				if(searchForHoles && board.matrix[y][x] == PieceType.Empty) {
					holes++;
				}
			}
		}
		System.out.println(holes);
		return 40 * averageHeight * 8 * maxHeight + 1.25 * holes;
	}
	
	

}
