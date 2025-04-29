import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parser class for reading network flow problems from files.
 */
public class Parser {

    /**
     * Parse a file describing a flow network and create a Graph object
     *
     * @param filename The name of the file to parse
     * @return A Graph object representing the flow network
     * @throws IOException If there is an error reading the file
     */
    public static Graph parseFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        // Read number of nodes
        int numNodes = Integer.parseInt(reader.readLine().trim());

        // Create a new graph with the specified number of nodes
        Graph graph = new Graph(numNodes);

        // Read edges
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 3) {
                int source = Integer.parseInt(parts[0]);
                int destination = Integer.parseInt(parts[1]);
                int capacity = Integer.parseInt(parts[2]);

                graph.addEdge(source, destination, capacity);
            }
        }

        reader.close();
        return graph;
    }
}