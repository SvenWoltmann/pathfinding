package eu.happycoders.pathfinding.dijkstra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests Dijkstra's algorithm on the following sample graph:
 *
 * <pre>
 *       A
 *      / \
 *    2/   \3
 *    /     \
 *   / 3   1 \    5
 *  C-----D---E-------B
 *  |      \  |       |
 *  |      4\ |6      |
 *  |        \|       |
 * 2|         F       |15
 *  |         |       |
 *  |         |7      |
 *  |         |       |
 *  G---------H-------I
 *       4        3
 * </pre>
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
class TestWithSampleGraph {

  @ParameterizedTest(name = "shortest path from {0} to {1} is {2}")
  @CsvSource({
    "D, H, 'D C G H'",
    "A, F, 'A E D F'",
    "E, H, 'E D C G H'",
    "B, H, 'B E D C G H'",
    "B, I, 'B I'",
  })
  void findShortestPath_sampleGraph_returnsTheExpectedPath(
      String source, String target, String expectedPath) {
    ValueGraph<String, Integer> graph = createSampleGraph();

    List<String> shortestPath = DijkstraWithPriorityQueue.findShortestPath(graph, source, target);

    assertEquals(List.of(expectedPath.split(" ")), shortestPath);
  }

  @ParameterizedTest(name = "shortest path from {0} to {1} is {2}")
  @CsvSource({
    "D, H, 'D C G H'",
    "A, F, 'A E D F'",
    "E, H, 'E D C G H'",
    "B, H, 'B E D C G H'",
    "B, I, 'B I'",
  })
  void findShortestPath_sampleGraphWithLazyDeletion_returnsTheExpectedPath(
      String source, String target, String expectedPath) {
    ValueGraph<String, Integer> graph = createSampleGraph();

    List<String> shortestPath = DijkstraWithLazyDeletion.findShortestPath(graph, source, target);

    assertEquals(List.of(expectedPath.split(" ")), shortestPath);
  }

  private static ValueGraph<String, Integer> createSampleGraph() {
    MutableValueGraph<String, Integer> graph = ValueGraphBuilder.undirected().build();
    graph.putEdgeValue("A", "C", 2);
    graph.putEdgeValue("A", "E", 3);
    graph.putEdgeValue("B", "E", 5);
    graph.putEdgeValue("B", "I", 15);
    graph.putEdgeValue("C", "D", 3);
    graph.putEdgeValue("C", "G", 2);
    graph.putEdgeValue("D", "E", 1);
    graph.putEdgeValue("D", "F", 4);
    graph.putEdgeValue("E", "F", 6);
    graph.putEdgeValue("F", "H", 7);
    graph.putEdgeValue("G", "H", 4);
    graph.putEdgeValue("H", "I", 3);
    return graph;
  }
}
