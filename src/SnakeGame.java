import java.awt.Insets;

import javax.swing.JFrame;

//Makes the window with the game
public class SnakeGame {

	public static void main(String[] args) {
		System.out.println("Snake Game started!");
		// build the window
		JFrame window = new JFrame(); 
		window.setTitle("SnakeGame");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets = window.getInsets();
		window.setSize(600 + insets.left + insets.right, 600 + insets.top + insets.bottom);
		//add the goddamn GamePanel
		GamePanel gp = new GamePanel();
		window.add(gp);
		window.setVisible(true);
	}

}
