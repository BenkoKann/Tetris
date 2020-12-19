import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
//	public static boolean left = false;
//	public static boolean right = false;
//	public static boolean up = false;
//	public static boolean down = false;
	
	Game game;
	
	
	public KeyInput(Game game) {
		this.game = game;
	}
	
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D) {
			if(!game.gameOver) {
				game.board.shiftRight();
				game.repaint();
			}
		}		
		if(key == KeyEvent.VK_A) {
			if(!game.gameOver) {
				game.board.shiftLeft();
				game.repaint();
			}
		}
		if(key == KeyEvent.VK_W) {
			if(!game.gameOver) {
				game.board.rotate();
				game.repaint();
			}
			
		}
		if(key == KeyEvent.VK_S) {
			if(!game.gameOver) {
				game.board.clearPiece();
				game.board.dropPiece();
				//game.board.placePiece();
				game.board.fallingPiece = new Piece(PieceType.getRandomPieceType(), 4, 1);
				game.board.clearLines();
				game.repaint();
			}
		}
		
		if(key == KeyEvent.VK_SPACE) {
			if(!game.gameOver) {
				game.board.clearPiece();
				DefaultBrain b = new DefaultBrain();
				Brain.Move move = b.bestMove(game.board, 20, new Brain.Move());

				game.board.placePiece(move.x, move.y);
			
				game.board.fallingPiece = new Piece(PieceType.getRandomPieceType(), 4, 1);
				game.board.clearLines();
				game.repaint();
			}
			
			
			
			
		}
	}
	


	
	
}
