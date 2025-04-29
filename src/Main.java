import java.io.IOException;
import java.util.Scanner;

/**
 * Main class for the Network Flow algorithm implementation.
 * This serves as the entry point for the program.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("***************************************");
        System.out.println("         Network Flow Algorithm        ");
        System.out.println("***************************************");
        System.out.println("     Enter '0' to exit the program     ");
        System.out.println("***************************************");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            try {
                // Ask for the benchmark file name
                System.out.print("\nEnter the benchmark file name (eg-: bridge_3,ladder_4): ");
                String filename = scanner.nextLine();
                
                // Check if user wants to exit
                if (filename.equals("0")) {
                    running = false;
                    continue;
                }
                
                // Prepend the benchmarks directory to the filename
                String fullPath = "benchmarks/" + filename;
                if (!filename.endsWith(".txt")) {
                    fullPath += ".txt";
                }

                // Parse the file and create the graph
                Graph graph = Parser.parseFile(fullPath);

                // Source is node 0, sink is the last node (n-1)
                int source = 0;
                int sink = graph.getNumNodes() - 1;

                // Create the MaxFlow solver
                MaxFlow maxFlow = new MaxFlow(graph, source, sink);

                // Print header for the augmenting paths
                System.out.println("\nFinal Augmenting Paths and Flow Calculations:");

                // Run the algorithm and print the maximum flow
                int maxFlowValue = maxFlow.findMaxFlow();

                System.out.println("\nMaximum Flow: " + maxFlowValue);

            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
        System.out.println("\n***************************************");
        System.out.println("    Program terminated successfully!   ");
        System.out.println("***************************************");
    }
}