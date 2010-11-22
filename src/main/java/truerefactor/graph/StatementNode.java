/**
 * 
 */
package truerefactor.graph;

/**
 * @author Isaac
 */
public class StatementNode extends CodeNode {

    /**
     * 
     */
    private MethodNode parent;

    /**
     * @return the parent
     */
    public MethodNode getParent()
    {
        return parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(MethodNode parent)
    {
        this.parent = parent;
    }

    /**
     * @param identifier
     * @param code
     */
    public StatementNode(String identifier, String code)
    {
        super(identifier, code);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * @see
     * truerefactor.graph.CodeNode#add(truerefactor.graph.CodeNode)
     */
    @Override
    public void add(CodeNode child)
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see
     * truerefactor.graph.CodeNode#remove(truerefactor.graph.CodeNode)
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
