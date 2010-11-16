/**
 * 
 */
package truerefactor.graph;

import java.util.Iterator;

/**
 * GraphOperations - Provides the necessary Graph traversal and manipulation
 * operations necessary for the NEO framework.
 * 
 * @author Isaac Griffith
 */
public class GraphOperations {

    /**
     * The index that indicates the discovery order.
     */
    private static int discoverIndex;
    /**
     * The index that indicates the finish order.
     */
    private static int finishIndex;
    /**
     * an array of node indexes representing the order of discovery of each
     * node.
     */
    private static int[] discoveryOrder;
    /**
     * array of indexes of nodes representing the sorted order of the graph.
     * Each value of this array is itself an index to the next node.
     */
    private static int[] finishOrder;
    /**
     * array of boolean values representing whether or not the node with the
     * same index has been visited by the current operation.
     */
    private static boolean[] visited;
    /**
     * array representing the indexes of the parents of the node found at the
     * index item.
     */
    private static int[] parent;

    /**
     * DepthFirstSearch traversal of the provided graph.
     * 
     * @param graph
     *            DiGraph to be traversed
     * @return integers representing the indexes
     */
    public synchronized static int[] depthFirstSearch(DiGraph graph)
    {
        int n = graph.numberOfNodes();
        parent = new int[n];
        visited = new boolean[n];
        discoveryOrder = new int[n];
        finishOrder = new int[n];
        finishIndex = 0;
        discoverIndex = 0;

        for (int i = 0; i < n; i++)
        {
            parent[i] = -1;
        }
        for (int i = 0; i < n; i++)
        {
            if (!visited[i])
                depthFirstSearch(graph, i);
        }

        return finishOrder;
    }

    /**
     * Recursively depth-first search the graph starting at vertex current
     * 
     * @param graph
     *            The graph to be traversed
     * @param current
     *            The start vertex
     */
    private synchronized static void depthFirstSearch(DiGraph graph, int current)
    {
        visited[current] = true;
        discoveryOrder[discoverIndex++] = current;
        Iterator<Node> itr = graph.getAdjacencies(graph.get(current)).iterator();

        while (itr.hasNext())
        {
            int neighbor = graph.indexOf(itr.next());
            if (!visited[neighbor])
            {
                parent[neighbor] = current;
                depthFirstSearch(graph, neighbor);
            }
        }

        finishOrder[finishIndex++] = current;
    }

    /**
     * Performs a Topological Sort of a Directed Acyclic Graph
     * 
     * @param graph
     *            The Graph to sort
     * @return The list of sorted Vertices
     */
    public synchronized static int[] topologicalSort(DiGraph graph)
    {
        depthFirstSearch(graph);

        int numNodes = graph.numberOfNodes();
        int[] temp = new int[numNodes];
        int[] finishOrder = depthFirstSearch(graph);

        for (int i = numNodes; i > 0; i--)
        {
            temp[numNodes - i] = finishOrder[i - 1];
        }

        return temp;
    }
}
