package eu.happycoders.pathfinding.astar;

import com.google.common.graph.ValueGraph;
import java.util.*;
import java.util.function.Function;

/**
 * Implementation of the A* algorithm with a {@link PriorityQueue} and a data structure holding the
 * actual node, its total distance and its predecessor ({@link AStarNodeWrapper}).
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class AStarWithPriorityQueue {

  /**
   * Finds the shortest path from {@code source} to {@code target}.
   *
   * @param graph the graph
   * @param source the source node
   * @param target the target node
   * @param heuristic the heuristic function mapping a node to its the minimal remaining costs
   * @return the shortest path; or {@code null} if no path was found
   */
  public static <N extends Comparable<N>> List<N> findShortestPath(
      ValueGraph<N, Double> graph, N source, N target, Function<N, Double> heuristic) {
    Map<N, AStarNodeWrapper<N>> nodeWrappers = new HashMap<>();
    PriorityQueue<AStarNodeWrapper<N>> queue = new PriorityQueue<>();
    Set<N> shortestPathFound = new HashSet<>();

    // Add source to queue
    AStarNodeWrapper<N> sourceWrapper =
        new AStarNodeWrapper<>(source, null, 0.0, heuristic.apply(source));
    nodeWrappers.put(source, sourceWrapper);
    queue.add(sourceWrapper);

    while (!queue.isEmpty()) {
      AStarNodeWrapper<N> nodeWrapper = queue.poll();
      N node = nodeWrapper.getNode();
      shortestPathFound.add(node);

      // Have we reached the target? --> Build and return the path
      if (node.equals(target)) {
        return buildPath(nodeWrapper);
      }

      // Iterate over all neighbors
      Set<N> neighbors = graph.adjacentNodes(node);
      for (N neighbor : neighbors) {
        // Ignore neighbor if shortest path already found
        if (shortestPathFound.contains(neighbor)) {
          continue;
        }

        // Calculate total cost from start to neighbor via current node
        double cost = graph.edgeValue(node, neighbor).orElseThrow(IllegalStateException::new);
        double totalCostFromStart = nodeWrapper.getTotalCostFromStart() + cost;

        // Neighbor not yet discovered?
        AStarNodeWrapper<N> neighborWrapper = nodeWrappers.get(neighbor);
        if (neighborWrapper == null) {
          neighborWrapper =
              new AStarNodeWrapper<>(
                  neighbor, nodeWrapper, totalCostFromStart, heuristic.apply(neighbor));
          nodeWrappers.put(neighbor, neighborWrapper);
          queue.add(neighborWrapper);
        }

        // Neighbor discovered, but total cost via current node is lower?
        // --> Update costs and predecessor
        else if (totalCostFromStart < neighborWrapper.getTotalCostFromStart()) {
          neighborWrapper.setTotalCostFromStart(totalCostFromStart);
          neighborWrapper.setPredecessor(nodeWrapper);

          // The position in the PriorityQueue won't change automatically;
          // we have to remove and reinsert the node.
          queue.remove(neighborWrapper);
          queue.add(neighborWrapper);
        }
      }
    }

    // All nodes were visited but the target was not found
    return null;
  }

  private static <N extends Comparable<N>> List<N> buildPath(AStarNodeWrapper<N> nodeWrapper) {
    List<N> path = new ArrayList<>();
    while (nodeWrapper != null) {
      path.add(nodeWrapper.getNode());
      nodeWrapper = nodeWrapper.getPredecessor();
    }
    Collections.reverse(path);
    return path;
  }
}
