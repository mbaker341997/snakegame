import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameInnerPanel extends JPanel {
  // Game core config
  private final boolean robotMode;
  private final Timer timer;

  // Major game objects
  private final Snake snake;
  private final FoodDot dot;
  private final SnakeBot snakeBot;

  // Game status
  private int score;
  private boolean gameOver;
  private boolean gameStarted;
  private Direction direction;
  private boolean makingMove;

  private static final int DEFAULT_X = 290;
  private static final int DEFAULT_Y = 290;
  private static final int START_DOT_X = DEFAULT_X + 50;
  private static final int MIN_X = 40;
  private static final int MAX_X = 530;
  private static final int MIN_Y = 40;
  private static final int MAX_Y = 530;

  public GameInnerPanel(boolean robotMode) {
    this.robotMode = robotMode;
    this.timer = new Timer(20, new TimerListener());

    this.setDefaultConfigParams();
    this.snake = new Snake(DEFAULT_X, DEFAULT_Y);
    this.dot = new FoodDot(START_DOT_X, DEFAULT_Y);
    this.snakeBot = new SnakeBot();
  }

  public void setDefaultConfigParams() {
    this.score = 0;
    this.gameStarted = false;
    this.gameOver = false;
    this.direction = Direction.RIGHT;
    this.makingMove = false;
  }

  public void reset() {
    this.setDefaultConfigParams();
    this.dot.setX(START_DOT_X);
    this.dot.setY(DEFAULT_Y);

    this.snake.setX(DEFAULT_X);
    this.snake.setY(DEFAULT_Y);
    this.snake.resetTail();
    this.timer.stop();
    this.repaint();
  }

  public boolean actionPermitted() {
    return !this.gameOver && !this.makingMove;
  }

  public boolean getGameStarted() {
    return this.gameStarted;
  }

  public void startGame() {
    this.gameStarted = true;
    this.timer.start();
  }

  public boolean hasEaten() {
    return this.dot.getX() == this.snake.getX()
        && this.dot.getY() == this.snake.getY();
  }

  public void move(Direction newDirection) {
    if (!Direction.NO_OP_SET.get(this.direction).contains(newDirection)
        || this.snake.getTail().isEmpty()) {
      this.direction = newDirection;
      this.makingMove = true;
    }
  }

  public static boolean isInBounds(int x, int y) {
    return x >= MIN_X && x <= MAX_X && y >= MIN_Y && y <= MAX_Y;
  }

  public void timerAction() {
    if (!isInBounds(this.snake.getX(), this.snake.getY())) {
      this.gameOver = true;
    } else {
      this.snake.addToTail(this.snake.getX(), this.snake.getY());

      if (Direction.DELTA_MAP.containsKey(this.direction)) {
        int[] delta = Direction.DELTA_MAP.get(this.direction);
        this.snake.setCoords(
            this.snake.getX() + delta[0],
            this.snake.getY() + delta[1]
        );
      }

      if (this.hasEaten()) {
        this.score++;
        this.dot.changePos(this.snake.getTail());
      } else {
        this.snake.chopOffEnd();
      }

      if (this.snake.touchedSelf()) {
        this.gameOver = true;
      }
    }

    if (this.gameOver) {
      this.timer.stop();
    }

    this.makingMove = false;
    this.repaint();

    // if robot mode then make the best direction change here
    if (this.robotMode) {
      this.direction = this.snakeBot.determineNextMove(
          this.snake,
          this.dot,
          this.direction
      );
    }
  }

  public void paintComponent(Graphics page) {
    super.paintComponent(page);
    setBackground(Color.BLACK);

    // set score label
    page.setFont(new Font(Font.SERIF, Font.BOLD, 25));
    page.setColor(Color.WHITE);
    page.drawString("Score: " + score, 40, 30);

    // robot mode label
    if (this.robotMode) {
      page.drawString("Robot Mode", 235, 30);
    }

    // bounds
    page.drawRect(40, 40, 500, 500);

    // draw the snake
    this.snake.draw(page);

    // draw the dot
    dot.draw(page);

    // show it game over if it is
    if (this.gameOver) {
      page.setFont(new Font(Font.SERIF, Font.BOLD, 50));
      page.setColor(Color.RED);
      page.drawString("GAME OVER", 145, 250);
    }
  }

  private class TimerListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      GameInnerPanel.this.timerAction();
    }
  }
}
