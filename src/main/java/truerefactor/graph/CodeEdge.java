/**
 * 
 */
package truerefactor.graph;

/**
 * CodeEdge - Represents a directed connection between two nodes. Where
 * the direction of flow is from the source node to the destination node. The
 * Edge maintains information about both the source node, the destination node,
 * and the weight assigned to the edge. Yet, the CodeEdge is only used to
 * maintain the graph structure, it does not exist as an element of the graph
 * beyond the insertion of nodes correctly into the graph.
 * 
 * @author Isaac
 */
public class CodeEdge {

    /**
     * The Node this edge is directed into
     */
    private Node destination;
    /**
     * The Node this edge is directed from
     */
    private Node source;
    /**
     * The weight associated with this edge between the source and destination
     * nodes
     */
    private double weight;

    /**
     * Constructs a new CodeEdge from the provided source node to the
     * destination node with a weight of 1.0
     * 
     * @param source
     *            The node the edge is directed from
     * @param destination
     *            The node the edge is directed towards
     */
    public CodeEdge(Node source, Node destination)
    {
        this(source, destination, 1.0d);
    }

    /**
     * Constructs a new CodeEdge from the provided source node to the
     * destination node with the specified weight
     * 
     * @param source
     *            The node the edge is directed from
     * @param destination
     *            The node the edge is directed towards
     * @param weight
     *            The weight of the edge
     */
    public CodeEdge(Node source, Node destination, double weight)
    {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Returns the destination node of this CodeEdge, where the
     * destination node is the node this CodeEdge is directed into.
     * 
     * @return This CodeEdge's destination node
     */
    public Node getDesination()
    {
        return destination;
    }

    /**
     * Returns the source node of this CodeEdge, where the source node is
     * the node this CodeEdge is directed away from.
     * 
     * @return This CodeEdge's source node
     */
    public Node getSource()
    {
        return source;
    }

    /**
     * Returns the weight of this edge. The weight is a measure of some factor
     * concerning the relationship between the source and the destination nodes.
     * Such factors could be strength of signal in a graph representing
     * electrical circuits, or it could be time, or some other factor that
     * affects the connection between the two nodes. The weight can be any value
     * that is a legitimate double value, and this method will always return a
     * legitimate double value.
     * 
     * @return The weight of this edge
     */
    public double getWeight()
    {
        return weight;
    }
}
