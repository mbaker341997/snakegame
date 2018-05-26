import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class SnakeBot {

	private Robot bot;
	
	public SnakeBot() {
		try {
			bot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void solveGame()
	{
		bot.keyPress(KeyEvent.VK_RIGHT);
	}

}
