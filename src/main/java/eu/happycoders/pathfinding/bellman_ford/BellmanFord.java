package eu.happycoders.pathfinding.bellman_ford;

import com.google.common.graph.*;

import java.util.*;

/**
 * Implementation of the Bellman Ford Algorithm.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class BellmanFord {

  /**
   * Finds the shortest path from {@code source} to {@code target}.
   *
   * @param graph the graph
   * @param source the source node
   * @param target the target node
   * @param <N> the node type
   * @return the shortest path; or {@code null} if no path was found
   * @throws IllegalArgumentException if a negative cycle was discovered
   */
  public static <N> List<N> findShortestPath(ValueGraph<N, Integer> graph, N source, N target) {
    Map<N, NodeWrapper<N>> nodeWrappers = new HashMap<>();

    // Add all node wrappers to the table
    for (N node : graph.nodes()) {
      int initialCostFromStart = node.equals(source) ? 0 : Integer.MAX_VALUE;
      NodeWrapper<N> nodeWrapper = new NodeWrapper<>(node, initialCostFromStart, null);
      nodeWrappers.put(node, nodeWrapper);
    }

    // Iterate n-1 times + 1 time for the negative cycle detection
    int n = graph.nodes().size();
    for (int i = 0; i < n; i++) {
      // Last iteration for detecting negative cycles?
      boolean lastIteration = i == n - 1;

      boolean atLeastOneChange = false;

      // For all edges...
      for (EndpointPair<N> edge : graph.edges()) {
        NodeWrapper<N> edgeSourceWrapper = nodeWrappers.get(edge.source());
        int totalCostToEdgeSource = edgeSourceWrapper.getTotalCostFromStart();
        // Ignore edge if no path to edge source was found so far
        if (totalCostToEdgeSource == Integer.MAX_VALUE) continue;

        // Calculate total cost from start via edge source to edge target
        int cost = graph.edgeValue(edge).orElseThrow(IllegalStateException::new);
        int totalCostToEdgeTarget = totalCostToEdgeSource + cost;

        // Cheaper path found?
        // a) regular iteration --> Update total cost and predecessor
        // b) negative cycle detection --> throw exception
        NodeWrapper edgeTargetWrapper = nodeWrappers.get(edge.target());
        if (totalCostToEdgeTarget < edgeTargetWrapper.getTotalCostFromStart()) {
          if (lastIteration) {
            throw new IllegalArgumentException("Negative cycle detected");
          }

          edgeTargetWrapper.setTotalCostFromStart(totalCostToEdgeTarget);
          edgeTargetWrapper.setPredecessor(edgeSourceWrapper);
          atLeastOneChange = true;
        }
      }

      // Optimization: terminate if nothing was changed
      if (!atLeastOneChange) break;
    }

    // Path found?
    NodeWrapper<N> targetNodeWrapper = nodeWrappers.get(target);
    if (targetNodeWrapper.getPredecessor() != null) {
      return buildPath(targetNodeWrapper);
    } else {
      return null;
    }
  }

  private static <N> List<N> buildPath(NodeWrapper<N> nodeWrapper) {
    List<N> path = new ArrayList<>();
    while (nodeWrapper != null) {
      path.add(nodeWrapper.getNode());
      nodeWrapper = nodeWrapper.getPredecessor();
    }
    Collections.reverse(path);
    return path;
  }
}
