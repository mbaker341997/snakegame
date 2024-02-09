import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Map;
import javax.swing.JPanel;
import javax.swing.Timer;

// the JPanel with the game in it.
public class GamePanel extends JPanel {

  private int score, xpos, ypos;
	private Snake snek;
	private FoodDot dot;
	private boolean gameStart, gameOver, makingMove;
	private Timer timer;
	public Direction direction;
	private final boolean robotMode;
	
	public GamePanel(int choice) {
		setup();
		
		// check if it's robot mode
    robotMode = choice == 1;
		
		// set up the key listener
    CanvasPanel canvas = new CanvasPanel();
		addKeyListener(new ArrowListener());
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		// put it all together
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
	}
	
	// initial settings
	public void setup() {
		// snake start position
		this.xpos = 100;
		this.ypos = 50;
		this.snek = new Snake(this.xpos, this.ypos);
		this.dot = new FoodDot(40, 250);
		this.direction = Direction.UP;
		this.makingMove = false;
				
		// score info
		this.score = 0;
		this.gameOver = false;
				
		// set up timer
		this.gameStart = false;
		this.timer = new Timer(60, new TimerListener());
	}
	
	// panel where everything will show up
	private class CanvasPanel extends JPanel {
		public void paintComponent(Graphics page) {
			super.paintComponent(page);
			setBackground(Color.BLACK);
			
			// set score label
			page.setFont(new Font(Font.SERIF, Font.BOLD, 25));
			page.setColor(Color.WHITE);
			page.drawString("Score: " + score, 40, 30);
			
			// robot mode label
			if (GamePanel.this.robotMode) {
				page.drawString("Robot Mode", 235, 30);
			}
			
			// bounds
			page.drawRect(40, 40, 500, 500);
			
			// draw the snake
			snek.draw(page);
			
			// draw the dot
			dot.draw(page);
			
			// show it game over if it is
			if (GamePanel.this.gameOver) {
				page.setFont(new Font(Font.SERIF, Font.BOLD, 50));
				page.setColor(Color.RED);
				page.drawString("GAME OVER", 145, 250);
			}
		}
	}
	
	public boolean hasEaten() {
    return dot.getX() == snek.getX() && dot.getY() == snek.getY();
	}
	
	public Point getFoodDotLoc() {
    return new Point(dot.getX(), dot.getY());
	}
	
	public Point getSnekHeadLoc() {
    return new Point(xpos, ypos);
	}
	
	public Direction getDirection(){
		return this.direction;
	}
	
	public boolean isGameOver(){
		return this.gameOver;
	}
	
	public boolean isSnekPoint(Point p) {
		return snek.getTail().contains(p);
	}

	public void move(Direction newDirection) {
		if (!Direction.NO_OP_SET.get(this.direction).contains(newDirection)) {
			this.direction = newDirection;
			this.makingMove = true;
		}
	}

	public void startGame() {
		this.gameStart = true;
		this.timer.start();
	}

	private class ArrowListener implements KeyListener {

		private static final Map<Integer, Direction> KEY_EVENT_DIRECTION_MAP = Map.of(
				KeyEvent.VK_UP, Direction.UP,
				KeyEvent.VK_DOWN, Direction.DOWN,
				KeyEvent.VK_LEFT, Direction.LEFT,
				KeyEvent.VK_RIGHT, Direction.RIGHT
		);

		@Override
		public void keyPressed(KeyEvent key) {
			if (!GamePanel.this.gameOver && !GamePanel.this.makingMove) {
				if (KEY_EVENT_DIRECTION_MAP.containsKey(key.getKeyCode())) {
					GamePanel.this.move(KEY_EVENT_DIRECTION_MAP.get(key.getKeyCode()));
				}

				// start the timer if the game has begun yet
				if (!GamePanel.this.gameStart) {
					GamePanel.this.startGame();
				}
			} else if (key.getKeyCode() == KeyEvent.VK_SPACE && !GamePanel.this.makingMove) {
				// if the game's over and you press space, restart
				setup();
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
	
	private class TimerListener implements ActionListener {
		private static final Map<Direction, int[]> DIRECTION_TO_DELTA_MAP = Map.of(
				Direction.UP, new int[] { 0, -10 },
				Direction.DOWN, new int[] { 0, 10 },
				Direction.LEFT, new int[] { -10, 0 },
				Direction.RIGHT, new int[] { 10, 0 }
		);

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (GamePanel.this.xpos < 40 || GamePanel.this.xpos >= 530
					|| GamePanel.this.ypos < 40 || GamePanel.this.ypos >= 530) {
				GamePanel.this.gameOver = true;
				GamePanel.this.timer.stop();
			} else {

				//change snake's makeup
				GamePanel.this.snek.addToTail(GamePanel.this.xpos, GamePanel.this.ypos);

				// TODO: make a constants file for game dimensions
				if (GamePanel.this.ypos >= 40 && GamePanel.this.ypos < 530
						&& GamePanel.this.xpos >= 40 && GamePanel.this.xpos < 530
						&& DIRECTION_TO_DELTA_MAP.containsKey(GamePanel.this.direction)
				) {
					int[] delta = DIRECTION_TO_DELTA_MAP.get(GamePanel.this.direction);
					GamePanel.this.xpos += delta[0];
					GamePanel.this.ypos += delta[1];
				}

				GamePanel.this.snek.setCoords(GamePanel.this.xpos, GamePanel.this.ypos);

				// if the snake has eaten, then move the dot and don't chop off end
				if (hasEaten()) {
					GamePanel.this.score++;
					GamePanel.this.dot.changePos(GamePanel.this.snek.getTail());
				} else {
					GamePanel.this.snek.chopOffEnd();
				}

				if (GamePanel.this.snek.touchedSelf()) {
					System.out.println("touched self");
					GamePanel.this.gameOver = true;
					GamePanel.this.timer.stop();
				}
			}

			GamePanel.this.makingMove = false;
			repaint();
		}
	
	}
}
