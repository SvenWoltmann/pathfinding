package eu.happycoders.pathfinding.fatcat;

import eu.happycoders.pathfinding.common.Statistics;
import eu.happycoders.pathfinding.fatcat.algorithm.*;
import eu.happycoders.pathfinding.fatcat.common.*;

import java.util.*;

/**
 * Compares the performance of the 1990 and 2020 cat algorithmss.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class CatAlgorithmsBenchmark {

  private static final int WARMUPS = 10;
  private static final int ITERATIONS = 20;

  private static CatAlgorithm algorithm1990 = new CatAlgorithmFrom1990();
  private static CatAlgorithm algorithm2020 = new CatAlgorithmFrom2020();
  private static CatAlgorithm algorithm2020Opt = new CatAlgorithmFrom2020Opt();

  private static Map<Class, List<Long>> timesMap = new HashMap<>();

  public static void main(String[] args) {
    runTests(WARMUPS, true);
    runTests(ITERATIONS, false);
  }

  private static void runTests(int iterations, boolean warmup) {
    for (int i = 0; i < iterations; i++) {
      System.out.printf(
          "----- %s iteration %d of %d -----%n", warmup ? "WARMUP" : "TEST", (i + 1), iterations);
      test(algorithm1990, warmup);
      test(algorithm2020, warmup);
      test(algorithm2020Opt, warmup);
    }
  }

  private static void test(CatAlgorithm algorithm, boolean warmup) {
    GameState gameState = new GameState(LabFactory.createLab1());

    long totalTime = 0;
    long blackhole = 0;

    for (int i = 0; i < 100_000; i++) {
      gameState = gameState.withRandomCatMousePositions();

      long t = System.nanoTime();
      gameState = algorithm.moveCat(gameState);
      totalTime += System.nanoTime() - t;

      blackhole += gameState.getCatDir().ordinal();
    }

    if (warmup) {
      System.out.printf(
          Locale.US,
          "%-23s --> totalTime = %.2f ms (blackhole = %d)%n",
          algorithm.getClass().getSimpleName(),
          totalTime / 1_000_000.0,
          blackhole);
    } else {
      long median = storeAndCalculateMedian(algorithm.getClass(), totalTime);
      System.out.printf(
          Locale.US,
          "%-23s --> totalTime = %.2f ms --> median = %.2f ms (blackhole = %d)%n",
          algorithm.getClass().getSimpleName(),
          totalTime / 1_000_000.0,
          median / 1_000_000.0,
          blackhole);
    }
  }

  private static long storeAndCalculateMedian(Class<? extends CatAlgorithm> algoClass, long time) {
    List<Long> times = timesMap.computeIfAbsent(algoClass, k -> new ArrayList<>());
    times.add(time);
    return Statistics.median(times);
  }
}
