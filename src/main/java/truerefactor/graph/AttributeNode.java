/**
 * 
 */
package truerefactor.graph;

/**
 * @author Isaac
 *
 */
public class AttributeNode extends CodeNode {

    /**
     * 
     */
    private ClassNode parent;
    /**
     * 
     */
    private ClassNode associatedClass;
    /**
     * 
     */
    private boolean primitive;
    
    /**
     * @return the parent
     */
    public ClassNode getParent()
    {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(ClassNode parent)
    {
        this.parent = parent;
    }

    /**
     * @return the associatedClass
     */
    public ClassNode getAssociatedClass()
    {
        return associatedClass;
    }

    /**
     * @param associatedClass the associatedClass to set
     */
    public void setAssociatedClass(ClassNode associatedClass)
    {
        this.associatedClass = associatedClass;
    }

    /**
     * @return the primitive
     */
    public boolean isPrimitive()
    {
        return primitive;
    }

    /**
     * @param primitive the primitive to set
     */
    public void setPrimitive(boolean primitive)
    {
        this.primitive = primitive;
    }

    /**
     * 
     * @param identifier
     * @param code
     */
    public AttributeNode(String identifier, String code)
    {
        super(identifier, code);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * @see truerefactor.graph.CodeNode#add(truerefactor.graph.CodeNode)
     */
    @Override
    public void add(CodeNode child)
    {
        // TODO Auto-generated method stub
        
    }

    /*
     * (non-Javadoc)
     * @see truerefactor.graph.CodeNode#remove(truerefactor.graph.CodeNode)
     */
    @Override
    public void remove(CodeNode child)
    {
        // TODO Auto-generated method stub
        
    }

    /*
     * (non-Javadoc)
     * @see truerefactor.graph.CodeNode#getChild(java.lang.String)
     */
    @Override
    public CodeNode getChild(String identfier)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
