import java.awt.Color;
import java.awt.Graphics;

//the actual Snake Object
public class Snake {

	private int x, y;
	
	public Snake(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setCoords(int xpos, int ypos){
		x = xpos;
		y = ypos;
	}
	
	//draw method draws the snake
	public void draw(Graphics page){
		page.setColor(Color.WHITE);
		page.fillRect(x, y, 10, 10);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	

}
