package eu.happycoders.pathfinding.floyd_warshall;

import com.google.common.graph.ValueGraph;
import java.util.Optional;

/**
 * Implementation of the Floyd-Warshall Algorithm.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
@SuppressWarnings({"squid:S106", "PMD.SystemPrintln"}) // Using System.out only for debug output
public class FloydWarshall {

  /**
   * Finds the shortest paths between all node pairs in the given graph.
   *
   * @param graph the graph
   * @param printDebugOutput if <code>true</code>, the cost and successor matrices are printed to
   *     std out after each iteration
   * @return a matrix containing the shortest distances between all node pairs
   * @throws IllegalArgumentException if a negative cycle was discovered
   */
  public static FloydWarshallMatrices findShortestPaths(
      ValueGraph<String, Integer> graph, boolean printDebugOutput) {
    // Preparation: Put all nodes into an array, so we have them indexed
    int n = graph.nodes().size();
    String[] nodes = graph.nodes().toArray(new String[n]);

    // Preparation: Create matrices
    FloydWarshallMatrices m = new FloydWarshallMatrices(nodes);

    // Preparation: Fill in direct paths
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        Optional<Integer> edgeValue = graph.edgeValue(nodes[i], nodes[j]);
        m.costs[i][j] = i == j ? 0 : edgeValue.orElse(Integer.MAX_VALUE);
        m.successors[i][j] = edgeValue.isPresent() ? j : -1;
      }
    }

    if (printDebugOutput) {
      System.out.println("\nMatrix after preparation:");
      m.print();
    }

    // Iterations
    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          int costViaNodeK = addCosts(m.costs[i][k], m.costs[k][j]);
          if (costViaNodeK < m.costs[i][j]) {
            m.costs[i][j] = costViaNodeK;
            m.successors[i][j] = m.successors[i][k];
          }
        }
      }

      if (printDebugOutput) {
        System.out.println("\nMatrices after k = " + k + ":");
        m.print();
      }
    }

    // Detect negative cycles
    for (int i = 0; i < n; i++) {
      if (m.costs[i][i] < 0) {
        throw new IllegalArgumentException("Graph has a negative cycle");
      }
    }

    return m;
  }

  private static int addCosts(int a, int b) {
    if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    }
    return a + b;
  }
}
