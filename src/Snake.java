import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

//the actual Snake Object
public class Snake {

	private int x, y;
	private ArrayList<Point> tail;
	
	public Snake(int x, int y) {
		this.x = x;
		this.y = y;
		tail = new ArrayList<Point>();
	}
	
	public void setCoords(int xpos, int ypos){
		x = xpos;
		y = ypos;
	}
	
	//draw method draws the snake
	public void draw(Graphics page){
		page.setColor(Color.WHITE);
		page.fillRect(x, y, 10, 10);
		for(int i = 0; i < tail.size(); i++){
			page.fillRect(tail.get(i).x, tail.get(i).y, 10, 10);
		}
	}
	
	//check if it touched itself (hehe)
	public boolean touchedSelf(){
		for(Point p : tail){
			if(p.getX() == x && p.getY() == y)
				return true;
		}
		return false;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public ArrayList<Point> getTail(){
		return tail;
	}
	
	public int getTailLength(){
		return tail.size();
	}
	
	public void addToTail(int x, int y){
		tail.add(new Point(x, y));
	}
	
	public void chopOffEnd(){
		tail.remove(0);
	}

}
