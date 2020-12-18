import java.awt.Point;

public class Board {
	
	public int width;
	public int height;
	public PieceType[][] matrix;
	
	
	
	
	public Piece fallingPiece; 
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.matrix = new PieceType[height][width];
		
		fallingPiece = new Piece(PieceType.getRandomPieceType(), 4, 4);
		initializeBoard();
		
		
	}
	
	public boolean placePiece(int x, int y) {
		
		for(Point p :fallingPiece.points) {
			int xPos = x + p.x;
			int yPos = y - p.y;

			try {
				if(matrix[yPos][xPos] != PieceType.Empty) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
			
		}
		
		
		for(Point p :fallingPiece.points) {
			int xPos = x + p.x;
			int yPos = y - p.y;

			matrix[yPos][xPos] = fallingPiece.type;
		}
		
		return true;
	}
	
	
	
	public boolean placePiece() {
		return placePiece(fallingPiece.x, fallingPiece.y);
	}
	
	public void clearPiece() {
		for(Point p :fallingPiece.points) {
			int xPos = fallingPiece.x + p.x;
			int yPos = fallingPiece.y - p.y;
			matrix[yPos][xPos] = PieceType.Empty;
		}
	}
	
	public boolean shiftDown() {
		this.clearPiece();
		this.fallingPiece.y+=1;
		
		boolean result = this.placePiece(this.fallingPiece.x, this.fallingPiece.y);
		
		if(result == false) {
			this.fallingPiece.y-=1;
			this.placePiece(this.fallingPiece.x, this.fallingPiece.y);
			return false;
		}

		return true;
	}
	
	public boolean shiftRight() {
		this.clearPiece();
		this.fallingPiece.x+=1;
		
		boolean result = this.placePiece(this.fallingPiece.x, this.fallingPiece.y);
		
		if(result == false) {
			this.fallingPiece.x-=1;
			this.placePiece(this.fallingPiece.x, this.fallingPiece.y);
			return false;
		}

		return true;
	}
	
	public boolean rotate() {
		clearPiece();
		Point[] oldpts = fallingPiece.points;
		fallingPiece.points = fallingPiece.computeNextRotation();
		boolean result = placePiece();
		if(result == false) {
			fallingPiece.points = oldpts;
			this.placePiece();
			return false;
		}
		return true;
	}
	
	public boolean checkDeath() {
		boolean result = false;
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				if(i < 2 && matrix[i][j] != PieceType.Empty) {
					result = true;
					
				}
				if(result == true && matrix[i][j] != PieceType.Empty) {
					matrix[i][j] = PieceType.Dead;
					
				}
			}
		}
		
		return result;
	}
	
	public void dropPiece() {
//		int minDist = 10000;
//		for(int i = 0; i < this.fallingPiece.points.length; i++) {
//			
//			int xPos = fallingPiece.x + this.fallingPiece.points[i].x;
//			int yPos = fallingPiece.y - this.fallingPiece.points[i].y;
//		
//			int distanceToFloor = 19 - yPos - getColumnHeight(xPos);
//			if(distanceToFloor < minDist) {
//				minDist = distanceToFloor;
//			}
//		}
//		
//		this.fallingPiece.y += minDist;
		boolean result = true;
		while(result == true) {
			result = this.shiftDown();
		}
	}
	
	private int getColumnHeight(int column) {
		for(int i = 0; i < matrix.length; i++) {
			if(matrix[(19)-i][column] == PieceType.Empty) {
				return i+1;
			}
		}
		return -1;
	}
	
	public boolean shiftLeft() {
		this.clearPiece();
		this.fallingPiece.x-=1;
		
		boolean result = this.placePiece(this.fallingPiece.x, this.fallingPiece.y);
		
		if(result == false) {
			this.fallingPiece.x+=1;
			this.placePiece(this.fallingPiece.x, this.fallingPiece.y);
			return false;
		}

		return true;
	}
	
	public boolean clearLines() {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j] == PieceType.Empty) break; // don't need to clear this line;
				else if(j == matrix[0].length - 1) { // reached end 
					clearLine(i);
					slideBlocksDown(i);
				}
			}
		}
		
		return true;
	}
	
	private void clearLine(int row) {
		for(int i = 0; i < matrix[0].length; i++) {
			matrix[row][i] = PieceType.Empty;
		}
	}
	
	private void slideBlocksDown(int row) { // row to shift to
		for(int i = row-1; i >= 0; i--) {
			for(int j = 0; j < matrix[0].length; j++) {
				matrix[i+1][j] = matrix[i][j];
			}
		}
	}
	
	
	
	private void initializeBoard() { // just set everything to empty values;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				this.matrix[y][x] = PieceType.Empty;
				
			}
		}

	}
}
