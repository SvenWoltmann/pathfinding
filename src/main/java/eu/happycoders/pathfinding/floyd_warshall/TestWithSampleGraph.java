package eu.happycoders.pathfinding.floyd_warshall;

import com.google.common.graph.*;

/**
 * Tests the implementation of the Floyd-Warshall Algorithm using the following sample graph:
 *
 * <pre>
 *             2
 *  ( A )------------>( B )
 *    ^              ^ ^ |
 *    |            /   | |
 *    |          /     | |
 *   1|        /4     7| |6
 *    |      /         | |
 *    |    /           | |
 *    |  /             | v
 *  ( E )<---( D )--->( C )
 *         3       1
 * </pre>
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class TestWithSampleGraph {
  public static void main(String[] args) {
    ValueGraph<String, Integer> graph = createSampleGraph();
    FloydWarshall.findShortestPaths(graph, true);
  }

  private static ValueGraph<String, Integer> createSampleGraph() {
    MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed().build();
    graph.putEdgeValue("A", "B", 2);
    graph.putEdgeValue("B", "C", 6);
    graph.putEdgeValue("C", "B", 7);
    graph.putEdgeValue("D", "C", 1);
    graph.putEdgeValue("D", "E", 3);
    graph.putEdgeValue("E", "A", 1);
    graph.putEdgeValue("E", "B", 4);
    return graph;
  }
}
