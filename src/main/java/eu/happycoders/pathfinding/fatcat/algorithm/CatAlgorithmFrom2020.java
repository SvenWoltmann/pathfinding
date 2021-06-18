package eu.happycoders.pathfinding.fatcat.algorithm;

import eu.happycoders.pathfinding.fatcat.common.*;
import java.util.*;

/**
 * Shortest-path algorithm for finding the shortest path from cat to mouse in the labyrinth.
 *
 * <p>Rewrite from 2020.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CatAlgorithmFrom2020 implements CatAlgorithm {

  /**
   * Finds the shortest path from cat to mouse, and moves the cat one step on this path.
   *
   * @param gameState the game state before moving the cat
   * @return the game state after moving the cat (with updated cat position and direction)
   */
  public GameState moveCat(GameState gameState) {
    Direction catDir = gameState.getCatDir();

    // Cat changes direction only on every 2nd field
    if (gameState.getCx() % 2 == 1 && gameState.getCy() % 2 == 1) {
      catDir =
          findShortestPathToMouse(
              gameState.getLab(),
              gameState.getCx(),
              gameState.getCy(),
              gameState.getMx(),
              gameState.getMy());
    }

    // Create new game state with moved cat
    return gameState.withMoveCat(catDir);
  }

  /**
   * Finds the shortest path from cat to mouse in the given labyrinth.
   *
   * @param lab the labyrinth's matrix with walls indicated by {@code true}
   * @param cx the cat's X coordinate
   * @param cy the cat's Y coordinate
   * @param mx the mouse's X coordinate
   * @param my the mouse's Y coordinate
   * @return the direction of the shortest path
   */
  private Direction findShortestPathToMouse(boolean[][] lab, int cx, int cy, int mx, int my) {
    // Create a queue for all nodes we will process in breadth-first order.
    // Each node is a data structure containing the cat's position and the
    // initial direction it took to reach this point.
    Queue<Node> queue = new ArrayDeque<>();

    // Matrix for "discovered" fields
    // (I know we're wasting a few bytes here as the cat and mouse can never
    // reach the outer border, but it will make the code easier to read. Another
    // solution would be to not store the outer border at all - neither here nor
    // in the labyrinth. But then we'd need additional checks in the code
    // whether the outer border is reached.)
    boolean[][] discovered = new boolean[23][31];

    // "Discover" and enqueue the cat's start position
    discovered[cy][cx] = true;
    queue.add(new Node(cx, cy, null));

    while (!queue.isEmpty()) {
      Node node = queue.poll();

      // Go breath-first into each direction
      for (Direction dir : Direction.values()) {
        int newX = node.x + dir.getDx();
        int newY = node.y + dir.getDy();
        Direction newDir = node.initialDir == null ? dir : node.initialDir;

        // Mouse found?
        if (newX == mx && newY == my) {
          return newDir;
        }

        // Is there a path in the direction (= is it a free field in the labyrinth)?
        // And has that field not yet been discovered?
        if (!lab[newY][newX] && !discovered[newY][newX]) {
          // "Discover" and enqueue that field
          discovered[newY][newX] = true;
          queue.add(new Node(newX, newY, newDir));
        }
      }
    }

    throw new IllegalStateException("No path found");
  }

  private static class Node {
    final int x;
    final int y;
    final Direction initialDir;

    public Node(int x, int y, Direction initialDir) {
      this.x = x;
      this.y = y;
      this.initialDir = initialDir;
    }
  }
}
