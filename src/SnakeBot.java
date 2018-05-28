import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class SnakeBot {

	private Robot bot;
	private GamePanel gp;
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	public static final int WAIT = 100;
	
	public SnakeBot(GamePanel gp) {
		this.gp = gp;
		try {
			bot = new Robot();
			bot.setAutoDelay(WAIT);
		} catch (AWTException e) {
			e.printStackTrace();
		}		
	}
	
	public void solveGame()
	{
		System.out.println(getDistToDot(gp.getSnekHeadLoc()));		
		bot.keyPress(KeyEvent.VK_RIGHT);
	}

	public double getDistToDot(Point p){
		Point foodLoc = gp.getFoodDotLoc();
		return Point.distance(p.getX(), p.getY(), foodLoc.getX(), foodLoc.getY());
	}
	
	//from the POV of the snake head
	public void moveLeft(){
		int dir = gp.getDirection();
		if(dir == UP)
			bot.keyPress(KeyEvent.VK_LEFT);
		else if(dir == DOWN)
			bot.keyPress(KeyEvent.VK_RIGHT);
		else if(dir == LEFT)
			bot.keyPress(KeyEvent.VK_DOWN);
		else if(dir == RIGHT)
			bot.keyPress(KeyEvent.VK_UP);
	}
	
	//from the POV of the snake head
	public void moveRight(){
		int dir = gp.getDirection();
		if(dir == UP)
			bot.keyPress(KeyEvent.VK_RIGHT);
		else if(dir == DOWN)
			bot.keyPress(KeyEvent.VK_LEFT);
		else if(dir == LEFT)
			bot.keyPress(KeyEvent.VK_UP);
		else if(dir == RIGHT)
			bot.keyPress(KeyEvent.VK_DOWN);
	}	
	
	//from the POV of the snake head
	public void stayStraight(){
		try {
			bot.wait(WAIT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//from the POV of the snake head
	public Point simLeftMove(){
		int dir = gp.getDirection();
		Point p = gp.getSnekHeadLoc();
		if(dir == UP)
			p.setLocation(p.getX()-10, p.getY());
		else if(dir == DOWN)
			p.setLocation(p.getX()+10, p.getY());
		else if(dir == LEFT)
			p.setLocation(p.getX(), p.getY()+10);
		else if(dir == RIGHT)
			p.setLocation(p.getX(), p.getY()-10);
		return p;
	}
	
	//from the POV of the snake head
	public Point simRightMove(){
		int dir = gp.getDirection();
		Point p = gp.getSnekHeadLoc();
		if(dir == UP)
			p.setLocation(p.getX()+10, p.getY());
		else if(dir == DOWN)
			p.setLocation(p.getX()-10, p.getY());
		else if(dir == LEFT)
			p.setLocation(p.getX(), p.getY()-10);
		else if(dir == RIGHT)
			p.setLocation(p.getX(), p.getY()+10);
		return p;
	}
	
	//from the POV of the snake head
	public Point simStraight(){
		int dir = gp.getDirection();
		Point p = gp.getSnekHeadLoc();
		if(dir == UP)
			p.setLocation(p.getX(), p.getY()+10);
		else if(dir == DOWN)
			p.setLocation(p.getX(), p.getY()-10);
		else if(dir == LEFT)
			p.setLocation(p.getX()-10, p.getY());
		else if(dir == RIGHT)
			p.setLocation(p.getX()+10, p.getY());
		return p;
	}
}
