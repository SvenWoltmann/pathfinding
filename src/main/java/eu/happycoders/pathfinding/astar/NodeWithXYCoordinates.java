package eu.happycoders.pathfinding.astar;

import java.util.Objects;

/**
 * Data structure for graph nodes containing a name and X/Y coordinates. The name is the "primary
 * key" and used in the equals(), hashCode() and compareTo() method as unique ID.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class NodeWithXYCoordinates implements Comparable<NodeWithXYCoordinates> {
  private final String name;
  private final double x;
  private final double y;

  public NodeWithXYCoordinates(String name, double x, double y) {
    this.name = name;
    this.x = x;
    this.y = y;
  }

  public String getName() {
    return name;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    NodeWithXYCoordinates aStarNode = (NodeWithXYCoordinates) o;

    return Objects.equals(name, aStarNode.name);
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }

  @Override
  public int compareTo(NodeWithXYCoordinates o) {
    return name.compareTo(o.name);
  }

  @Override
  public String toString() {
    return name;
  }
}
