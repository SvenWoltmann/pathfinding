package eu.happycoders.pathfinding.fatcat;

import eu.happycoders.pathfinding.fatcat.algorithm.CatAlgorithm;
import eu.happycoders.pathfinding.fatcat.algorithm.CatAlgorithmFrom1990;
import eu.happycoders.pathfinding.fatcat.algorithm.CatAlgorithmFrom2020;
import eu.happycoders.pathfinding.fatcat.algorithm.CatAlgorithmFrom2020Opt;
import eu.happycoders.pathfinding.fatcat.common.GameState;
import eu.happycoders.pathfinding.fatcat.common.LabFactory;

/**
 * Tests the 1990 and 2020 cat algorithms with random positions for cat and mouse.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
@SuppressWarnings({"squid:S106", "PMD.SystemPrintln"}) // System.out is OK in this test program
public class CatAlgorithmsTest {

  public static void main(String[] args) {
    testWith(new CatAlgorithmFrom1990());
    testWith(new CatAlgorithmFrom2020());
    testWith(new CatAlgorithmFrom2020Opt());
  }

  private static void testWith(CatAlgorithm algorithm) {
    System.out.printf("Algorithm: %s%n%n", algorithm.getClass().getSimpleName());

    GameState gameState = new GameState(LabFactory.createLab1()).withRandomCatMousePositions();

    System.out.printf("%nStep  0: catDir = null%n%n");
    gameState.printLab();

    int step = 0;
    while (!gameState.hasCatEatenMouse()) {
      gameState = algorithm.moveCat(gameState);
      System.out.printf("%nStep %2d: catDir = %s%n%n", ++step, gameState.getCatDir());
      gameState.printLab();
    }
  }
}
