import java.util.Map;
import java.util.Set;

public enum Direction {
  UP,
  DOWN,
  LEFT,
  RIGHT;

  // set of directions where making a move in this direction is a no-op
  public static final Map<Direction, Set<Direction>> NO_OP_SET= Map.of(
      Direction.UP, Set.of(Direction.UP, Direction.DOWN),
      Direction.DOWN, Set.of(Direction.UP, Direction.DOWN),
      Direction.LEFT, Set.of(Direction.LEFT, Direction.RIGHT),
      Direction.RIGHT, Set.of(Direction.LEFT, Direction.RIGHT)
  );
}
