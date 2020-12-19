import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Piece {
	
	public PieceType type;	
	public int x, y;
	public Point[] points;
	
	public Piece(PieceType piece) {
		this(piece, 0, 0);
	}
	
	public Piece(PieceType type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.points = this.type.getPoints(); // only get points from enum in the constructor, now use the field to get them
	}
	
	public Piece(PieceType type, int x, int y, Point[] points) {
		this(type, x, y);
		this.points = this.type.getPoints(); 
	}
	
	public int getWidth() {
		int leftMost = 999;
		int rightMost = - 100;
		for(Point p : this.points) {
			if(p.x < leftMost) leftMost = p.x;
			if(p.x > rightMost) rightMost = p.x;
		} 
		return rightMost - leftMost + 1;
	}
	
	public int getHeight() {
		int bottomMost = 999;
		int topMost = - 100;
		for(Point p : this.points) {
			if(p.y < bottomMost) bottomMost = p.y;
			if(p.y > topMost) topMost = p.y;
		} 
		return topMost - bottomMost + 1;
	}
	
	private int[][] multiplyMatrix(int[][] m1, int[][] m2) {
		int r1 = m1.length;
		int c1 = m1[0].length;
		
		//int r2 = m2.length;
		int c2 = m2[0].length;
		
		
		
		
		int[][] product = new int[r1][c2];
		for(int i = 0; i < r1; i++) {
			for(int j = 0; j < c2; j++) {
				for(int k = 0; k < c1; k++) {
					product[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return product;
	}
	
	private int[][] pieceToMatrix() {
		int size = Math.max(this.getHeight(), this.getWidth());
		int[][] matrix = new int[size][size];
		for(int i = 0; i < this.points.length; i++) {
			matrix[points[i].y][points[i].x] = 1;
		}
		return matrix;
	}
	
	private Point[] matrixToPoints(int[][] matrix) {
		Point[] points = new Point[this.points.length];
		int count = 0;
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix.length; j++) {
				if(matrix[i][j] == 1) {
					points[count] = new Point(j, i);
					count++;
				}
			}
		}
		return points;
	}
	
	
	
	public Point[] computeNextRotation() {
		int size = Math.max(getWidth(), getHeight());
		int[][] matrix = new int[size][size];
		int[][] inverse = new int[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(i + j == size-1) inverse[i][j] = 1;
			}
		}
		for(Point p : points) { // input point data
			matrix[p.y][p.x] = 1; 
		}
		matrix = flipMatrix(matrix);
	    
	    matrix = multiplyMatrix(matrix, inverse); // rotates clockwise
	    return matrixToPoints(matrix);
	   
	}
	
	private int[][] flipMatrix(int[][] matrix) { 
		/**
		 * 1 1 1
		 * 0 0 1
		 * 0 0 0
		 * 
		 * 1 0 0 
		 * 1 0 0 
		 * 1 1 0
		 */
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix.length; j++) {
				if(j > i) {
					int temp = matrix[i][j];
					matrix[i][j] = matrix[j][i];
					matrix[j][i] = temp;		
				}
			}
		}
		return matrix;
	}
	
	public static void main(String[] args) {
	
	}
	


	

	

	


}
