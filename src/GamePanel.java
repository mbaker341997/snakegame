import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

//the JPanel with the game in it.
@SuppressWarnings("serial")
public class GamePanel extends JPanel{

	private CanvasPanel canvas;
	private int score, xpos, ypos;
	private Snake snek;
	private FoodDot dot;
	private boolean gameStart, gameOver;
	private Timer timer;
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	public int direction;
	
	public GamePanel() {
		//snake start position
		xpos = 290;
		ypos = 290;
		snek = new Snake(xpos, ypos);
		dot = new FoodDot(xpos + 50, ypos);
		direction = -1;//default direction, this is probably horrible practice
		
		//score info
		score = 0;
		gameOver = false;
		
		//set up timer
		gameStart = false;
		timer = new Timer(50, new TimerListener());
		
		//set up the key listener
		canvas = new CanvasPanel();	
		addKeyListener(new ArrowListener());
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		//put it all together
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
			page.drawString("Score: " + score, 40, 30);
			
			//bounds
			page.drawRect(40, 40, 500, 500);
			
			//draw the snake
			snek.draw(page);
			
			//draw the dot
			dot.draw(page);
			
			//show it game over if it is
			if(gameOver){
				page.setFont(new Font(Font.SERIF, Font.BOLD, 50));
				page.setColor(Color.RED);
				page.drawString("GAME OVER", 145, 250);
			}
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
			if(!gameOver){
				//change the snake's direction
				if(key.getKeyCode() == KeyEvent.VK_UP && direction != DOWN){					
					direction = UP;
				}
				else if(key.getKeyCode() == KeyEvent.VK_DOWN && direction != UP){
					direction = DOWN;
				}
				else if(key.getKeyCode() == KeyEvent.VK_LEFT && direction != RIGHT){
					direction = LEFT;
				}
				else if(key.getKeyCode() == KeyEvent.VK_RIGHT && direction != LEFT){
					direction = RIGHT;
				}
				
				//start the timer if the game has begun yet
				if(!gameStart){
					gameStart = true;
					timer.start();
				}			
			}
			//if the game's over and you press space, restart
			else if(key.getKeyCode() == KeyEvent.VK_SPACE){
				//snake start position
				xpos = 290;
				ypos = 290;
				snek = new Snake(xpos, ypos);
				dot = new FoodDot(xpos + 50, ypos);
				
				//score info
				score = 0;
				gameOver = false;
				
				//set up timer
				gameStart = false;
				timer = new Timer(50, new TimerListener());
				
				repaint();
			}

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
	
	private class TimerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//move the snake
			if(direction == UP){					
				if(ypos > 40)
					ypos-=10;
				else
					gameOver = true;
			}
			else if(direction == DOWN){
				if(ypos < 530)
					ypos+=10;
				else
					gameOver = true;
			}
			else if(direction == LEFT){
				if(xpos > 40)
					xpos-=10;
				else
					gameOver = true;
			}
			else if(direction == RIGHT){
				if(xpos < 530)
					xpos+=10;
				else
					gameOver = true;
			}
			snek.setCoords(xpos, ypos);	

			//stop the timer if gameOver
			if(gameOver)
				timer.stop();
			
			//move the dot if it has been eaten
			if(hasEaten()){
				score++;
				dot.changePos(xpos, ypos);
			}
			
			//repaint 
			repaint();
		}
	
	}
}
