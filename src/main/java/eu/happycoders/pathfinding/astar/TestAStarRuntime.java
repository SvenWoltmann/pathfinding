package eu.happycoders.pathfinding.astar;

import com.google.common.graph.ValueGraph;
import eu.happycoders.pathfinding.common.Statistics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings({"squid:S106", "PMD.SystemPrintln"}) // System.out is OK in this test program
public class TestAStarRuntime {

  private static final int MAX_WARMUPS = 1;
  private static final int MAX_ITERATIONS = 100;

  private static final int MIN_NODES = 10_000;
  private static final int MAX_NODES = 310_000;

  // Let's assume that on a road map, an average of four roads depart from each intersection
  private static final int EDGES_PER_NODE = 4;

  private static final Map<Integer, List<Long>> TIMES = new HashMap<>();

  private static int blackhole = 0;

  public static void main(String[] args) {
    for (int i = 0; i < MAX_WARMUPS; i++) {
      runTests(i, true);
    }
    for (int i = 0; i < MAX_ITERATIONS; i++) {
      runTests(i, false);
    }
  }

  private static void runTests(int iteration, boolean warmup) {
    System.out.printf("%n%sIteration %d:%n", warmup ? "Warmup - " : "Test - ", iteration + 1);
    for (int numNodes = MIN_NODES;
        numNodes <= MAX_NODES;
        numNodes = Math.min((int) (numNodes * 1.5), numNodes + 25_000)) {
      int numEdges = numNodes * EDGES_PER_NODE;
      long time = runTestForGraphSize(numNodes, numEdges);
      System.out.printf(
          Locale.US,
          "Time for graph with %,7d nodes and %,9d edges = %,8.1f ms",
          numNodes,
          numEdges,
          time / 1_000_000.0);

      if (!warmup) {
        List<Long> times = TIMES.computeIfAbsent(numNodes, k -> new ArrayList<>());
        times.add(time);
        long median = Statistics.median(times);
        System.out.printf(
            Locale.US,
            "  -->  Median after %2d iterations = %,8.1f ms",
            iteration + 1,
            median / 1_000_000.0);
      }
      System.out.println();
    }
    System.out.println("blackhole = " + blackhole);
  }

  private static long runTestForGraphSize(int numNodes, int numEdges) {
    List<NodeWithXYCoordinates> shortestPath = null;
    long time = 0;
    while (shortestPath == null) {
      NodeWithXYCoordinates[] nodes = TestGraphFactory.createNodes(numNodes);

      ValueGraph<NodeWithXYCoordinates, Double> graph =
          TestGraphFactory.createGraph(nodes, numEdges);

      NodeWithXYCoordinates source = nodes[0];
      NodeWithXYCoordinates target = nodes[1];

      // Don't measure generating the heuristic function - it needs to be generated only once per
      // graph and can, for example, be included in a satnav's data.
      HeuristicForNodesWithXYCoordinates heuristic =
          new HeuristicForNodesWithXYCoordinates(graph, target);

      time = System.nanoTime();
      shortestPath = AStarWithTreeSet.findShortestPath(graph, source, target, heuristic);
      time = System.nanoTime() - time;
    }
    blackhole += shortestPath.size();
    return time;
  }
}
