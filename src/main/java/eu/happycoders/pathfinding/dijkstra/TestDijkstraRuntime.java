package eu.happycoders.pathfinding.dijkstra;

import com.google.common.graph.*;
import eu.happycoders.pathfinding.common.Statistics;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TestDijkstraRuntime {

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
    List<String> shortestPath = null;
    long time = 0;
    while (shortestPath == null) {
      ValueGraph<String, Integer> graph = buildGraph(numNodes, numEdges);
      String source = nodeName(0);
      String target = nodeName(numNodes - 1);

      time = System.nanoTime();
      shortestPath = DijkstraWithPriorityQueue.findShortestPath(graph, source, target);
      time = System.nanoTime() - time;
    }
    blackhole += shortestPath.size();
    return time;
  }

  private static ValueGraph<String, Integer> buildGraph(int numNodes, int numEdges) {
    MutableValueGraph<String, Integer> graph = ValueGraphBuilder.undirected().build();

    // Add all nodes
    for (int i = 0; i < numNodes; i++) {
      graph.addNode(nodeName(i));
    }

    // Add random edges
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < numEdges; i++) {
      String node1 = nodeName(random.nextInt(numNodes));

      String node2;
      do {
        node2 = nodeName(random.nextInt(numNodes));
      } while (node2.equals(node1));

      int weight = random.nextInt(1, 10);

      graph.putEdgeValue(node1, node2, weight);
    }
    return graph;
  }

  private static String nodeName(int i) {
    return "Node" + i;
  }
}
