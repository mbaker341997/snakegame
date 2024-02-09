import java.awt.Point;

public class SnakeBot {

	private final GamePanel gp;
	
	public SnakeBot(GamePanel gp) {
		this.gp = gp;
	}
	
	public void solveGame() {
		System.out.println("solving game");
		Point atP = new Point(290, 290);
		Point nextP = new Point(300, 290);
		double rightValue = 0;
		double leftValue = 0;
		double straightValue = 0;
		String lastMove = "right";
		Direction lastDir = Direction.UP;
		gp.startGame();
		while (!gp.isGameOver()) {
			atP = gp.getSnekHeadLoc();
			lastDir = gp.getDirection();
			
			rightValue = valueFunc(simRightMove());
			leftValue = valueFunc(simLeftMove());
			straightValue = valueFunc(simStraight());
			
			if (straightValue >= rightValue && straightValue >= leftValue) {
				lastMove = "straight";
				nextP = simStraight();
				stayStraight();
			}
			else if (rightValue >= leftValue && rightValue >= straightValue) {
				lastMove = "right";
				nextP = simRightMove();
				moveRight();
			} else {
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
	public double getDistToDot(Point p) {
		Point foodLoc = this.gp.getFoodDotLoc();
		return Math.abs(p.getX() - foodLoc.getX()) + Math.abs(p.getY() - foodLoc.getY());
	}

	// TODO: all of these move methods can be refactored
	
	//from the POV of the snake head
	public void moveLeft() {
		//System.out.println("moving left");
		Direction dir = gp.getDirection();
		if (dir == Direction.UP)
			gp.move(Direction.LEFT);
		else if (dir == Direction.DOWN)
			gp.move(Direction.RIGHT);
		else if (dir == Direction.LEFT)
			gp.move(Direction.DOWN);
		else if (dir == Direction.RIGHT)
			gp.move(Direction.UP);
	}
	
	//from the POV of the snake head
	public void moveRight() {
		//System.out.println("moving right");
		Direction dir = gp.getDirection();
		if (dir == Direction.UP)
			gp.move(Direction.RIGHT);
		else if (dir == Direction.DOWN)
			gp.move(Direction.LEFT);
		else if (dir == Direction.LEFT)
			gp.move(Direction.UP);
		else if (dir == Direction.RIGHT)
			gp.move(Direction.DOWN);
	}	
	
	//from the POV of the snake head
	public void stayStraight() {
		//System.out.println("Staying straight");
		Direction dir = gp.getDirection();
		if (dir == Direction.UP)
			gp.move(Direction.UP);
		else if (dir == Direction.DOWN)
			gp.move(Direction.DOWN);
		else if (dir == Direction.LEFT)
			gp.move(Direction.LEFT);
		else if (dir == Direction.RIGHT)
			gp.move(Direction.RIGHT);
	}
	
	//from the POV of the snake head
	// TODO: move direction deltas to constants or some utils
	public Point simLeftMove() {
		Direction dir = gp.getDirection();
		Point p = gp.getSnekHeadLoc();
		if (dir == Direction.UP)
			p.setLocation(p.getX()-10, p.getY());
		else if (dir == Direction.DOWN)
			p.setLocation(p.getX()+10, p.getY());
		else if (dir == Direction.LEFT)
			p.setLocation(p.getX(), p.getY()+10);
		else if (dir == Direction.RIGHT)
			p.setLocation(p.getX(), p.getY()-10);
		return p;
	}
	
	//from the POV of the snake head
	public Point simRightMove(){
		Direction dir = gp.getDirection();
		Point p = gp.getSnekHeadLoc();
		if (dir == Direction.UP)
			p.setLocation(p.getX()+10, p.getY());
		else if (dir == Direction.DOWN)
			p.setLocation(p.getX()-10, p.getY());
		else if (dir == Direction.LEFT)
			p.setLocation(p.getX(), p.getY()-10);
		else if (dir == Direction.RIGHT)
			p.setLocation(p.getX(), p.getY()+10);
		return p;
	}
	
	//from the POV of the snake head
	public Point simStraight(){
		Direction dir = gp.getDirection();
		Point p = gp.getSnekHeadLoc();
		if (dir == Direction.UP)
			p.setLocation(p.getX(), p.getY()-10);
		else if (dir == Direction.DOWN)
			p.setLocation(p.getX(), p.getY()+10);
		else if (dir == Direction.LEFT)
			p.setLocation(p.getX()-10, p.getY());
		else if (dir == Direction.RIGHT)
			p.setLocation(p.getX()+10, p.getY());
		return p;
	}
	
	public double valueFunc(Point p){
		if (p.equals(this.gp.getFoodDotLoc()))
			return Integer.MAX_VALUE;
		else if (p.getX() < 40 || p.getX() > 530 || p.getY() < 40 || p.getY() > 530)
			return Integer.MIN_VALUE;
		// TODO: this contains can be more efficient
		if (this.gp.isSnekPoint(p))
			return Integer.MIN_VALUE;
		return -getDistToDot(p);
	}
	
	public String pointString(Point p){
		return "(" + p.getX() + ", " + p.getY() + ")";
	}
}
