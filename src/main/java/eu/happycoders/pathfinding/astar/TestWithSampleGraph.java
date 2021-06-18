package eu.happycoders.pathfinding.astar;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests the implementation of the A* algorithm using the following sample graph:
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

  private static final Logger LOG =
      LoggerFactory.getLogger(HeuristicForNodesWithXYCoordinates.class);

  public static void main(String[] args) {
    ValueGraph<NodeWithXYCoordinates, Double> graph = createSampleGraph();

    LOG.info("graph = {}", graph);

    Map<String, NodeWithXYCoordinates> nodeByName = createNodeByNameMap(graph);

    findAndPrintShortestPath(graph, nodeByName.get("D"), nodeByName.get("H"));
    findAndPrintShortestPath(graph, nodeByName.get("A"), nodeByName.get("F"));
    findAndPrintShortestPath(graph, nodeByName.get("E"), nodeByName.get("H"));
    findAndPrintShortestPath(graph, nodeByName.get("B"), nodeByName.get("H"));
    findAndPrintShortestPath(graph, nodeByName.get("B"), nodeByName.get("I"));
    findAndPrintShortestPath(graph, nodeByName.get("E"), nodeByName.get("H"));
  }

  private static ValueGraph<NodeWithXYCoordinates, Double> createSampleGraph() {
    MutableValueGraph<NodeWithXYCoordinates, Double> graph = ValueGraphBuilder.undirected().build();

    NodeWithXYCoordinates a = new NodeWithXYCoordinates("A", 2_410, 6_230);
    NodeWithXYCoordinates b = new NodeWithXYCoordinates("B", 8_980, 6_080);
    NodeWithXYCoordinates c = new NodeWithXYCoordinates("C", 560, 3_360);
    NodeWithXYCoordinates d = new NodeWithXYCoordinates("D", 2_980, 3_900);
    NodeWithXYCoordinates e = new NodeWithXYCoordinates("E", 4_220, 4_280);
    NodeWithXYCoordinates f = new NodeWithXYCoordinates("F", 4_000, 2_600);
    NodeWithXYCoordinates g = new NodeWithXYCoordinates("G", 0, 0);
    NodeWithXYCoordinates h = new NodeWithXYCoordinates("H", 4_850, 110);
    NodeWithXYCoordinates i = new NodeWithXYCoordinates("I", 7_500, 0);

    graph.putEdgeValue(a, c, 2.0);
    graph.putEdgeValue(a, e, 3.0);
    graph.putEdgeValue(b, e, 5.0);
    graph.putEdgeValue(b, i, 15.0);
    graph.putEdgeValue(c, d, 3.0);
    graph.putEdgeValue(c, g, 2.0);
    graph.putEdgeValue(d, e, 1.0);
    graph.putEdgeValue(d, f, 4.0);
    graph.putEdgeValue(e, f, 6.0);
    graph.putEdgeValue(f, h, 7.0);
    graph.putEdgeValue(g, h, 4.0);
    graph.putEdgeValue(h, i, 3.0);

    return graph;
  }

  private static Map<String, NodeWithXYCoordinates> createNodeByNameMap(
      ValueGraph<NodeWithXYCoordinates, Double> graph) {
    return graph.nodes().stream()
        .collect(Collectors.toMap(NodeWithXYCoordinates::getName, Function.identity()));
  }

  private static void findAndPrintShortestPath(
      ValueGraph<NodeWithXYCoordinates, Double> graph,
      NodeWithXYCoordinates source,
      NodeWithXYCoordinates target) {
    Function<NodeWithXYCoordinates, Double> heuristic =
        new HeuristicForNodesWithXYCoordinates(graph, target);
    List<NodeWithXYCoordinates> shortestPath =
        AStarWithTreeSet.findShortestPath(graph, source, target, heuristic);
    LOG.info("shortestPath from {} to {} = {}", source, target, shortestPath);
  }
}
