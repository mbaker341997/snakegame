import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

// the actual Snake Object
public class Snake {
	private int x;
	private int y;
	private final List<Point> tail;
	private static final int SQUARE_SIDE = 10;
	
	public Snake(int x, int y) {
		this.x = x;
		this.y = y;
		this.tail = new ArrayList<>();
	}
	
	public void setCoords(int xpos, int ypos){
		this.x = xpos;
		this.y = ypos;
	}
	
	// draw method draws the snake
	public void draw(Graphics page) {
		page.setColor(Color.WHITE);
		page.fillRect(this.x, this.y, SQUARE_SIDE, SQUARE_SIDE);
		this.tail.forEach(point -> page.fillRect(point.x, point.y, SQUARE_SIDE, SQUARE_SIDE));
	}
	
	// check if it touched itself (hehe)
	public boolean touchedSelf() {
		return this.tail.stream().anyMatch(p -> p.getX() == this.x && p.getY() == this.y);
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

	public List<Point> getTail(){
		return tail;
	}

	public void addToTail(int x, int y){
		tail.add(new Point(x, y));
	}
	
	public void chopOffEnd(){
		tail.removeFirst();
	}

	public void resetTail() {
		this.tail.clear();
	}

	public Point getHeadAsPoint() {
		return new Point(this.x, this.y);
	}
}
