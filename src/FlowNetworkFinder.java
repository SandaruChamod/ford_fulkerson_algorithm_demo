import java.util.Scanner;

class FlowNetworkFinder {

    private int nodeCount;
    private int edgeCount;
    private int tempForAugPath = 0;
    private int[] nodeIdentifiers;
    private int[] edge_u;
    private int[] edge_v;
    private int[][] edge_capacity;
    private static Scanner input = new Scanner(System.in);
    private static String ordinal;
    private static final int MAX_VERTICES = 96;
    private static final int MIN_VERTICES = 6;


    public static void main(String[] args) throws InterruptedException {

        printTitle();
        System.out.println("1. Input graph details manually[1]\n2. Get details from graph source[2]");
        while (!input.hasNextInt()) {
            System.out.println("Input a valid integer");
            input.next();
        }
        int selectedOption = input.nextInt();
        while (selectedOption != 1 && selectedOption != 2) {
            System.out.println("Your have entered a wrong input");
            System.out.println("1. Input graph details manually[1]\n2. Get details from graph source[2]?");
            while (!input.hasNextInt()) {
                System.out.println("Input a valid integer");
                input.next();
            }
            selectedOption = input.nextInt();
        }
        FlowNetworkFinder flowNetworkFinder = new FlowNetworkFinder();
        if (selectedOption == 1) {
            System.out.println("Please note '0' is the source node, which means s = 0");
            System.out.println("Input number of nodes (starting node and ending node inclusive)");
            while (!input.hasNextInt()) {
                System.out.println("Input a valid integer");
                input.next();
            }
            flowNetworkFinder.nodeCount = input.nextInt();
            while (!(MIN_VERTICES <= flowNetworkFinder.nodeCount) || !(flowNetworkFinder.nodeCount <= 12)) {
                System.out.println("Your have entered a wrong input");
                System.out.println("Input a number between 6 to 12");
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                flowNetworkFinder.nodeCount = input.nextInt();
            }

            System.out.println("Input number of edges");
            while (!input.hasNextInt()) {
                System.out.println("Input a valid integer");
                input.next();
            }
            flowNetworkFinder.edgeCount = input.nextInt();
            while (!(4 <= flowNetworkFinder.edgeCount) || !(flowNetworkFinder.edgeCount <= ((flowNetworkFinder.edgeCount * (flowNetworkFinder.edgeCount - 1)) - (2 * flowNetworkFinder.edgeCount) + 3))) {
                System.out.println("Your have entered a wrong number of edges");
                System.out.println("Input a number between " + 4 + " and " + ((flowNetworkFinder.edgeCount * (flowNetworkFinder.edgeCount - 1)) - (2 * flowNetworkFinder.edgeCount) + 3));
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                flowNetworkFinder.edgeCount = input.nextInt();
            }

            flowNetworkFinder.nodeIdentifiers = new int[flowNetworkFinder.nodeCount];
            flowNetworkFinder.edge_u = new int[flowNetworkFinder.edgeCount];
            flowNetworkFinder.edge_v = new int[flowNetworkFinder.edgeCount];
            flowNetworkFinder.edge_capacity = new int[flowNetworkFinder.nodeCount][flowNetworkFinder.nodeCount];

            for (int i = 0; i < flowNetworkFinder.edgeCount; i++) {
                int start, end, capacity;
                flowNetworkFinder.ordinalIndicatorAssigner(i);
                System.out.println("Input the starting node of your " + (i + 1) + ordinal + " edge");
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                start = input.nextInt();
                while ((start >= flowNetworkFinder.nodeCount - 1) || (start < 0)) {
                    System.out.println("Your have entered a wrong input");
                    System.out.println("Input a number between 0 and " + (flowNetworkFinder.nodeCount - 2));
                    while (!input.hasNextInt()) {
                        System.out.println("Input a valid integer");
                        input.next();
                    }
                    start = input.nextInt();
                }
                flowNetworkFinder.edge_u[i] = start;
                System.out.println("Input the ending node of your " + (i + 1) + ordinal + " edge");
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                end = input.nextInt();
                while ((end > flowNetworkFinder.nodeCount - 1) || (end <= 0)) {
                    System.out.println("Your have entered a wrong input");
                    System.out.println("Input a number between 1 and " + (flowNetworkFinder.nodeCount - 1));
                    while (!input.hasNextInt()) {
                        System.out.println("Input a valid integer");
                        input.next();
                    }
                    end = input.nextInt();
                }
                flowNetworkFinder.edge_v[i] = end;
                System.out.println("Input the capacity of your " + (i + 1) + ordinal + " edge");
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                capacity = input.nextInt();
                while ((capacity > 20) || (capacity < 2)) {
                    System.out.println("Your have entered a wrong input");
                    System.out.println("Input a number between 5 and 20");
                    while (!input.hasNextInt()) {
                        System.out.println("Input a valid integer");
                        input.next();
                    }
                    capacity = input.nextInt();
                }
                flowNetworkFinder.edge_capacity[start][end] = capacity;

            }
            System.out.println("Number of Nodes(including s and t): " + flowNetworkFinder.nodeCount);
            System.out.println("Number of Edges: " + flowNetworkFinder.edgeCount);
            flowNetworkFinder.assignNodeNames();
            flowNetworkFinder.printEdgeCapacities();

        } else {
            System.out.println("Input number of nodes/vertices that you want to generate :");
            while (!input.hasNextInt()) {
                System.out.println("Input a valid number");
                input.next();
            }
            int noOfNodes = input.nextInt();
            while ((noOfNodes < MIN_VERTICES) || (noOfNodes > MAX_VERTICES)) {
                System.out.println("Your have entered a wrong input");
                System.out.println("Input a number between " + MIN_VERTICES + " and " + MAX_VERTICES);
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                noOfNodes = input.nextInt();
            }

            flowNetworkFinder.nodeCount = noOfNodes;
            flowNetworkFinder.edgeCount = flowNetworkFinder.generateRandomNumber(6, ((flowNetworkFinder.nodeCount * (flowNetworkFinder.nodeCount - 1)) - (2 * flowNetworkFinder.nodeCount) + 3));
            flowNetworkFinder.nodeIdentifiers = new int[flowNetworkFinder.nodeCount];
            flowNetworkFinder.edge_u = new int[flowNetworkFinder.edgeCount];
            flowNetworkFinder.edge_v = new int[flowNetworkFinder.edgeCount];
            flowNetworkFinder.edge_capacity = new int[flowNetworkFinder.nodeCount][flowNetworkFinder.nodeCount];
            flowNetworkFinder.tempForAugPath = flowNetworkFinder.generateRandomNumber(1, flowNetworkFinder.nodeCount - 2);
            System.out.println("Number of Nodes(including s and t): " + flowNetworkFinder.nodeCount);
            System.out.println("Number of Edges: " + flowNetworkFinder.edgeCount);
            flowNetworkFinder.assignNodeNames();
            flowNetworkFinder.generateNetwork();
            flowNetworkFinder.printEdgeCapacities();
        }
        MaxFlowFinder m = new MaxFlowFinder();

        //Get current time from the system.
        long startTime = System.currentTimeMillis();

        //Print calculated maximum flow
        int maximumFlow = m.findMaxFlow(flowNetworkFinder.edge_capacity, 0, flowNetworkFinder.nodeCount - 1, flowNetworkFinder.nodeCount, flowNetworkFinder.nodeIdentifiers);
        System.out.println("\nThe maximum possible flow is " + maximumFlow);
        //Print the elapsed time to execute the algorithm
        System.out.println("Elapsed milli seconds: " + (System.currentTimeMillis() - startTime));

        System.out.println("Input [1] to edit the existing graph or any key to exit");
        int edit = input.nextInt();
        while (edit == 1) {
            flowNetworkFinder.removeEdges();
            flowNetworkFinder.addEdges();
            m = new MaxFlowFinder();

            //Get current time from the system.
            startTime = System.currentTimeMillis();

            //Print calculated maximum flow
            maximumFlow = m.findMaxFlow(flowNetworkFinder.edge_capacity, 0, flowNetworkFinder.nodeCount - 1, flowNetworkFinder.nodeCount, flowNetworkFinder.nodeIdentifiers);
            System.out.println("The maximum possible flow is " + maximumFlow);
            //Print the elapsed time to execute the algorithm
            System.out.println("Elapsed milli seconds: " + (System.currentTimeMillis() - startTime));

            System.out.println("Input [1] to edit the existing graph or any key to exit");
            edit = input.nextInt();
        }
    }

    /**
     * Generate network flow for the given graph.
     */
    private void generateNetwork() {

        int tempVNode;

        //Set augmented path (should contain at least one augmented path)
        for (int i = 0; i < this.edgeCount; i++) {
            do {
                if (i == 0) {
                    this.edge_u[i] = 0;
                    tempVNode = tempForAugPath;
                } else if (i == 1) {
                    this.edge_u[i] = tempForAugPath;
                    tempVNode = this.nodeCount - 1;
                } else {
                    this.edge_u[i] = (int) (Math.random() * (this.nodeCount - 1));
                    tempVNode = (int) (Math.random() * (this.nodeCount - 1)) + 1;
                }
            } while ((tempVNode == this.edge_u[i]) || (this.isConnected(this.edge_u[i], tempVNode)));

            this.edge_v[i] = tempVNode;
            this.connect(this.edge_u[i], this.edge_v[i]);
        }

    }

    /**
     * Connect given nodes with a capacity.
     *
     * @param u first node
     * @param v second node
     */
    private void connect(int u, int v) {
        edge_capacity[u][v] = generateRandomNumber(5, 20);

    }

    /**
     * Check if given nodes are connected.
     *
     * @param u first node
     * @param v second node
     * @return boolean
     */
    private boolean isConnected(int u, int v) {
        return edge_capacity[u][v] > 0;
    }

    /**
     * Add edges to the graph.
     */
    private void addEdges() {

        System.out.println("Input your preferred number of edges to add");
        while (!input.hasNextInt()) {
            System.out.println("Input a valid integer");
            input.next();
        }
        int newEdges = input.nextInt();
        int startingEdge, endingEdge, capacity;
        for (int i = 0; i < newEdges; i++) {
            ordinalIndicatorAssigner(i);
            System.out.println("Input the starting node of your " + (i + 1) + ordinal + " edge");
            while (!input.hasNextInt()) {
                System.out.println("Input a valid integer");
                input.next();
            }
            startingEdge = input.nextInt();
            while ((startingEdge >= nodeCount - 1) || (startingEdge < 0)) {
                System.out.println("Your have entered a wrong input");
                System.out.println("Input a number between 0 and " + (nodeCount - 2) + " to add starting node");
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                startingEdge = input.nextInt();
            }
            System.out.println("Input the ending node of your " + (i + 1) + ordinal + " edge");
            while (!input.hasNextInt()) {
                System.out.println("Input a valid integer");
                input.next();
            }
            endingEdge = input.nextInt();
            while ((endingEdge > nodeCount - 1) || (endingEdge <= 0)) {
                System.out.println("Your have entered a wrong input");
                System.out.println("Input a number between 1 and " + (nodeCount - 1) + " to add ending node");
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                endingEdge = input.nextInt();
            }
            System.out.println("Input the capacity of your " + (i + 1) + ordinal + " edge");
            while (!input.hasNextInt()) {
                System.out.println("Input a valid integer");
                input.next();
            }
            capacity = input.nextInt();
            while ((capacity > 20) || (capacity < 4)) {
                System.out.println("Your have entered a wrong input");
                System.out.println("Input a number between 5 and 20 to add new capacity");
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                capacity = input.nextInt();
            }
            edge_capacity[startingEdge][endingEdge] = capacity;
        }
    }

    /**
     * Assign an ordinal Indicator.
     *
     * @param i number to apply the indicator.
     */
    private void ordinalIndicatorAssigner(int i) {
        switch (i) {
            case 0:
                ordinal = "st";
                break;
            case 1:
                ordinal = "nd";
                break;
            case 2:
                ordinal = "rd";
                break;
            default:
                ordinal = "th";
        }
    }

    /**
     * Remove edge from the graph.
     */
    private void removeEdges() {

        System.out.println("Input your preferred number of edges to remove");
        while (!input.hasNextInt()) {
            System.out.println("Input a valid integer");
            input.next();
        }
        int numberOfEdgesToRemove = input.nextInt();

        for (int i = 0; i < numberOfEdgesToRemove; i++) {
            int start, end;
            ordinalIndicatorAssigner(i);
            System.out.println("Input the starting node of your " + (i + 1) + ordinal + " edge to remove");
            while (!input.hasNextInt()) {
                System.out.println("Input a valid integer");
                input.next();
            }
            start = input.nextInt();
            while ((start >= nodeCount - 1) || (start < 0)) {
                System.out.println("Your have entered a wrong input");
                System.out.println("Input a number between 0 and " + (nodeCount - 2));
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                start = input.nextInt();
            }
            System.out.println("Input the ending node of your " + (i + 1) + ordinal + " edge to remove");
            while (!input.hasNextInt()) {
                System.out.println("Input a valid integer");
                input.next();
            }
            end = input.nextInt();
            while ((end > nodeCount - 1) || (end <= 0)) {
                System.out.println("Your have entered a wrong input");
                System.out.println("Input a number between 1 and " + (nodeCount - 1));
                while (!input.hasNextInt()) {
                    System.out.println("Input a valid integer");
                    input.next();
                }
                end = input.nextInt();
            }
            edge_capacity[start][end] = 0;

        }
        System.out.println("Number of Nodes(including s and t): " + nodeCount);
        System.out.println("Number of Edges: " + edgeCount);
        printEdgeCapacities();
    }

    /**
     * Generate and return a random number between given values.
     *
     * @param min min value
     * @param max max value
     * @return int
     */
    private int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    /**
     * Print edge capacities on console.
     */
    private void printEdgeCapacities() {
        System.out.println(Colors.ANSI_CYAN + "\n-------------------------");
        System.out.println("| Connection | Capacity |");
        System.out.println("-------------------------");
        for (int i = 0; i < edge_u.length; i++) {
            System.out.print("|   " + nodeIdentifiers[edge_u[i]] + " -> ");
            System.out.print(nodeIdentifiers[edge_v[i]] + "   | ");
            System.out.printf("   %02d    |\n", edge_capacity[edge_u[i]][edge_v[i]]);
            System.out.println("-------------------------");
        }
        System.out.println(Colors.ANSI_RESET + "\nAugmented paths used to calculate the maximum flow:\n");
    }

    /**
     * Assign node names to nodes list.
     */
    private void assignNodeNames() {
        for (int i = 0; i < nodeCount; i++) {
            nodeIdentifiers[i] = i;
        }
    }

    /**
     * Responsible for print title of the program.
     */
    private static void printTitle() {
        System.out.println(Colors.ANSI_BRIGHT_GREEN + "\n" +
                "   \\  |               _)                                   ____|  |                       ____| _)             |             \n" +
                "  |\\/ |   _` | \\ \\  /  |  __ `__ \\   |   |  __ `__ \\       |      |   _ \\ \\ \\  \\   /      |      |  __ \\    _` |   _ \\   __| \n" +
                "  |   |  (   |  `  <   |  |   |   |  |   |  |   |   |      __|    |  (   | \\ \\  \\ /       __|    |  |   |  (   |   __/  |    \n" +
                " _|  _| \\__,_|  _/\\_\\ _| _|  _|  _| \\__,_| _|  _|  _|     _|     _| \\___/   \\_/\\_/       _|     _| _|  _| \\__,_| \\___| _|    \n" +
                "                                                                                                                             \n" + Colors.ANSI_RESET);
    }
}
