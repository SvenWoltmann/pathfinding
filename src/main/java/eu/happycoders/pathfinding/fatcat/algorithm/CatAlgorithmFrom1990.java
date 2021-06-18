package eu.happycoders.pathfinding.fatcat.algorithm;

import eu.happycoders.pathfinding.fatcat.common.Direction;
import eu.happycoders.pathfinding.fatcat.common.GameState;

/**
 * Shortest-path algorithm for finding the shortest path from cat to mouse in the labyrinth.
 *
 * <p>Java adaption of my Turbo Pascal code from 1990.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
@SuppressWarnings("squid:S2259") // I don't want to fix my code from 1990 ;-)
public class CatAlgorithmFrom1990 implements CatAlgorithm {

  // Global variables in the Turbo Pascal code
  private boolean[][] lab; // the labyrinth; 1-based coordinates
  private Direction catDir; // cat's direction
  private byte mx, my, cx, cy; // mouse and cat coordinates

  // Variables that were local to moveCat()
  // Had to make them class-level as we cannot nest methods in Java
  short dirDataPos, newDirDataPos, ways;
  byte col, line;

  // Data structure holding direction and coordinates reached for potential paths
  Direction[] dir = new Direction[100];
  byte[] wayX = new byte[100];
  byte[] wayY = new byte[100];

  // Temporary data structure holding direction and coordinates reached in the next step
  Direction[] newDir = new Direction[100];
  byte[] newWayX = new byte[100];
  byte[] newWayY = new byte[100];

  boolean[][] p = new boolean[32][24]; // visited fields (1-based coordinates)

  private void firstMoveTest(byte dx, byte dy, Direction dir2) {
    // Mouse not on the target field?
    if (cx + dx != mx || cy + dy != my) {
      // Can the cat go there?
      if (!lab[cy + dy][cx + dx]) {
        dirDataPos++;
        wayX[dirDataPos] = (byte) (cx + 2 * dx);
        wayY[dirDataPos] = (byte) (cy + 2 * dy);
        dir[dirDataPos] = dir2;

        // Mark the next node in this direction as visited
        p[cx + 2 * dx][cy + 2 * dy] = true;
      }
    } else catDir = dir2; // Cat reached the mouse --> Set direction
  }

  private void secondMoveTest(byte dx, byte dy) {
    // Mouse not on the target field?
    if (wayX[dirDataPos] + dx != mx || wayY[dirDataPos] + dy != my) {
      // Can the cat go there + have we not tried this field yet?
      if (!lab[wayY[dirDataPos] + dy][wayX[dirDataPos] + dx]
          && !p[wayX[dirDataPos] + 2 * dx][wayY[dirDataPos] + 2 * dy]) {
        newDirDataPos++;
        newWayX[newDirDataPos] = (byte) (wayX[dirDataPos] + 2 * dx);
        newWayY[newDirDataPos] = (byte) (wayY[dirDataPos] + 2 * dy);

        // The direction the cat took to come here stays the same
        newDir[newDirDataPos] = dir[dirDataPos];

        // Mark next node in this direction as visited
        p[wayX[dirDataPos] + 2 * dx][wayY[dirDataPos] + 2 * dy] = true;
      }
    } else catDir = dir[dirDataPos]; // Cat reached the mouse --> Set direction
  }

  public void moveCat() {
    // Cat changes direction only on every 2nd field
    if (cx % 2 == 0 && cy % 2 == 0) {
      // Mark all fields as "not visited"
      for (col = 1; col <= 31; col++) {
        for (line = 1; line <= 23; line++) {
          p[col][line] = false;
        }
      }

      dirDataPos = 0;
      catDir = null;
      p[cx][cy] = true; // Mark current cat position as "visited"

      // Try one step in each direction
      firstMoveTest((byte) 1, (byte) 0, Direction.RIGHT);
      firstMoveTest((byte) -1, (byte) 0, Direction.LEFT);
      firstMoveTest((byte) 0, (byte) 1, Direction.DOWN);
      firstMoveTest((byte) 0, (byte) -1, Direction.UP);

      ways = dirDataPos; // number of found paths

      // Is the mouse on the next node in any of the found directions? Go there!
      for (dirDataPos = 1; dirDataPos <= ways; dirDataPos++) {
        if (wayX[dirDataPos] == mx && wayY[dirDataPos] == my) {
          catDir = dir[dirDataPos];
        }
      }

      // No direction found after 1 or 2 steps?
      while (catDir == null && (mx != cx || my != cy)) {
        newDirDataPos = 0;
        // From each field we've reached so far... try one step in each direction
        for (dirDataPos = 1; dirDataPos <= ways; dirDataPos++) {
          secondMoveTest((byte) 1, (byte) 0);
          secondMoveTest((byte) -1, (byte) 0);
          secondMoveTest((byte) 0, (byte) 1);
          secondMoveTest((byte) 0, (byte) -1);
        }

        ways = newDirDataPos; // number of found paths

        // Copy data from temporary data structure back to permanent structure.
        // Is the mouse on the next node in any of the found directions? Go there!
        for (dirDataPos = 1; dirDataPos <= newDirDataPos; dirDataPos++) {
          wayX[dirDataPos] = newWayX[dirDataPos];
          wayY[dirDataPos] = newWayY[dirDataPos];
          dir[dirDataPos] = newDir[dirDataPos];
          if (wayX[dirDataPos] == mx && wayY[dirDataPos] == my) {
            catDir = dir[dirDataPos];
          }
        }
      }
    }

    // deleteCat();

    switch (catDir) {
      case LEFT -> cx--;
      case DOWN -> cy++;
      case RIGHT -> cx++;
      case UP -> cy--;
    }

    // drawCat();
  }

  // -- Additional methods to test the algorithm --

  public GameState moveCat(GameState gameState) {
    // Copy game state to fields (so that the actual moveCat() method can be as close as possible to
    // the original Turbo Pascal code)

    // In the original code, coordinates were 1-based
    this.lab = toOneBased(gameState.getLab());
    this.mx = (byte) (1 + gameState.getMx());
    this.my = (byte) (1 + gameState.getMy());
    this.cx = (byte) (1 + gameState.getCx());
    this.cy = (byte) (1 + gameState.getCy());

    this.catDir = gameState.getCatDir();

    // Move cat
    moveCat();

    // Return new game state
    return new GameState(gameState.getLab(), mx - 1, my - 1, cx - 1, cy - 1, catDir);
  }

  private boolean[][] toOneBased(boolean[][] lab0) {
    boolean[][] lab1 = new boolean[lab0.length + 1][];
    for (int y = 0; y < lab0.length; y++) {
      boolean[] row0 = lab0[y];
      boolean[] row1 = new boolean[row0.length + 1];
      for (int x = 0; x < row0.length; x++) {
        row1[x + 1] = row0[x];
      }
      lab1[y + 1] = row1;
    }
    return lab1;
  }
}
