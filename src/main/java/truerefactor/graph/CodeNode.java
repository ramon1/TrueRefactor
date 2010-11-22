/**
 * 
 */
package truerefactor.graph;

/**
 * @author Isaac
 *
 */
public abstract class CodeNode {

    /**
     * 
     */
    protected String code;
    /**
     * 
     */
    protected String identifier;
    
    /**
     * 
     * @param identifier
     * @param code
     */
    public CodeNode(String identifier, String code) {
        this.identifier = identifier;
        this.code = code;
    }
    
    /**
     * 
     * @param child
     */
    public abstract void add(CodeNode child);
    
    /**
     * 
     * @param child
     */
    public abstract void remove(CodeNode child);
    
    /**
     * 
     * @param identfier
     * @return
     */
    public abstract CodeNode getChild(String identfier);

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof CodeNode))
        {
            return false;
        }
        CodeNode other = (CodeNode) obj;
        if (code == null)
        {
            if (other.code != null)
            {
                return false;
            }
        }
        else if (!code.equals(other.code))
        {
            return false;
        }
        if (identifier == null)
        {
            if (other.identifier != null)
            {
                return false;
            }
        }
        else if (!identifier.equals(other.identifier))
        {
            return false;
        }
        return true;
    }
}
