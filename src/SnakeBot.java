import java.awt.Point;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class SnakeBot {
  private static final Map<Direction, Direction> LEFT_MAP = Map.of(
      Direction.UP, Direction.LEFT,
      Direction.DOWN, Direction.RIGHT,
      Direction.LEFT, Direction.DOWN,
      Direction.RIGHT, Direction.UP
  );

  private static final Map<Direction, Direction> RIGHT_MAP = Map.of(
      Direction.UP, Direction.RIGHT,
      Direction.DOWN, Direction.LEFT,
      Direction.LEFT, Direction.UP,
      Direction.RIGHT, Direction.DOWN
  );

  public SnakeBot() {

  }

  public Direction determineNextMove(Snake snake, FoodDot foodDot, Direction currentDir) {
    Point atP = snake.getHeadAsPoint();

    double rightValue = valueFunc(
        simMove(atP, RIGHT_MAP.get(currentDir)),
        foodDot,
        snake,
        RIGHT_MAP.get(currentDir)
    );
    double leftValue = valueFunc(
        simMove(atP, LEFT_MAP.get(currentDir)),
        foodDot,
        snake,
        LEFT_MAP.get(currentDir)
    );
    double straightValue = valueFunc(
        simMove(atP, currentDir),
        foodDot,
        snake,
        currentDir
    );

    if (straightValue >= rightValue && straightValue >= leftValue) {
      return currentDir;
    } else if (rightValue >= leftValue && rightValue >= straightValue) {
      return RIGHT_MAP.get(currentDir);
    } else {
      return LEFT_MAP.get(currentDir);
    }
  }

  public Point simMove(Point currentPoint, Direction direction) {
    int[] delta = Direction.DELTA_MAP.get(direction);
    return new Point(currentPoint.x + delta[0], currentPoint.y + delta[1]);
  }

  public double valueFunc(
      Point p,
      FoodDot foodDot,
      Snake snake,
      Direction dir
  ) {
    if (p.getX() == foodDot.getX() && p.getY() == foodDot.getY())
      return 10000.0;
    else if (!GameInnerPanel.isInBounds(p.x, p.y))
      return -10000.0;
    if (snake.getTail().contains(p))
      return -10000.0;
    // TODO: look ahead more than one step (will i back my head into a corner)?
    return -getDistToDot(p, foodDot) + lookAhead(p, snake, foodDot, dir);
  }

  public double getDistToDot(Point p, FoodDot foodDot) {
    return Math.abs(p.getX() - foodDot.getX())
        + Math.abs(p.getY() - foodDot.getY());
  }

  // if you kept going in this direciton, would you hit food, wall, or self first?
  public double lookAhead(Point p, Snake snake, FoodDot foodDot, Direction dir) {
    Point checkP = p;
    while (GameInnerPanel.isInBounds(checkP.x, checkP.y)) {
      // if it's food, then good score return positive number
      if (checkP.getX() == foodDot.getX() && checkP.getY() == foodDot.getY()) {
        return 10000.0;
      } else if (snake.getTail().contains(checkP)) {
        return -500.0;
      }
      checkP = simMove(checkP, dir);
    }
    // wall, return 0
    return 0.0;
  }
}
