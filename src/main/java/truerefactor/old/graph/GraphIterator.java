/**
 * 
 */
package truerefactor.old.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * GraphIterator - Generates a sequence of Nodes in the Graph and provides
 * methods by which these nodes can be sequentially visited. In order to ensure
 * that the iteration process correctly visits each node in the graph, the graph
 * cannot be modified during the period of iteration.
 * 
 * @author Isaac
 */
public class GraphIterator {

    /** The CodeGraph over which this Iterator is to Iterate */
    protected DiGraph graph;

    /**
     * Array of node indexes in the List of nodes in the graph representing the
     * topological sort order.
     */
    protected int[] order;

    /**
     * Number of nodes in the graph at the time the topological sort was
     * conducted
     */
    protected int numNodes;

    /**
     * Number of edges in the graph at the time the topological sort was
     * conducted
     */
    protected int numEdges;

    /**
     * Current index in the traversal order
     */
    protected int currentIndex;

    /**
     * @param graph
     */
    public GraphIterator(DiGraph graph)
    {
        this.graph = graph;
        numEdges = graph.numberOfEdges();
        numNodes = graph.numberOfNodes();
        currentIndex = 0;
        
        order = GraphOperations.topologicalSort(this.graph);
    }

    /**
     * Returns the first node as defined by the iterator;
     * 
     * @return the first node in the sequence
     */
    public Node begin()
    {
        if (hasGraphStateChanged())
        {
            throw new Error();
        }

        return graph.get(order[0]);
    }

    /**
     * Returns the next node in the iteration sequence if one exists.
     * 
     * @return the next node in the sequence
     */
    public Node next()
    {
        if (hasGraphStateChanged())
        {
            throw new Error();
        }

        Node retVal = graph.get(order[currentIndex]);
        currentIndex++;
        return retVal;
    }

    /**
     * Returns the last node in the iteration sequence.
     * 
     * @return last node in the sequence
     */
    public Node end()
    {
        if (hasGraphStateChanged())
        {
            throw new Error();
        }

        return graph.get(order[order.length - 1]);
    }

    /**
     * Tests whether there is another node after the current node in the
     * iteration sequence
     * 
     * @return true if there is another node following the current node in the
     *         sequence, false otherwise
     */
    public boolean hasNext()
    {
        if (hasGraphStateChanged())
        {
            throw new Error();
        }

        if (currentIndex <= numNodes - 1)
        {
            return true;
        }

        return false;
    }

    /**
     * Resets this iterator to the beginning of the iteration sequence.
     */
    public void reset()
    {
        if (hasGraphStateChanged())
        {
            throw new Error();
        }

        currentIndex = 0;
    }

    /**
     * Tests whether the graph's state has changed since the iteration order was
     * first generated. If the number of nodes or number of edges in the graph
     * has changed, then it is possible that the provided iteration sequence is
     * incorrect.
     * 
     * @return true if the associated graph's state (number of edges, number of
     *         nodes) has changed, false otherwise.
     */
    protected boolean hasGraphStateChanged()
    {
        boolean returnVal = false;

        if (graph.numberOfNodes() != numNodes)
        {
            returnVal = true;
        }
        else if (graph.numberOfEdges() != numEdges)
        {
            returnVal = true;
        }

        return returnVal;
    }

    /**
     * Returns the Nodes which are output only for the current timestep
     * 
     * @return Set of Nodes which represent the IUpdatables that only output,
     *         returns the empty set if no Nodes are available for Output only.
     */
    public Set<Node> getOutputNodes()
    {
        return new HashSet<Node>();
    }
}
