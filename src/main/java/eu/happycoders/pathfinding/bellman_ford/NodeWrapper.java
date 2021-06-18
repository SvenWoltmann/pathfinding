package eu.happycoders.pathfinding.bellman_ford;

/**
 * Data structure containing a node, it's total cost from the start and its predecessor.
 *
 * <p>Used by {@link BellmanFord}.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
class NodeWrapper<N> {
  private final N node;
  private int totalCostFromStart;
  private NodeWrapper<N> predecessor;

  NodeWrapper(N node, int totalCostFromStart, NodeWrapper<N> predecessor) {
    this.node = node;
    this.totalCostFromStart = totalCostFromStart;
    this.predecessor = predecessor;
  }

  N getNode() {
    return node;
  }

  void setTotalCostFromStart(int totalCostFromStart) {
    this.totalCostFromStart = totalCostFromStart;
  }

  public int getTotalCostFromStart() {
    return totalCostFromStart;
  }

  public void setPredecessor(NodeWrapper<N> predecessor) {
    this.predecessor = predecessor;
  }

  public NodeWrapper<N> getPredecessor() {
    return predecessor;
  }

  // Using identity for equals and hashcode here, which is much faster.
  // It's sufficient as within the algorithm, we have only one NodeWrapper instance per node.

  @Override
  public boolean equals(Object other) {
    return super.equals(other);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
