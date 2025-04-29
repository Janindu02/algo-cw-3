import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * MaxFlow class implements the Ford-Fulkerson algorithm with BFS (Edmonds-Karp)
 * to find the maximum flow in a network.
 */
public class MaxFlow {
    private Graph graph;
    private int source;
    private int sink;
    private List<List<Integer>> augmentingPaths;

    /**
     * Constructor for MaxFlow
     *
     * @param graph  The flow network
     * @param source The source node
     * @param sink   The sink/target node
     */
    public MaxFlow(Graph graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
        this.augmentingPaths = new ArrayList<>();
    }

    /**
     * Find an augmenting path from source to sink using BFS
     *
     * @return Array of parent edges or null if no path exists
     */
    private Edge[] findAugmentingPath() {
        int numNodes = graph.getNumNodes();
        boolean[] visited = new boolean[numNodes];
        Edge[] parentEdge = new Edge[numNodes];

        Arrays.fill(visited, false);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty() && !visited[sink]) {
            int current = queue.poll();

            // Explore all outgoing edges
            for (Edge edge : graph.getOutgoingEdges(current)) {
                int next = edge.getDestination();
                if (!visited[next] && edge.getResidualCapacity() > 0) {
                    parentEdge[next] = edge;
                    visited[next] = true;
                    queue.add(next);
                }
            }

            // Explore all incoming edges (for residual backward edges)
            for (Edge edge : graph.getIncomingEdges(current)) {
                int next = edge.getSource();
                if (!visited[next] && edge.getFlow() > 0) {
                    parentEdge[next] = edge;  // This represents a backward edge in the residual graph
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }

        return visited[sink] ? parentEdge : null;
    }

    /**
     * Finds the maximum flow from source to sink using Edmonds-Karp algorithm
     *
     * @return The maximum flow value
     */
    public int findMaxFlow() {
        // Reset any existing flows
        graph.resetFlows();
        augmentingPaths.clear();

        int maxFlow = 0;
        int pathNum = 1;

        // Find augmenting paths and push flow along them
        Edge[] parentEdge;
        while ((parentEdge = findAugmentingPath()) != null) {
            // Find the bottleneck capacity (minimum residual capacity along the path)
            int bottleneck = findBottleneckCapacity(parentEdge);

            // Augment flow along the path
            augmentFlow(parentEdge, bottleneck);

            // Record the augmenting path
            List<Integer> path = reconstructPath(parentEdge);
            augmentingPaths.add(path);

            // Print the augmenting path
            System.out.print("Path " + pathNum + ": ");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println(" with flow = " + bottleneck);

            maxFlow += bottleneck;
            pathNum++;
        }

        return maxFlow;
    }

    /**
     * Find the bottleneck capacity along an augmenting path
     *
     * @param parentEdge Array of parent edges defining the path
     * @return The bottleneck capacity
     */
    private int findBottleneckCapacity(Edge[] parentEdge) {
        int bottleneck = Integer.MAX_VALUE;
        int current = sink;

        while (current != source) {
            Edge edge = parentEdge[current];

            if (edge.getDestination() == current) {
                // Forward edge
                bottleneck = Math.min(bottleneck, edge.getResidualCapacity());
                current = edge.getSource();
            } else {
                // Backward edge
                bottleneck = Math.min(bottleneck, edge.getFlow());
                current = edge.getDestination();
            }
        }

        return bottleneck;
    }

    /**
     * Augment flow along the path by the bottleneck amount
     *
     * @param parentEdge Array of parent edges defining the path
     * @param bottleneck The bottleneck capacity
     */
    private void augmentFlow(Edge[] parentEdge, int bottleneck) {
        int current = sink;

        while (current != source) {
            Edge edge = parentEdge[current];

            if (edge.getDestination() == current) {
                // Forward edge - increase flow
                edge.addFlow(bottleneck);
                current = edge.getSource();
            } else {
                // Backward edge - decrease flow
                edge.setFlow(edge.getFlow() - bottleneck);
                current = edge.getDestination();
            }
        }
    }

    /**
     * Reconstruct the augmenting path from the parent edges
     *
     * @param parentEdge Array of parent edges
     * @return List of nodes in the path from source to sink
     */
    private List<Integer> reconstructPath(Edge[] parentEdge) {
        List<Integer> path = new ArrayList<>();
        int current = sink;

        path.add(current);

        while (current != source) {
            Edge edge = parentEdge[current];

            if (edge.getDestination() == current) {
                current = edge.getSource();
            } else {
                current = edge.getDestination();
            }

            path.add(0, current);
        }

        return path;
    }

    /**
     * Get the list of augmenting paths found during the algorithm
     *
     * @return List of augmenting paths
     */
    public List<List<Integer>> getAugmentingPaths() {
        return augmentingPaths;
    }
}