/**
 * 
 */
package truerefactor.old.graph;

/**
 * @author Isaac
 */
public class Edge {

    /** */
    private Node destination;
    /** */
    private Node source;
    /** */
    private Type type;

    /**
     * @param source
     * @param destination
     */
    public Edge(Node source, Node destination)
    {
        this(source, destination, Type.ASSOCIATION);
    }

    /**
     * @param source
     * @param destination
     * @param type
     */
    public Edge(Node source, Node destination, Type type)
    {
        this.source = source;
        this.destination = destination;
        this.type = type;
    }

    /**
     * @return
     */
    public Node getDestination()
    {
        return destination;
    }

    /**
     * @return
     */
    public Node getSource()
    {
        return source;
    }

    /**
     * @return
     */
    public Type getType()
    {
        return type;
    }

    public enum Type {
        USE, INHERITANCE, ASSOCIATION, AGGREGATION, REALIZATION, CALL, RETURN, BODY;
    }
}
