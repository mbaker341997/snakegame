import java.awt.Point;

public class SnakeBot {

	private final GameInnerPanel gameInnerPanel;
	
	public SnakeBot(GameInnerPanel gameInnerPanel) {
		this.gameInnerPanel = gameInnerPanel;
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
		this.gameInnerPanel.startGame();
		while (!this.gameInnerPanel.getGameOver() && this.gameInnerPanel.getGameStarted()) {
			atP = this.gameInnerPanel.getSnake().getHeadAsPoint();
			lastDir = this.gameInnerPanel.getDirection();
			
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
		System.out.println("Snake died at"
				+ pointString(this.gameInnerPanel.getSnake().getHeadAsPoint()));
		System.out.println("I expected to be at" + pointString(nextP));
		System.out.println("Last Move: " + lastMove);
		System.out.println("Right Value: " + rightValue);
		System.out.println("Left Value: " + leftValue);
		System.out.println("Straight Value: " + straightValue);
	}

	//manhattan distance
	public double getDistToDot(Point p) {
		FoodDot dot = this.gameInnerPanel.getDot();
		return Math.abs(p.getX() - dot.getX()) + Math.abs(p.getY() - dot.getY());
	}

	// TODO: all of these move methods can be refactored
	
	//from the POV of the snake head
	public void moveLeft() {
		//System.out.println("moving left");
		Direction dir = this.gameInnerPanel.getDirection();
		if (dir == Direction.UP)
			this.gameInnerPanel.move(Direction.LEFT);
		else if (dir == Direction.DOWN)
			this.gameInnerPanel.move(Direction.RIGHT);
		else if (dir == Direction.LEFT)
			this.gameInnerPanel.move(Direction.DOWN);
		else if (dir == Direction.RIGHT)
			this.gameInnerPanel.move(Direction.UP);
	}
	
	//from the POV of the snake head
	public void moveRight() {
		//System.out.println("moving right");
		Direction dir = this.gameInnerPanel.getDirection();
		if (dir == Direction.UP)
			this.gameInnerPanel.move(Direction.RIGHT);
		else if (dir == Direction.DOWN)
			this.gameInnerPanel.move(Direction.LEFT);
		else if (dir == Direction.LEFT)
			this.gameInnerPanel.move(Direction.UP);
		else if (dir == Direction.RIGHT)
			this.gameInnerPanel.move(Direction.DOWN);
	}	
	
	//from the POV of the snake head
	public void stayStraight() {
		//System.out.println("Staying straight");
		Direction dir = this.gameInnerPanel.getDirection();
		if (dir == Direction.UP)
			this.gameInnerPanel.move(Direction.UP);
		else if (dir == Direction.DOWN)
			this.gameInnerPanel.move(Direction.DOWN);
		else if (dir == Direction.LEFT)
			this.gameInnerPanel.move(Direction.LEFT);
		else if (dir == Direction.RIGHT)
			this.gameInnerPanel.move(Direction.RIGHT);
	}
	
	//from the POV of the snake head
	// TODO: move direction deltas to constants or some utils
	public Point simLeftMove() {
		Direction dir = this.gameInnerPanel.getDirection();
		Point p = this.gameInnerPanel.getSnake().getHeadAsPoint();
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
		Direction dir = this.gameInnerPanel.getDirection();
		Point p = this.gameInnerPanel.getSnake().getHeadAsPoint();
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
		Direction dir = this.gameInnerPanel.getDirection();
		Point p = this.gameInnerPanel.getSnake().getHeadAsPoint();
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
	
	public double valueFunc(Point p) {
		FoodDot dot = this.gameInnerPanel.getDot();
		if (p.getX() == dot.getX() && p.getY() == dot.getY())
			return 10000.0;
		else if (p.getX() < 40 || p.getX() > 530 || p.getY() < 40 || p.getY() > 530)
			return -10000.0;
		if (this.gameInnerPanel.getSnake().getTail().contains(p))
			return -10000.0;
		// TODO: look ahead more than one step (will i back my head into a corner)?
		return -getDistToDot(p);
	}
	
	public String pointString(Point p){
		return "(" + p.getX() + ", " + p.getY() + ")";
	}
}
