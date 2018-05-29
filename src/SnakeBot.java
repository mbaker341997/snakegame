import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class SnakeBot {

	private Robot bot;
	private GamePanel gp;
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	public static final int WAIT = 0;
	
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
		Point atP = new Point(290, 290);
		Point nextP = new Point(300, 290);
		double rightValue = 0;
		double leftValue = 0;
		double straightValue = 0;
		String lastMove = "right";
		int lastDir = 0;
		while(!gp.isGameOver())
		{
			atP = gp.getSnekHeadLoc();
			lastDir = gp.getDirection();
			/*printPoint(gp.getSnekHeadLoc());
			System.out.print("If I go right: ");
			printPoint(simRightMove());
			System.out.print("If I go left: ");
			printPoint(simLeftMove());
			System.out.print("If I stay straight: ");
			printPoint(simStraight());
			System.out.println("Value of going right: " + valueFunc(simRightMove()));
			System.out.println("Value of going left: " + valueFunc(simLeftMove()));
			System.out.println("Value of staying straight: " + valueFunc(simStraight()));*/
			
			rightValue = valueFunc(simRightMove());
			leftValue = valueFunc(simLeftMove());
			straightValue = valueFunc(simStraight());
			
			if(straightValue >= rightValue && straightValue >= leftValue){
				//System.out.println("straight");
				lastMove = "straight";
				nextP = simStraight();
				stayStraight();
			}
			else if(rightValue >= leftValue && rightValue >= straightValue){
				//System.out.println("right");
				lastMove = "right";
				nextP = simRightMove();
				moveRight();
			}
			else{
				//System.out.println("left");
				lastMove = "left";
				nextP = simLeftMove();
				moveLeft();
			}
		}
		System.out.println("I made my decision at " + pointString(atP) + " heading " + lastDir);
		System.out.println("Snake died at" + pointString(gp.getSnekHeadLoc()));
		System.out.println("I expected to be at" + pointString(nextP));
		System.out.println("Last Move: " + lastMove);
		System.out.println("Right Value: " + rightValue);
		System.out.println("Left Value: " + leftValue);
		System.out.println("Straight Value: " + straightValue);
	}

	//manhattan distance
	public double getDistToDot(Point p){
		Point foodLoc = gp.getFoodDotLoc();
		return Math.abs(p.getX() - foodLoc.getX()) + Math.abs(p.getY() - foodLoc.getY());	
	}
	
	//from the POV of the snake head
	public void moveLeft(){
		//System.out.println("moving left");
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
		//System.out.println("moving right");
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
		//System.out.println("Staying straight");
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
			p.setLocation(p.getX(), p.getY()-10);
		else if(dir == DOWN)
			p.setLocation(p.getX(), p.getY()+10);
		else if(dir == LEFT)
			p.setLocation(p.getX()-10, p.getY());
		else if(dir == RIGHT)
			p.setLocation(p.getX()+10, p.getY());
		return p;
	}
	
	public double valueFunc(Point p){
		if(gp.isSnekPoint(p))
			return -20000;
		if(p.equals(gp.getFoodDotLoc()))
			return 1000;
		else if(p.getX() < 40 || p.getX() > 530 || p.getY() < 40 || p.getY() > 530)
			return -1000000;
		//TODO find a way to make it detect when the snake hits itself
		return -getDistToDot(p);
	}
	
	public String pointString(Point p){
		return "(" + p.getX() + ", " + p.getY() + ")";
	}
}
