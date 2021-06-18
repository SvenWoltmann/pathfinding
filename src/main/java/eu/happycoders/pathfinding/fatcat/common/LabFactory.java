package eu.happycoders.pathfinding.fatcat.common;

/**
 * Factory for creating labyrinths.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class LabFactory {

  private static final int WIDTH = 31;
  private static final int HEIGHT = 23;

  /**
   * Creates labyrinth 1 from the original FatCat game on the HP-85.
   *
   * @return labyrinth 1
   */
  public static boolean[][] createLab1() {
    return parseLab(
        """
            ###############################
            #                             #
            # ############# ############# #
            # #   #   #   # #   #   #   # #
            # # # # # # # # # # # # # # # #
            # # # # # # # # # # # # # # # #
            # # # # # # # # # # # # # # # #
            # # #   # # #     # # #   # # #
            # # ##### # ####### # ##### # #
            # #     # #         # #     # #
            # ##### # ##### ##### # ##### #
            #       #             #       #
            # ##### # ##### ##### # ##### #
            # #     # #         # #     # #
            # # ##### # ####### # ##### # #
            # # #   # # #     # # #   # # #
            # # # # # # # # # # # # # # # #
            # # # # # # # # # # # # # # # #
            # # # # # # # # # # # # # # # #
            # #   #   #   # #   #   #   # #
            # ############# ############# #
            #                             #
            ###############################""");
  }

  private static boolean[][] parseLab(String labAsString) {
    String[] rows = labAsString.split("\\n");
    int height = rows.length;
    if (height != HEIGHT)
      throw new IllegalArgumentException("Labyrinth has " + height + " rows; expected: " + HEIGHT);

    boolean[][] lab = new boolean[HEIGHT][WIDTH];

    for (int y = 0; y < height; y++) {
      String row = rows[y];
      if (row.length() != WIDTH) {
        throw new IllegalArgumentException(
            "Row " + y + " has a width of " + row.length() + "; expected: " + WIDTH);
      }
      for (int x = 0; x < row.length(); x++) {
        lab[y][x] = row.charAt(x) != ' ';
      }
    }

    return lab;
  }
}
