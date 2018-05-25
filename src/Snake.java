import java.awt.Graphics;

//the actual Snake Object
public class Snake {

	private int x, y, length;
	
	public Snake(int x, int y) {
		this.x = x;
		this.y = y;
		length = 1;
	}
	
	//draw method draws the snake
	public void draw(Graphics page){
		page.fillRect(x, y, 10, 10);
	}

}
