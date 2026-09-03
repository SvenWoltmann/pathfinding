package eu.happycoders.pathfinding.floyd_warshall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
class TestWithSampleGraph {

  @ParameterizedTest(name = "shortest path from {0} to {1} is {2} with cost {3}")
  @CsvSource({
    "A, B, 'A B', 2",
    "A, C, 'A B C', 8",
    "C, B, 'C B', 7",
    "D, A, 'D E A', 4",
    "D, B, 'D E A B', 6",
    "D, C, 'D C', 1",
    "E, C, 'E A B C', 9",
  })
  void findShortestPaths_sampleGraph_returnsTheExpectedPathAndCost(
      String source, String target, String expectedPath, int expectedCost) {
    ValueGraph<String, Integer> graph = createSampleGraph();

    FloydWarshallMatrices shortestPaths = FloydWarshall.findShortestPaths(graph, false);

    assertEquals(
        Optional.of(List.of(expectedPath.split(" "))), shortestPaths.getPath(source, target));
    assertEquals(expectedCost, shortestPaths.getCost(source, target));
  }

  @Test
  void findShortestPaths_sampleGraph_hasNoPathFromAToD() {
    ValueGraph<String, Integer> graph = createSampleGraph();

    FloydWarshallMatrices shortestPaths = FloydWarshall.findShortestPaths(graph, false);

    assertTrue(shortestPaths.getPath("A", "D").isEmpty());
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
