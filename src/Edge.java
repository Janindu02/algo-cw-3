/**
 * Edge class represents a directed edge in a flow network.
 * It stores the source and destination nodes, capacity and current flow.
 */
public class Edge {
    private int source;      // Source node
    private int destination; // Destination node
    private int capacity;    // Maximum capacity of the edge
    private int flow;        // Current flow along the edge

    /**
     * Constructor for Edge
     *
     * @param source      Source node
     * @param destination Destination node
     * @param capacity    Maximum capacity of the edge
     */
    public Edge(int source, int destination, int capacity) {
        this.source = source;
        this.destination = destination;
        this.capacity = capacity;
        this.flow = 0;  // Initialize flow to 0
    }

    /**
     * Get the source node of this edge
     *
     * @return The source node
     */
    public int getSource() {
        return source;
    }

    /**
     * Get the destination node of this edge
     *
     * @return The destination node
     */
    public int getDestination() {
        return destination;
    }

    /**
     * Get the capacity of this edge
     *
     * @return The capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Get the current flow along this edge
     *
     * @return The flow
     */
    public int getFlow() {
        return flow;
    }

    /**
     * Set the flow along this edge
     *
     * @param flow The new flow value
     */
    public void setFlow(int flow) {
        this.flow = flow;
    }

    /**
     * Get the residual capacity of this edge
     *
     * @return The remaining capacity (capacity - flow)
     */
    public int getResidualCapacity() {
        return capacity - flow;
    }

    /**
     * Add more flow to this edge
     *
     * @param additionalFlow The amount of flow to add
     */
    public void addFlow(int additionalFlow) {
        this.flow += additionalFlow;
    }

    /**
     * String representation of the edge
     *
     * @return String in format "source->destination (flow/capacity)"
     */
    @Override
    public String toString() {
        return source + "->" + destination + " (" + flow + "/" + capacity + ")";
    }
}