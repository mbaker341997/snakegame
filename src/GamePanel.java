import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

//the JPanel with the game in it.
@SuppressWarnings("serial")
public class GamePanel extends JPanel{

	private CanvasPanel canvas;
	private int score, xpos, ypos;
	private Snake snek;
	private FoodDot dot;
	
	public GamePanel() {
		//snake start position
		xpos = 290;
		ypos = 290;
		snek = new Snake(xpos, ypos);
		dot = new FoodDot(xpos + 50, ypos);
		
		//score info
		score = 0;
		
		canvas = new CanvasPanel();	
		addKeyListener(new ArrowListener());
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
	}
	
	//panel where everything will show up
	private class CanvasPanel extends JPanel
	{
		public void paintComponent(Graphics page){
			super.paintComponent(page);
			setBackground(Color.BLACK);
			
			//set score label
			page.setFont(new Font(Font.SERIF, Font.BOLD, 25));
			page.setColor(Color.WHITE);
			page.drawString("Score: " + score, 40, 25);
			
			//bounds
			page.drawRect(40, 40, 500, 500);
			
			//draw the snake
			snek.draw(page);
			
			//draw the dot
			dot.draw(page);
		}
	}
	
	public boolean hasEaten(){
		if(dot.getX() == snek.getX() && dot.getY() == snek.getY())
			return true;
		else
			return false;
	}
	
	private class ArrowListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent key) {
			if(key.getKeyCode() == KeyEvent.VK_UP){
				if(ypos > 40){
					ypos-=10;
				}
			}
			else if(key.getKeyCode() == KeyEvent.VK_DOWN){
				if(ypos < 530)
					ypos+=10;
			}
			else if(key.getKeyCode() == KeyEvent.VK_LEFT){
				if(xpos > 40)
					xpos-=10;
			}
			else if(key.getKeyCode() == KeyEvent.VK_RIGHT){
				if(xpos < 530)
					xpos+=10;
			}
			snek.setCoords(xpos, ypos);
			if(hasEaten()){
				score++;
				dot.changePos(xpos, ypos);
			}
			repaint();

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}
		
	}
}
