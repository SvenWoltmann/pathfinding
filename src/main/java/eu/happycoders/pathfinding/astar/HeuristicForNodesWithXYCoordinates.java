package eu.happycoders.pathfinding.astar;

import com.google.common.graph.*;
import org.slf4j.*;

import java.util.function.Function;

public class HeuristicForNodesWithXYCoordinates implements Function<NodeWithXYCoordinates, Double> {

  private static final Logger LOG =
      LoggerFactory.getLogger(HeuristicForNodesWithXYCoordinates.class);

  private final double maxSpeed;
  private final NodeWithXYCoordinates target;

  /**
   * Constructs the heuristic function for the specified graph and target node.
   *
   * @param graph the graph
   * @param target the target node
   */
  public HeuristicForNodesWithXYCoordinates(
      ValueGraph<NodeWithXYCoordinates, Double> graph, NodeWithXYCoordinates target) {
    // We need the maximum speed possible on any path in the graph for the heuristic function to
    // calculate the cost for a euclidean distance
    this.maxSpeed = calculateMaxSpeed(graph);
    this.target = target;
  }

  /**
   * Calculates the maximum speed possible on any path in the graph. The speed of a path is
   * calculated as: euclidean distance between the path's nodes, divided by its cost.
   *
   * @param graph the graph
   * @return the maximum speed
   */
  private static double calculateMaxSpeed(ValueGraph<NodeWithXYCoordinates, Double> graph) {
    return graph.edges().stream()
        .map(edge -> calculateSpeed(graph, edge))
        .max(Double::compare)
        .get();
  }

  /**
   * Calculates the speed on a path in the graph. The speed of a path is calculated as: euclidean
   * distance between the path's nodes, divided by its cost.
   *
   * @param graph the graph
   * @param edge the edge (= path)
   * @return the speed
   */
  private static double calculateSpeed(
      ValueGraph<NodeWithXYCoordinates, Double> graph, EndpointPair<NodeWithXYCoordinates> edge) {
    double euclideanDistance = calculateEuclideanDistance(edge.nodeU(), edge.nodeV());
    double cost = graph.edgeValue(edge).get();
    double speed = euclideanDistance / cost;

    LOG.debug(
        "Calculated speed from {} to {}: euclideanDistance = {}, cost = {} --> speed = {}",
        edge.nodeU(),
        edge.nodeV(),
        euclideanDistance,
        cost,
        speed);

    return speed;
  }

  public static double calculateEuclideanDistance(
      NodeWithXYCoordinates source, NodeWithXYCoordinates target) {
    double distanceX = target.getX() - source.getX();
    double distanceY = target.getY() - source.getY();
    return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
  }

  /**
   * Applies the heuristic function to the specified node.
   *
   * @param node the node
   * @return the minimum cost for traveling from the specified node to the target
   */
  @Override
  public Double apply(NodeWithXYCoordinates node) {
    double euclideanDistance = calculateEuclideanDistance(node, target);
    double minimumCost = euclideanDistance / maxSpeed;

    LOG.debug(
        "Applied heuristic to node {}: euclideanDistance = {}, maxSpeed = {} --> minimumCost = {}",
        node,
        euclideanDistance,
        maxSpeed,
        minimumCost);

    return minimumCost;
  }
}
