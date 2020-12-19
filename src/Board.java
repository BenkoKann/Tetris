import java.awt.Point;

public class Board {
	
	public int width;
	public int height;
	public PieceType[][] matrix;
	public int score; // number of lines cleared in total

	public Piece fallingPiece; 
	
	
	public PieceType[][] prevMatrix = null;
	public Piece prevPiece = null;
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.matrix = new PieceType[height][width];
		
		fallingPiece = new Piece(PieceType.getRandomPieceType(), 4, 4);
		initializeBoard();
		
		
	}
	
	public Board(PieceType[][] matrix, Piece fallingPiece) { // basically a clone constructor
		this.width = 10;
		this.height = 20;
		
		this.matrix = matrix;
		this.fallingPiece = fallingPiece;
		
	}
	
	public void push() { // saves current board state
		this.prevMatrix = copy2DArray(matrix);
		this.prevPiece = new Piece(fallingPiece.type, fallingPiece.x, fallingPiece.y, fallingPiece.points);
	}
	
	public void pop() { // restores previous saved board state
		this.matrix = prevMatrix;
		this.fallingPiece = prevPiece;
	}

	
	public PieceType[][] copy2DArray(PieceType[][] old) {
		PieceType[][] child = new PieceType[old.length][];
		for(int i = 0; i < old.length; i++) {
			child[i] = old[i].clone();
		}
		return child;
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
		boolean result = true;
		while(result == true) {
			result = this.shiftDown();
		}
	}
	
	/**
	 * computes the value Y in which the piece will come to rest
	 */
	
	
	
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
	
	public void clearLines() {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j] == PieceType.Empty) break; // don't need to clear this line;
				else if(j == matrix[0].length - 1) { // reached end 
					this.score++;
					clearLine(i);
					slideBlocksDown(i);
				}
			}
		}
		
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
	
	// [START] stuff the AI uses
	
	public double getAverageHeight() {
		double sum = 0;
		for(int i = 0; i < width; i++) {
			sum += this.getHeightOfColumn(i);
		}
		return sum / width;
	}
	
	public int maxHeight() {
		int maxHeight = -1;

		for(int x = 0; x < width; x++) {
			int columnHeight = 0;

			columnHeight = this.getHeightOfColumn(x);
			if(columnHeight > maxHeight) {
				maxHeight = columnHeight;
			}		
		}
		
		return maxHeight;
	}
	
	public int getHeightOfColumn(int x) {
		for(int i = 0; i < height; i++) {
			if(matrix[i][x] != PieceType.Empty) {
				return height - i;
			}
		}
		return -1;
	}
	
	// [END] Stuff the AI Uses
	
	
	
	private void initializeBoard() { // just set everything to empty values;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				this.matrix[y][x] = PieceType.Empty;
				this.matrix[y][x] = PieceType.Empty;
				
			}
		}

	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public static void main(String[] args) {
		Board b = new Board(5, 5);
		b.initializeBoard();
		
		b.matrix[4][4] = PieceType.J;
		b.matrix[3][4] = PieceType.L;
		System.out.println(b.getHeightOfColumn(4));
	}
}
