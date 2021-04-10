package eu.happycoders.pathfinding.floyd_warshall;

import java.util.*;

/**
 * Cost and successor matrices for the Floyd-Warshall Algorithm.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class FloydWarshallMatrices {

  private final int n;
  private final String[] nodes;
  private final Map<String, Integer> nodeNameToIndex;

  final int[][] costs;
  final int[][] successors;

  /**
   * Creates the matrices for the given array of nodes names.
   *
   * @param nodes the node names
   */
  public FloydWarshallMatrices(String[] nodes) {
    this.n = nodes.length;
    this.nodes = nodes;

    // Create lookup map from node name to node index
    Map<String, Integer> temp = new HashMap<>();
    for (int i = 0; i < n; i++) {
      temp.put(nodes[i], i);
    }
    this.nodeNameToIndex = Collections.unmodifiableMap(temp);

    // Create cost and successor matrix
    this.costs = new int[n][n];
    this.successors = new int[n][n];
  }

  /**
   * Returns the cost from source to destination nodes, referenced by their names.
   *
   * @param source the source node name
   * @param dest the destination node name
   * @return the cost from source to destination node
   */
  public int getCost(String source, String dest) {
    return costs[nodeNameToIndex.get(source)][nodeNameToIndex.get(dest)];
  }

  /**
   * Returns the shortest path from source to destination nodes, referenced by their names.
   *
   * @param source the source node name
   * @param dest the destination node name
   * @return the shortest path from source to destination node, if it exists; an empty optional
   *     otherwise
   */
  public Optional<List<String>> getPath(String source, String dest) {
    int i = nodeNameToIndex.get(source);
    int j = nodeNameToIndex.get(dest);

    // Check for -1 in case there's no path from source to dest
    if (successors[i][j] == -1) {
      return Optional.empty();
    }

    List<String> path = new ArrayList<>();
    path.add(nodes[i]);

    while (i != j) {
      i = successors[i][j];
      path.add(nodes[i]);
    }

    return Optional.of(List.copyOf(path));
  }

  /** Prints the cost and successor matrices to the console. */
  public void print() {
    printCosts();
    printSuccessors();
  }

  private void printCosts() {
    System.out.println("Costs:");

    printHeader();

    for (int rowNo = 0; rowNo < n; rowNo++) {
      System.out.printf("%5s", nodes[rowNo]);

      for (int colNo = 0; colNo < n; colNo++) {
        int cost = costs[rowNo][colNo];
        if (cost == Integer.MAX_VALUE) System.out.print("    ∞");
        else System.out.printf("%5d", cost);
      }
      System.out.println();
    }
  }

  private void printSuccessors() {
    System.out.println("Successors:");

    printHeader();

    for (int rowNo = 0; rowNo < n; rowNo++) {
      System.out.printf("%5s", nodes[rowNo]);

      for (int colNo = 0; colNo < n; colNo++) {
        int successor = successors[rowNo][colNo];
        String nextNode = successor != -1 ? nodes[successor] : "-";
        System.out.printf("%5s", nextNode);
      }
      System.out.println();
    }
  }

  private void printHeader() {
    System.out.print("     ");
    for (int colNo = 0; colNo < n; colNo++) {
      System.out.printf("%5s", nodes[colNo]);
    }
    System.out.println();
  }
}
