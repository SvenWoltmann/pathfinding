package eu.happycoders.pathfinding.dijkstra;

import com.google.common.graph.ValueGraph;

import java.util.*;

/**
 * Implementation of Dijkstra's algorithm with a {@link TreeSet} and a data structure holding the
 * actual node, its total distance and its predecessor ({@link NodeWrapper}).
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class DijkstraWithTreeSet {

  /**
   * Finds the shortest path from {@code source} to {@code target}.
   *
   * @param graph the graph
   * @param source the source node
   * @param target the target node
   * @param <N> the node type
   * @return the shortest path; or {@code null} if no path was found
   */
  public static <N extends Comparable<N>> List<N> findShortestPath(
      ValueGraph<N, Integer> graph, N source, N target) {
    Map<N, NodeWrapperForTreeSet<N>> nodeWrappers = new HashMap<>();
    TreeSet<NodeWrapperForTreeSet<N>> queue = new TreeSet<>();
    Set<N> shortestPathFound = new HashSet<>();

    // Add source to queue
    NodeWrapperForTreeSet<N> sourceWrapper = new NodeWrapperForTreeSet<>(source, 0, null);
    nodeWrappers.put(source, sourceWrapper);
    queue.add(sourceWrapper);

    while (!queue.isEmpty()) {
      NodeWrapperForTreeSet<N> nodeWrapper = queue.pollFirst();
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

        // Calculate total distance to neighbor via current node
        int distance = graph.edgeValue(node, neighbor).orElseThrow(IllegalStateException::new);
        int totalDistance = nodeWrapper.getTotalDistance() + distance;

        // Neighbor not yet discovered?
        NodeWrapperForTreeSet<N> neighborWrapper = nodeWrappers.get(neighbor);
        if (neighborWrapper == null) {
          neighborWrapper = new NodeWrapperForTreeSet<>(neighbor, totalDistance, nodeWrapper);
          nodeWrappers.put(neighbor, neighborWrapper);
          queue.add(neighborWrapper);
        }

        // Neighbor discovered, but total distance via current node is shorter?
        // --> Update total distance and predecessor
        else if (totalDistance < neighborWrapper.getTotalDistance()) {
          neighborWrapper.setTotalDistance(totalDistance);
          neighborWrapper.setPredecessor(nodeWrapper);

          // The position in the PriorityQueue won't change automatically;
          // we have to remove and reinsert the node
          queue.remove(neighborWrapper);
          queue.add(neighborWrapper);
        }
      }
    }

    // All nodes were visited but the target was not found
    return null;
  }

  private static <N extends Comparable<N>> List<N> buildPath(NodeWrapperForTreeSet<N> nodeWrapper) {
    List<N> path = new ArrayList<>();
    while (nodeWrapper != null) {
      path.add(nodeWrapper.getNode());
      nodeWrapper = nodeWrapper.getPredecessor();
    }
    Collections.reverse(path);
    return path;
  }
}
