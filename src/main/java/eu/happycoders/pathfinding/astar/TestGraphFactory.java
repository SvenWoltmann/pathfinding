package eu.happycoders.pathfinding.astar;

import com.google.common.graph.*;
import java.util.concurrent.ThreadLocalRandom;

public class TestGraphFactory {

  public static NodeWithXYCoordinates[] createNodes(int numNodes) {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    // Add all nodes with random X and Y position from 0 to 10,000
    // (and store them in a temporary array, so we can access them in the next step)
    NodeWithXYCoordinates[] nodes = new NodeWithXYCoordinates[numNodes];
    for (int i = 0; i < numNodes; i++) {
      NodeWithXYCoordinates node =
          new NodeWithXYCoordinates(
              "Node" + i, random.nextDouble(10_000), random.nextDouble(10_000));
      nodes[i] = node;
    }

    return nodes;
  }

  public static ValueGraph<NodeWithXYCoordinates, Double> createGraph(
      NodeWithXYCoordinates[] nodes, int numEdges) {
    MutableValueGraph<NodeWithXYCoordinates, Double> graph = ValueGraphBuilder.undirected().build();

    for (NodeWithXYCoordinates node : nodes) {
      graph.addNode(node);
    }

    // Add random edges with costs at a random speed between 15 and 100
    ThreadLocalRandom random = ThreadLocalRandom.current();
    int numNodes = nodes.length;
    for (int i = 0; i < numEdges; i++) {
      int nodeIndex1 = random.nextInt(numNodes);
      int nodeIndex2;
      do {
        nodeIndex2 = random.nextInt(numNodes);
      } while (nodeIndex2 == nodeIndex1);

      NodeWithXYCoordinates node1 = nodes[nodeIndex1];
      NodeWithXYCoordinates node2 = nodes[nodeIndex2];

      double speed = random.nextDouble(15, 100);
      double distance = HeuristicForNodesWithXYCoordinates.calculateEuclideanDistance(node1, node2);
      double cost = distance / speed;

      graph.putEdgeValue(node1, node2, cost);
    }
    return graph;
  }
}
