package eu.happycoders.pathfinding.fatcat.common;

/**
 * Direction of the mouse or the cat.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public enum Direction {
  // Value "NO" from the original Pascal code is replaced by null
  UP(0, -1),
  RIGHT(1, 0),
  DOWN(0, 1),
  LEFT(-1, 0);

  private final int dx;
  private final int dy;

  Direction(int dx, int dy) {
    this.dx = dx;
    this.dy = dy;
  }

  public int getDx() {
    return dx;
  }

  public int getDy() {
    return dy;
  }
}
