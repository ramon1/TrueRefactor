/**
 * 
 */
package truerefactor.graph;

/**
 * DependentNode - A Node used to represent an IUpdatable in a CodeGraph.
 * 
 * @author Isaac
 */
public class CodeNode extends Node {

    /**
     * The value of this node from which all outgoing Nodes are dependent and
     * upon all incoming nodes this IUpdatable depends
     */
    private String value;

    /**
     * Constructs a new DependentNode with the provided IUpdatable as its value.
     * 
     * @param value
     *            the IUpdatable contained within this node
     */
    public CodeNode(String value)
    {
        super();
        this.value = value;
    }

    /**
     * Returns the IUpdatable contained within this node.
     * 
     * @return the IUpdatable stored in this Node
     */
    public String getValue()
    {
        return value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!super.equals(obj))
        {
            return false;
        }
        
        if (obj instanceof CodeNode) {
            CodeNode dn = (CodeNode)obj;
            
            if (this.value.equals(dn.getValue())) {
                return true;
            }
        }
        
        return false;
    }
}
