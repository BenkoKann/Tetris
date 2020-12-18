import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	public static boolean left = false;
	public static boolean right = false;
	public static boolean up = false;
	public static boolean down = false;
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D) {
			right = true;
		}		
		if(key == KeyEvent.VK_A) {
			left = true;
		}
		if(key == KeyEvent.VK_W) {
			up = true;
		}
		if(key == KeyEvent.VK_S) {
			down = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D) right = false;

		
		if(key == KeyEvent.VK_A) left = false;
		if(key == KeyEvent.VK_W) {
			up = false;
		}
		if(key == KeyEvent.VK_S) {
			down = false;
		}
	}
	
	
	
	public static boolean isLeft() {
		return left;
	}
	
	public static boolean isRight() {
		return right;
	}
	
	public static boolean isUp() {
		return up;
	}
	
	public static boolean isDown() {
		return down;
	}
	
	
}
