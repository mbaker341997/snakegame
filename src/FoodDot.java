import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Random;

//the thing we want the snake to eat
public class FoodDot {
	private int x;
	private int y;
	
	public FoodDot(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void changePos(List<Point> tail) {
		boolean success = false;
		do {
			int newX = (new Random().nextInt(53 - 4) + 4) * 10;
			int newY = (new Random().nextInt(53 -4 ) + 4) * 10;

			if (tail.stream().noneMatch(p -> newX == p.getX() && newY == p.getY())
					&& (newX != this.x || newY != this.y)) {
				this.x = newX;
				this.y = newY;
				success = true;
			}
		} while (!success);
	}
	
	//draw method draws the snake
	public void draw(Graphics page) {
		page.setColor(Color.YELLOW);
		page.fillRect(this.x, this.y, 10, 10);
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
