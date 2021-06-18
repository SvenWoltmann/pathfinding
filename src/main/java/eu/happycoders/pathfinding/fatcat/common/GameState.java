package eu.happycoders.pathfinding.fatcat.common;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Simplified game state containing the labyrinth and the mouse and cat coordinates.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
@SuppressWarnings({"squid:S106", "PMD.SystemPrintln"}) // System.out is OK in this test program
public class GameState {

  private final boolean[][] lab;
  private final int mx, my, cx, cy; // Mouse and cat coordinates
  private Direction catDir = null; // Cat's direction

  public GameState(boolean[][] lab) {
    this.lab = lab;

    // Position mouse at the left center
    this.mx = 1;
    this.my = 11;

    // Position cat at the right center
    this.cx = 21;
    this.cy = 11;
  }

  public GameState(boolean[][] lab, int mx, int my, int cx, int cy, Direction catDir) {
    this.lab = lab;
    this.mx = mx;
    this.my = my;
    this.cx = cx;
    this.cy = cy;
    this.catDir = catDir;
  }

  public GameState withRandomCatMousePositions() {
    // Position cat on any even field
    int cx = 1 + 2 * ThreadLocalRandom.current().nextInt(14);
    int cy = 1 + 2 * ThreadLocalRandom.current().nextInt(10);

    int mx;
    int my;

    // Repeat in case mouse is positioned on a labyrinth wall or on the cat
    do {
      mx = 1 + ThreadLocalRandom.current().nextInt(28);
      my = 1 + ThreadLocalRandom.current().nextInt(20);
    } while (lab[my][mx] || mx == cx && my == cy);
    return new GameState(this.lab, mx, my, cx, cy, null);
  }

  public GameState withMoveCat(Direction dir) {
    return new GameState(
        this.lab, this.mx, this.my, this.cx + dir.getDx(), this.cy + dir.getDy(), dir);
  }

  public boolean[][] getLab() {
    return lab;
  }

  public int getMx() {
    return mx;
  }

  public int getMy() {
    return my;
  }

  public int getCx() {
    return cx;
  }

  public int getCy() {
    return cy;
  }

  public Direction getCatDir() {
    return catDir;
  }

  public boolean hasCatEatenMouse() {
    return cx == mx && cy == my;
  }

  public void printLab() {
    for (int y = 0; y < lab.length; y++) {
      boolean[] row = lab[y];
      for (int x = 0; x < row.length; x++) {
        if (y == cy && x == cx) {
          System.out.print("Ca");
        } else if (y == my && x == mx) {
          System.out.print("Mo");
        } else if (row[x]) {
          System.out.print("██");
        } else {
          System.out.print("  ");
        }
      }
      System.out.println();
    }
  }
}
