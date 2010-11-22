/**
 * 
 */
package truerefactor.old.graph;

import java.util.List;
import java.util.Set;

/**
 * DiGraph - A Directed Graph ADT.
 * 
 * @author Isaac
 */
public interface DiGraph {

    /**
     * Returns a CodeEdge (directed Edge) if one exists between the source
     * node and the destination node. If no such edge actually exists in the
     * graph, then null is returned.
     * 
     * @param source
     *            The from node, where the edge originates
     * @param destination
     *            The to node, where the edge ends.
     * @return CodeEdge encapsulating the directional relationship between
     *         the source and destination nodes if such a relationship exists.
     */
    CodeEdge getEdge(Node source, Node destination);

    /**
     * Returns an Iterator for this graph which enumerates a sequence of nodes
     * using the edges linking nodes. As a requirement if a node is not
     * connected or if there are two subgraphs with in the graph which are not
     * connected, there is no guarantee as to the order of the nodes in both
     * subgraphs in sequence beyond the order maintained inside the subgraphs
     * themselves.
     * 
     * @return an iterator over this graph.
     */
    GraphIterator getIterator();

    /**
     * Inserts the provided CodeEdge into this graph, if such edge does
     * not already exist.
     * 
     * @param edge
     *            CodeEdge to be inserted.
     */
    void insert(CodeEdge edge);

    /**
     * Returns the number of edges that exist in this graph
     * 
     * @return number of edges comprising this graph
     */
    int numberOfEdges();

    /**
     * Returns the number of Nodes contained in this graph.
     * 
     * @return number of nodes comprising this graph
     */
    int numberOfNodes();

    /**
     * Removes the specified node, if it exists in this graph, from this graph.
     * Upon remove all edges connecting nodes to the specified node, and edges
     * connecting from this node are simultaneously removed as well.
     * 
     * @param node
     *            Node to be removed from this graph.
     */
    void remove(Node node);

    /**
     * Removes the specified CodeEdge, if such an edge connects the
     * contained nodes exists in this graph, from this graph.
     * 
     * @param edge
     *            CodeEdge to be removed from this graph.
     */
    void remove(CodeEdge edge);

    /**
     * Returns the Node at the specified index in the adjacency list
     * 
     * @see DiGraph#indexOf(Node)
     * @param index
     *            Index of the Node to be retrieved
     * @return Node with the specified index, if the index is not greater than
     *         the number of available nodes, or less than 0. Otherwise this
     *         method returns null.
     */
    Node get(int index);

    /**
     * Returns a List of Nodes adjacent to the Node specified.
     * 
     * @param node
     *            Node whose adjacency list is required
     * @return Adjacency list for the specified node, if that node exists in the
     *         graph, or null, if it doesn't.
     */
    List<Node> getAdjacencies(Node node);

    /**
     * Returns the index in the adjacency list of the specified Node.
     * 
     * @param node
     *            Node for which an index is required
     * @return Index of the specified Node, if that Node exists in the graph,
     *         otherwise it returns -1.
     */
    int indexOf(Node node);

    /**
     * Determines if a CodeEdge exists between the provided source node
     * and destination node in this DiGraph
     * 
     * @param source
     *            Source Node
     * @param destination
     *            Destination Node
     * @return True if a CodeEdge exists between the Source Node and
     *         Destination Node, false otherwise.
     */
    boolean isEdge(Node source, Node destination);

    /**
     * Inserts the specified Node into this graph.
     * 
     * @param source
     *            Node to be inserted.
     * @return A reference to the newly inserted Node
     */
    Node insert(Node source);

    /**
     * Returns all of the Source Nodes for this graph. Where a Source node is
     * any node which is not associated as the destination end of any edge in
     * the graph.
     * 
     * @return List of all source nodes found in the graph.
     */
    List<Node> getSources();

    /**
     * Returns all of the Sink Nodes for this graph. Where a Sink node is any
     * node which is not associated as the source end of any edge in the graph.
     * 
     * @return List of all sink nodes found in the graph.
     */
    List<Node> getSinks();

    /**
     * Returns a set of Nodes representing the difference between this DiGraph
     * and the provided DiGraph.
     * 
     * @param other
     *            DiGraph to compare this graph to
     * @return Set<Node> representing the difference between this DiGraph and
     *         the other DiGraph
     */
    Set<Node> diff(DiGraph other);
}