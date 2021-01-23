package eu.happycoders.pathfinding.dijkstra;

/**
 * Data structure containing a node, it's total distance from the start and its predecessor.
 *
 * <p>Used by {@link DijkstraWithPriorityQueue}.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
class NodeWrapper<N> implements Comparable<NodeWrapper<N>> {
  private final N node;
  private int totalDistance;
  private NodeWrapper<N> predecessor;

  NodeWrapper(N node, int totalDistance, NodeWrapper<N> predecessor) {
    this.node = node;
    this.totalDistance = totalDistance;
    this.predecessor = predecessor;
  }

  N getNode() {
    return node;
  }

  void setTotalDistance(int totalDistance) {
    this.totalDistance = totalDistance;
  }

  public int getTotalDistance() {
    return totalDistance;
  }

  public void setPredecessor(NodeWrapper<N> predecessor) {
    this.predecessor = predecessor;
  }

  public NodeWrapper<N> getPredecessor() {
    return predecessor;
  }

  @Override
  public int compareTo(NodeWrapper<N> o) {
    return Integer.compare(this.totalDistance, o.totalDistance);
  }

  // Using identity for equals and hashcode here, which is much faster.
  // It's sufficient as within the algorithm, we have only one NodeWrapper instance per node.

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
