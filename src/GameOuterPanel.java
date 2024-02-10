import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import javax.swing.JPanel;

public class GameOuterPanel extends JPanel {
  private final GameInnerPanel innerPanel;
  private final boolean robotMode;
  private final SnakeBot snakeBot;

  public GameOuterPanel(boolean robotMode) {
    this.robotMode = robotMode;
    this.innerPanel = new GameInnerPanel(this.robotMode);
    this.addKeyListener(new ArrowListener());
    this.setFocusable(true);
    this.setFocusTraversalKeysEnabled(false);

    // put it all together
    this.setLayout(new BorderLayout());
    this.add(this.innerPanel, BorderLayout.CENTER);

    this.snakeBot = new SnakeBot(this.innerPanel);
    if (this.robotMode) {
      this.triggerSnakeBot();
    }
  }

  // TODO: instead of separate game thread, can probably have the robot run in the main game thread
  public void startGame() {
    this.innerPanel.startGame();
  }

  public void triggerSnakeBot() {
    new Thread(() -> {
      try {
        Thread.sleep(1000);
        GameOuterPanel.this.snakeBot.solveGame();
      } catch (InterruptedException e) {
        System.out.println("The exception: " + e);
      }
    }).start();
  }

  private class ArrowListener implements KeyListener {

    private static final Map<Integer, Direction> KEY_EVENT_DIRECTION_MAP = Map.of(
        KeyEvent.VK_UP, Direction.UP,
        KeyEvent.VK_DOWN, Direction.DOWN,
        KeyEvent.VK_LEFT, Direction.LEFT,
        KeyEvent.VK_RIGHT, Direction.RIGHT
    );


    @Override
    public void keyPressed(KeyEvent key) {
      if (key.getKeyCode() == KeyEvent.VK_SPACE) {
        // if the game's over, and you press space, restart
        GameOuterPanel.this.innerPanel.reset();
        if (GameOuterPanel.this.robotMode) {
          GameOuterPanel.this.triggerSnakeBot();
        }
      } else if (GameOuterPanel.this.innerPanel.actionPermitted() && !GameOuterPanel.this.robotMode) {
        // start the timer if the game has begun yet
        if (!GameOuterPanel.this.innerPanel.getGameStarted()) {
          GameOuterPanel.this.startGame();
        }

        if (KEY_EVENT_DIRECTION_MAP.containsKey(key.getKeyCode())) {
          GameOuterPanel.this.innerPanel.move(
              KEY_EVENT_DIRECTION_MAP.get(key.getKeyCode()));
        }
      }

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
      // Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
      // Auto-generated method stub

    }
  }
}
