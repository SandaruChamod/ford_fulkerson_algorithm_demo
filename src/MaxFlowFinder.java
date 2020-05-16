import java.util.*;
import java.lang.*;
import java.util.LinkedList;

/**
 * class MaxFlowFinder
 */
class MaxFlowFinder {

    //Stores stepCount of nodes in the graph
    private int numberOfNodes = 0;

    //Count steps in finding the maximum flow
    private static int stepCount = 1;

    /**
     * Responsible for search breadth first in the process of finding maximum flow.
     *
     * @param rGraph       graph
     * @param startingNode source node
     * @param endingNode   sink node
     * @param parent       array to store the path
     * @return boolean
     */
    private boolean searchBreadthFirst(int[][] rGraph, int startingNode, int endingNode, int[] parent) {

        //Array to keep track of visited nodes of graph
        boolean[] visited = new boolean[numberOfNodes];

        //Set all nodes as unvisited
        for (int i = 0; i < numberOfNodes; ++i) {
            visited[i] = false;
        }

        //List the has responsibility to store the nodes
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(startingNode);
        visited[startingNode] = true;
        parent[startingNode] = -1;

        while (queue.size() != 0) {

            //Get and remove the head of the list
            int u = queue.poll();

            for (int v = 0; v < numberOfNodes; v++) {

                //If a node has not been visited and it is also having a capacity
                if (!visited[v] && rGraph[u][v] > 0) {
                    //Add to the queue
                    queue.add(v);
                    parent[v] = u;
                    //Mark node as it visited
                    visited[v] = true;
                }

            }
        }

        //Return sink node (t node) has been visited or not
        return (visited[endingNode]);
    }

    /**
     * Responsible to return maximum floor from s to t in the given graph.
     *
     * @param graph         graph
     * @param sourceNode    source node (s)
     * @param sinkNode      sink node (t)
     * @param numberOfNodes node count
     * @param nodeNames     node names
     * @return int - Maximum floor
     * @throws InterruptedException ex
     */
    synchronized int findMaxFlow(int[][] graph, int sourceNode, int sinkNode, int numberOfNodes, int[] nodeNames) throws InterruptedException {
        //Length of one dimension of the 2D capacity array (Node count)
        this.numberOfNodes = numberOfNodes;

        //Starting with u and ending with v
        int u, v;

        //Copying original capacity array to a new array
        int[][] rGraph = new int[numberOfNodes][numberOfNodes];
        for (u = 0; u < numberOfNodes; u++) {
            for (v = 0; v < numberOfNodes; v++) {
                rGraph[u][v] = graph[u][v];
            }
        }
        //This array is filled by BFS to store path
        int[] parent = new int[numberOfNodes];

        //Use to store maximum flow
        int max_flow = 0;

        //Generate flow while the last node can be visited
        while (searchBreadthFirst(rGraph, sourceNode, sinkNode, parent)) {
            //Assign the maximum value to the path_flow variable on initialization
            int path_flow = Integer.MAX_VALUE;

            // Responsible to store augmented paths
            String augmentedPathString = "";

            for (v = sinkNode; v != sourceNode; v = parent[v]) {
                //get the previous vertex in the path
                u = parent[v];
                //minimum of previous bottleneck & the capacity of the new edge
                path_flow = Math.min(path_flow, rGraph[u][v]);

                //concat vertex to path
                augmentedPathString = " --> ".concat(nodeNames[v] + augmentedPathString);
            }
            //loop stops before it gets to S, so add S to the beginning
            augmentedPathString = "S" + augmentedPathString;
            // replace sink node to T
            augmentedPathString = augmentedPathString.replace(String.valueOf(sinkNode), "T");
            System.out.println("Augmentation path \n" + Colors.ANSI_RED + augmentedPathString + Colors.ANSI_RESET);
            System.out.println(Colors.ANSI_BLUE + "bottleneck (min flow on path added to max flow) = " + path_flow + "\n" + Colors.ANSI_RESET);

            for (v = sinkNode; v != sourceNode; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }
            //Add path flow to maximum flow
            max_flow += path_flow;
        }
        return max_flow;
    }
}
