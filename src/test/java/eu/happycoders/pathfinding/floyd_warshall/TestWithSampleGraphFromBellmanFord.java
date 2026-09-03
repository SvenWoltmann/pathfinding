package eu.happycoders.pathfinding.floyd_warshall;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests the implementation of the Bellman Ford Algorithm using the following sample graph:
 *
 * <pre>
 *          4           5
 *  ( A )------>( B )<----->( C )
 *   ^ |         ^ |         ^ |
 *   | |         | |         | |
 *   | |         | |         | |
 *  4| |3      -3| |4       4| |-2
 *   | |         | |         | |
 *   | |         | |         | |
 *   | v         | v         | v
 *  ( D )<----->( E )------>( F )
 *          3           2
 * </pre>
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
class TestWithSampleGraphFromBellmanFord {

  @Test
  void findShortestPaths_sampleGraph_returnsTheExpectedPathFromAToF() {
    ValueGraph<String, Integer> graph = createSampleGraph();

    FloydWarshallMatrices shortestPaths = FloydWarshall.findShortestPaths(graph, false);

    assertEquals(
        Optional.of(List.of("A", "D", "E", "B", "C", "F")), shortestPaths.getPath("A", "F"));
    assertEquals(6, shortestPaths.getCost("A", "F"));
  }

  @ParameterizedTest(name = "cost of the shortest path from {0} to {1} is {2}")
  @CsvSource({
    "A, B, 3",
    "B, A, 11",
    "C, F, -2",
    "E, B, -3",
    "F, A, 20",
  })
  void findShortestPaths_sampleGraph_returnsTheExpectedCosts(
      String source, String target, int expectedCost) {
    ValueGraph<String, Integer> graph = createSampleGraph();

    FloydWarshallMatrices shortestPaths = FloydWarshall.findShortestPaths(graph, false);

    assertEquals(expectedCost, shortestPaths.getCost(source, target));
  }

  private static ValueGraph<String, Integer> createSampleGraph() {
    MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed().build();
    graph.putEdgeValue("A", "B", 4);
    graph.putEdgeValue("A", "D", 3);
    graph.putEdgeValue("B", "C", 5);
    graph.putEdgeValue("B", "E", 4);
    graph.putEdgeValue("C", "B", 5);
    graph.putEdgeValue("C", "F", -2);
    graph.putEdgeValue("D", "A", 4);
    graph.putEdgeValue("D", "E", 3);
    graph.putEdgeValue("E", "B", -3);
    graph.putEdgeValue("E", "D", 3);
    graph.putEdgeValue("E", "F", 2);
    graph.putEdgeValue("F", "C", 4);
    return graph;
  }
}
