import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graph class represents a flow network.
 * It contains nodes and edges with their capacities.
 */
public class Graph {
    private int numNodes;    // Number of nodes in the graph
    private List<Edge> edges;    // List of all edges
    private Map<Integer, List<Edge>> outgoingEdges;    // Map of outgoing edges for each node
    private Map<Integer, List<Edge>> incomingEdges;    // Map of incoming edges for each node

    /**
     * Constructor for Graph
     *
     * @param numNodes Number of nodes in the graph
     */
    public Graph(int numNodes) {
        this.numNodes = numNodes;
        this.edges = new ArrayList<>();
        this.outgoingEdges = new HashMap<>();
        this.incomingEdges = new HashMap<>();

        // Initialize the adjacency lists for each node
        for (int i = 0; i < numNodes; i++) {
            outgoingEdges.put(i, new ArrayList<>());
            incomingEdges.put(i, new ArrayList<>());
        }
    }

    /**
     * Add an edge to the graph
     *
     * @param source      Source node
     * @param destination Destination node
     * @param capacity    Capacity of the edge
     */
    public void addEdge(int source, int destination, int capacity) {
        Edge edge = new Edge(source, destination, capacity);
        edges.add(edge);
        outgoingEdges.get(source).add(edge);
        incomingEdges.get(destination).add(edge);
    }

    /**
     * Get the number of nodes in the graph
     *
     * @return Number of nodes
     */
    public int getNumNodes() {
        return numNodes;
    }

    /**
     * Get all edges in the graph
     *
     * @return List of all edges
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Get all outgoing edges for a node
     *
     * @param node The node to get outgoing edges for
     * @return List of outgoing edges
     */
    public List<Edge> getOutgoingEdges(int node) {
        return outgoingEdges.get(node);
    }

    /**
     * Get all incoming edges for a node
     *
     * @param node The node to get incoming edges for
     * @return List of incoming edges
     */
    public List<Edge> getIncomingEdges(int node) {
        return incomingEdges.get(node);
    }

    /**
     * Reset all flows in the graph to zero
     */
    public void resetFlows() {
        for (Edge edge : edges) {
            edge.setFlow(0);
        }
    }

    /**
     * Calculate the total flow coming out of the source
     *
     * @param source The source node
     * @return Total outgoing flow from source
     */
    public int calculateTotalFlow(int source) {
        int totalFlow = 0;
        for (Edge edge : outgoingEdges.get(source)) {
            totalFlow += edge.getFlow();
        }
        return totalFlow;
    }

    /**
     * Print the current state of the graph with flows
     */
    public void printGraph() {
        for (Edge edge : edges) {
            System.out.println(edge);
        }
    }
}