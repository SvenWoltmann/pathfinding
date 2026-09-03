package eu.happycoders.pathfinding.floyd_warshall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.junit.jupiter.api.Test;

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
class TestWithNegativeCycle {

  @Test
  void findShortestPaths_graphWithNegativeCycle_throwsException() {
    ValueGraph<String, Integer> graph = createSampleGraph();

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> FloydWarshall.findShortestPaths(graph, false));

    assertEquals("Graph has a negative cycle", exception.getMessage());
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
