package eu.happycoders.pathfinding.floyd_warshall;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

/**
 * Tests the implementation of the Floyd-Warshall Algorithm using the following sample graph
 * containing a negative cycle:
 *
 * <pre>
 *                +--->( C )----+
 *               1|             |2
 *                |             |
 *                |             v
 *  ( A )------>( B )<--------( D )------>( E )
 *          5           -4            3
 * </pre>
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class TestWithNegativeCycle {
  public static void main(String[] args) {
    ValueGraph<String, Integer> graph = createSampleGraph();
    FloydWarshall.findShortestPaths(graph, true);
  }

  private static ValueGraph<String, Integer> createSampleGraph() {
    MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed().build();
    graph.putEdgeValue("A", "B", 5);
    graph.putEdgeValue("B", "C", 1);
    graph.putEdgeValue("C", "D", 2);
    graph.putEdgeValue("D", "B", -4);
    graph.putEdgeValue("D", "E", 3);
    return graph;
  }
}
