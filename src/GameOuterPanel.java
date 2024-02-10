import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import javax.swing.JPanel;

public class GameOuterPanel extends JPanel {
  private final GameInnerPanel innerPanel;
  private final boolean robotMode;

  public GameOuterPanel(boolean robotMode) {
    this.robotMode = robotMode;
    this.innerPanel = new GameInnerPanel(this.robotMode);
    this.addKeyListener(new ArrowListener());
    this.setFocusable(true);
    this.setFocusTraversalKeysEnabled(false);

    // put it all together
    this.setLayout(new BorderLayout());
    this.add(this.innerPanel, BorderLayout.CENTER);
    // TODO: a toggle button on top for switching between modes?
  }

  public void startGame() {
    this.innerPanel.startGame();
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
        boolean gameWasStarted = GameOuterPanel.this.innerPanel.getGameStarted();
        GameOuterPanel.this.innerPanel.reset();
        if (GameOuterPanel.this.robotMode && !gameWasStarted) {
          GameOuterPanel.this.startGame();
        }
      } else if (GameOuterPanel.this.innerPanel.actionPermitted()
          && !GameOuterPanel.this.robotMode) {
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
