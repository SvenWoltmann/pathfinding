package eu.happycoders.pathfinding.fatcat;

import eu.happycoders.pathfinding.fatcat.algorithm.CatAlgorithm;
import eu.happycoders.pathfinding.fatcat.algorithm.CatAlgorithmFrom1990;
import eu.happycoders.pathfinding.fatcat.algorithm.CatAlgorithmFrom2020;
import eu.happycoders.pathfinding.fatcat.algorithm.CatAlgorithmFrom2020Opt;
import eu.happycoders.pathfinding.fatcat.common.GameState;
import eu.happycoders.pathfinding.fatcat.common.LabFactory;

/**
 * Demonstrates the 1990 and 2020 cat algorithms with random positions for cat and mouse: prints the
 * maze and the cat's steps to the mouse.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
@SuppressWarnings({"squid:S106", "PMD.SystemPrintln"}) // System.out is OK in this demo program
public class CatAlgorithmsDemo {

  public static void main(String[] args) {
    demoWith(new CatAlgorithmFrom1990());
    demoWith(new CatAlgorithmFrom2020());
    demoWith(new CatAlgorithmFrom2020Opt());
  }

  private static void demoWith(CatAlgorithm algorithm) {
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
