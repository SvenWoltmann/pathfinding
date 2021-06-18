package eu.happycoders.pathfinding.bellman_ford;

import com.google.common.graph.*;
import java.util.List;

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
public class TestWithSampleGraph {
  public static void main(String[] args) {
    ValueGraph<String, Integer> graph = createSampleGraph();

    System.out.println("graph = " + graph);

    findAndPrintShortestPath(graph, "A", "F");
    findAndPrintShortestPath(graph, "C", "D");
  }

  private static void findAndPrintShortestPath(
      ValueGraph<String, Integer> graph, String source, String target) {
    List<String> shortestPath = BellmanFord.findShortestPath(graph, source, target);
    System.out.printf("shortestPath from %s to %s = %s%n", source, target, shortestPath);
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
