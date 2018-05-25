import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

//the thing we want the snake to eat
public class FoodDot {
	
	private int x, y;
	
	public FoodDot(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void changePos(int notX, int notY){
		boolean success = false;
		do{
			int newX = (new Random().nextInt(53 - 4) + 4) * 10;
			int newY = (new Random().nextInt(53 -4 ) + 4) * 10;
			
			if(newX != x || newY != y){
				x = newX;
				y = newY;
				success = true;
			}
		}while(!success);
	}
	
	//draw method draws the snake
	public void draw(Graphics page){
		page.setColor(Color.YELLOW);
		page.fillRect(x, y, 10, 10);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

}
