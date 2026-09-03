package eu.happycoders.pathfinding.bellman_ford;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.List;
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
class TestWithSampleGraph {

  @ParameterizedTest(name = "shortest path from {0} to {1} is {2}")
  @CsvSource({
    "A, F, 'A D E B C F'",
    "C, D, 'C B E D'",
  })
  void findShortestPath_sampleGraph_returnsTheExpectedPath(
      String source, String target, String expectedPath) {
    ValueGraph<String, Integer> graph = createSampleGraph();

    List<String> shortestPath = BellmanFord.findShortestPath(graph, source, target);

    assertEquals(List.of(expectedPath.split(" ")), shortestPath);
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
