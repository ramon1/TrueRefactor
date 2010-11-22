/**
 * 
 */
package truerefactor.graph;

/**
 * @author Isaac
 *
 */
public class Edge {

    /**
     * 
     */
    private CodeNode source;
    /**
     * 
     */
    private CodeNode destination;
    /**
     * 
     */
    private boolean isDirected;
    /**
     * 
     */
    private String sourceLabel;
    /**
     * 
     */
    private String destLabel;
    /**
     * 
     */
    private EdgeType type;
    /**
     * 
     */
    private Cardinality sourceCard;
    /**
     * 
     */
    private Cardinality destCard;
    
    /**
     * 
     */
    public Edge() {
        // TODO stub constructor
        throw new UnsupportedOperationException("Constructor not yet implemented");
    }
    
    /**
     * 
     * @param source
     * @param dest
     */
    public void connect(CodeNode source, CodeNode dest) {
        // TODO stub method
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * @return the source
     */
    public CodeNode getSource()
    {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(CodeNode source)
    {
        this.source = source;
    }

    /**
     * @return the destination
     */
    public CodeNode getDestination()
    {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(CodeNode destination)
    {
        this.destination = destination;
    }

    /**
     * @return the isDirected
     */
    public boolean isDirected()
    {
        return isDirected;
    }

    /**
     * @param isDirected the isDirected to set
     */
    public void setDirected(boolean isDirected)
    {
        this.isDirected = isDirected;
    }

    /**
     * @return the sourceLabel
     */
    public String getSourceLabel()
    {
        return sourceLabel;
    }

    /**
     * @param sourceLabel the sourceLabel to set
     */
    public void setSourceLabel(String sourceLabel)
    {
        this.sourceLabel = sourceLabel;
    }

    /**
     * @return the destLabel
     */
    public String getDestLabel()
    {
        return destLabel;
    }

    /**
     * @param destLabel the destLabel to set
     */
    public void setDestLabel(String destLabel)
    {
        this.destLabel = destLabel;
    }

    /**
     * @return the edge type
     */
    public EdgeType getType()
    {
        return type;
    }

    /**
     * @param type the edge type to set
     */
    public void setType(EdgeType type)
    {
        this.type = type;
    }

    /**
     * @return the sourceCardinality
     */
    public Cardinality getSourceCard()
    {
        return sourceCard;
    }

    /**
     * @param sourceCard the sourceCardinality to set
     */
    public void setSourceCardinality(Cardinality sourceCard)
    {
        this.sourceCard = sourceCard;
    }

    /**
     * @return the destCardinality
     */
    public Cardinality getDestCard()
    {
        return destCard;
    }

    /**
     * @param destCard the destCardinality to set
     */
    public void setDestCardinality(Cardinality destCard)
    {
        this.destCard = destCard;
    }
}
