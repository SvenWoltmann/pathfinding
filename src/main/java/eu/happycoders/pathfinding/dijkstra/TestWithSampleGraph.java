package eu.happycoders.pathfinding.dijkstra;

import com.google.common.graph.*;
import java.util.List;

/**
 * Tests the implementation of Dijkstra's algorithm using the following sample graph:
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
public class TestWithSampleGraph {
  public static void main(String[] args) {
    ValueGraph<String, Integer> graph = createSampleGraph();

    System.out.println("graph = " + graph);

    findAndPrintShortestPath(graph, "D", "H");
    findAndPrintShortestPath(graph, "A", "F");
    findAndPrintShortestPath(graph, "E", "H");
    findAndPrintShortestPath(graph, "B", "H");
    findAndPrintShortestPath(graph, "B", "I");
    findAndPrintShortestPath(graph, "E", "H");
  }

  private static void findAndPrintShortestPath(
      ValueGraph<String, Integer> graph, String source, String target) {
    List<String> shortestPath = DijkstraWithPriorityQueue.findShortestPath(graph, source, target);
    System.out.printf("shortestPath from %s to %s = %s%n", source, target, shortestPath);
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
