package eu.happycoders.pathfinding.dijkstra;

/**
 * Data structure containing a node, it's total distance to the target and its predecessor.
 *
 * <p>Used by {@link DijkstraWithTreeSet}.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
class NodeWrapperForTreeSet<N extends Comparable<N>>
    implements Comparable<NodeWrapperForTreeSet<N>> {
  private final N node;
  private int totalDistance;
  private NodeWrapperForTreeSet<N> predecessor;

  NodeWrapperForTreeSet(N node, int totalDistance, NodeWrapperForTreeSet<N> predecessor) {
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

  public void setPredecessor(NodeWrapperForTreeSet<N> predecessor) {
    this.predecessor = predecessor;
  }

  public NodeWrapperForTreeSet<N> getPredecessor() {
    return predecessor;
  }

  @Override
  public int compareTo(NodeWrapperForTreeSet<N> o) {
    int compare = Integer.compare(this.totalDistance, o.totalDistance);
    if (compare == 0) {
      compare = node.compareTo(o.node);
    }
    return compare;
  }

  // Using identity for equals and hashcode here, which is much faster.
  // It's sufficient as within the algorithm, we have only one NodeWrapperForTreeSet instance per
  // node.

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
