import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel{
	
	private Board board;

	
	
	private static final Color boarderColor = Color.decode("#393939");
	
	
	
	static int top = 10;
	static int left = 170;
	static int size = 30;
	
	Timer t;
	Timer t2;
	
	long keytime = 0;
	
	long lastTime;
	
	ActionListener gameLoop = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		
			update();
			repaint();
		}
		
	};
	
	ActionListener keyLoop = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			keytime += System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			
			if(keytime > 75) {
				keytime = 0;
				if(KeyInput.isLeft()) {
					board.shiftLeft();
					repaint();
				}
				
				if(KeyInput.isRight()) {
					board.shiftRight();
					repaint();
				}
				
				if(KeyInput.isUp()) {
					board.rotate();
					repaint();
				}
				
				if(KeyInput.isDown()) {
					board.clearPiece();
					board.dropPiece();
					board.placePiece();
					board.fallingPiece = new Piece(PieceType.getRandomPieceType(), 4, 1);
					board.clearLines();
					repaint();
				}
			}
			
			
			
		}
		
	};
	
	public Game() {
		new Window(660, 660, this);
		board = new Board(10, 20);
		
	
		board.placePiece(4, 4);
		
		t = new Timer(300, gameLoop);
		t2 = new Timer(17, keyLoop);
		

		lastTime = System.currentTimeMillis();
		
		
		t.start();
		t2.start();
	}
	
	public void update() {
		
		boolean result = board.shiftDown();
		
		if(result == false) {
			boolean gameOver = board.checkDeath();
			if(gameOver == true) {
				t.stop();
				t2.stop();
				return;
			}
			board.fallingPiece = new Piece(PieceType.getRandomPieceType(), 4, 1);
			board.clearLines();
		}
		

		
	}
	
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(boarderColor);

		for(int y = 0; y < board.height; y++) {
			for(int x = 0; x < board.width; x++) {
				
				paintHelper(g, board.matrix[y][x].getColor(), boarderColor, x, y);
				
				g.drawRect(left + x * size, top + y * size, size, size);
			}
		}
	}
	
	private void paintHelper(Graphics g, Color startColor, Color endColor, int x, int y) {
		g.setColor(startColor);
		g.fillRect(left + x * size, top + y * size, size, size);
		g.setColor(endColor);
	}
	
	public static void main(String[] args) {
		new Game();
	}

}

