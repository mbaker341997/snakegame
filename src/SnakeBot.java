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
		while(!gp.isGameOver())
		{
			/*System.out.print("I'm at "); 
			printPoint(gp.getSnekHeadLoc());
			System.out.print("Food's at ");
			printPoint(gp.getFoodDotLoc());
			System.out.println("Distance if I go right: " + getDistToDot(simRightMove()));
			System.out.println("Distance if I go left: " + getDistToDot(simLeftMove()));
			System.out.println("Distance if I stay straight: " + getDistToDot(simStraight()));*/
			
			//if moving right gets you closest to the dot then move right
			if(getDistToDot(simRightMove()) <= getDistToDot(simLeftMove()) && getDistToDot(simRightMove()) <= getDistToDot(simStraight()))
				moveRight();
			//if moving left gets you closest to the dot then move left
			else if(getDistToDot(simLeftMove()) <= getDistToDot(simRightMove()) && getDistToDot(simLeftMove()) <= getDistToDot(simStraight()))
				moveLeft();
			//otherwise just keep going straight
			else
				stayStraight();
		}
	}

	//manhattan distance
	public double getDistToDot(Point p){
		Point foodLoc = gp.getFoodDotLoc();
		return Math.abs(p.getX() - foodLoc.getX()) + Math.abs(p.getY() - foodLoc.getY());	
	}
	
	//from the POV of the snake head
	public void moveLeft(){
		System.out.println("moving left");
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
		System.out.println("moving right");
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
		System.out.println("Staying straight");
		int dir = gp.getDirection();
		if(dir == UP)
			bot.keyPress(KeyEvent.VK_UP);
		else if(dir == DOWN)
			bot.keyPress(KeyEvent.VK_DOWN);
		else if(dir == LEFT)
			bot.keyPress(KeyEvent.VK_LEFT);
		else if(dir == RIGHT)
			bot.keyPress(KeyEvent.VK_RIGHT);
		/*try {
			bot.wait(WAIT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
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
	
	public void printPoint(Point p){
		System.out.println("(" + p.getX() + ", " + p.getY() + ")");
	}
}
