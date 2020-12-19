import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel{
	
	public Board board;

	
	
	private static final Color boarderColor = Color.decode("#393939");
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	static int top = 100;
	
	static int size = 30;
	static int left = (int) (screenSize.getWidth()/2 - (size*10)/2 - 40);
	public boolean gameOver = false;
	
	Timer t;

	
	long keytime;
	
	long lastTime;
	
	ActionListener gameLoop = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		
			update();
			repaint();
		}
		
	};
	

	public Game() {
		new Window((int) screenSize.getWidth(), (int)screenSize.getHeight(), this);
		board = new Board(10, 20);
		
	
		board.placePiece(4, 4);
		
		t = new Timer(300, gameLoop);
		
		keytime = 0;

		

		lastTime = System.currentTimeMillis();
		
		
		t.start();
	
	}
	
	public void reset() {
//		board = new Board(10, 20);
//		board.fallingPiece = new Piece(PieceType.getRandomPieceType(), 4, 2);
//		board.placePiece(4, 4);
//		keytime = 0;
//		lastTime = System.currentTimeMillis();
//		t.restart();
		
	}
	
	public void update() {
		
		
		
		boolean result = board.shiftDown();
		
		if(result == false) {
			boolean gameOver = board.checkDeath();
			if(gameOver == true) {
				this.gameOver = true;
				t.stop();
				return;
			}
			board.fallingPiece = new Piece(PieceType.getRandomPieceType(), 4, 1);
			board.clearLines();
			
			
			
		}
		
	
		
		
		

		
	}
	
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("HP Simplified Jpan", Font.PLAIN, 55));
		g.setColor(boarderColor);
		
		g.drawString("Score: " + board.score, 620, 80);

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
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		g.drawRect(left + x * size, top + y * size, size, size);
		g.setColor(endColor);
	}
	
	public static void main(String[] args) {
		new Game();
	}

}

