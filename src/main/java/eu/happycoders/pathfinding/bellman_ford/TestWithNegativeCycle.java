package eu.happycoders.pathfinding.bellman_ford;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.List;

/**
 * Tests the implementation of the Bellman Ford Algorithm using the following sample graph
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

    System.out.println("graph = " + graph);

    findAndPrintShortestPath(graph, "A", "E");
  }

  private static void findAndPrintShortestPath(
      ValueGraph<String, Integer> graph, String source, String target) {
    List<String> shortestPath = BellmanFord.findShortestPath(graph, source, target);
    System.out.printf("shortestPath from %s to %s = %s%n", source, target, shortestPath);
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
