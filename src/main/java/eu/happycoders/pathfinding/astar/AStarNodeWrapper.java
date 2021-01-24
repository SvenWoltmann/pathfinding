package eu.happycoders.pathfinding.astar;

/**
 * Data structure containing a node, its predecessor, its total cost from the start, its minimum
 * remaining cost, and the cost sum.
 *
 * <p>Used by {@link AStarWithTreeSet} and {@link AStarWithPriorityQueue}.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class AStarNodeWrapper<N extends Comparable<N>> implements Comparable<AStarNodeWrapper<N>> {
  private final N node;
  private AStarNodeWrapper<N> predecessor;
  private double totalCostFromStart;
  private final double minimumRemainingCostToTarget;
  private double costSum;

  public AStarNodeWrapper(
      N node,
      AStarNodeWrapper<N> predecessor,
      double totalCostFromStart,
      double minimumRemainingCostToTarget) {
    this.node = node;
    this.predecessor = predecessor;
    this.totalCostFromStart = totalCostFromStart;
    this.minimumRemainingCostToTarget = minimumRemainingCostToTarget;
    calculateCostSum();
  }

  private void calculateCostSum() {
    this.costSum = this.totalCostFromStart + this.minimumRemainingCostToTarget;
  }

  public N getNode() {
    return node;
  }

  public void setPredecessor(AStarNodeWrapper<N> predecessor) {
    this.predecessor = predecessor;
  }

  public AStarNodeWrapper<N> getPredecessor() {
    return predecessor;
  }

  public void setTotalCostFromStart(double totalCostFromStart) {
    this.totalCostFromStart = totalCostFromStart;
    calculateCostSum();
  }

  public double getTotalCostFromStart() {
    return totalCostFromStart;
  }

  @Override
  public int compareTo(AStarNodeWrapper<N> o) {
    int compare = Double.compare(this.costSum, o.costSum);
    if (compare == 0) {
      compare = node.compareTo(o.node);
    }
    return compare;
  }

  // Using identity for equals and hashcode here, which is much faster.
  // It's sufficient as within the algorithm, we have only one AStarNodeWrapper
  // instance per node.

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "AStarNodeWrapperForTreeSet{"
        + "node="
        + node
        + ", predecessor="
        + (predecessor != null ? predecessor.getNode().toString() : "")
        + ", totalCostFromStart="
        + totalCostFromStart
        + ", minimumRemainingCostToTarget="
        + minimumRemainingCostToTarget
        + ", costSum="
        + costSum
        + '}';
  }
}
