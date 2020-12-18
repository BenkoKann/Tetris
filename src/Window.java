import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public class Window {

		
	public Window(int width, int height, Game game) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setPreferredSize(new Dimension(width, height));
		frame.add(game);
		frame.addKeyListener(new KeyInput());
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
