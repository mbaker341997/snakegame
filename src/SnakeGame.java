import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
		
		// choose to play with robot or not
		Object[] options = {"I want to play!", "Make the robot play"};
		int choice = JOptionPane.showOptionDialog(window,
				"Do you want to play or make the robot play?",
				"SnakeGame",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		System.out.println(choice);
		
		// add the GamePanel
		GameOuterPanel gameOuterPanel = new GameOuterPanel(choice == 1);
		window.add(gameOuterPanel);
		window.setVisible(true);
	}

}
