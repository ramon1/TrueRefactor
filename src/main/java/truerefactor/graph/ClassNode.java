/**
 * 
 */
package truerefactor.graph;

import java.util.List;

/**
 * @author Isaac
 *
 */
public class ClassNode extends CodeNode {

    /**
     * 
     */
    private List<CodeNode> attributes;
    /**
     * 
     */
    private List<CodeNode> methods;
    /**
     * 
     */
    private PackageNode pNode;
    
    /**
     * 
     * @param identifier
     * @param code
     */
    public ClassNode(String identifier, String code)
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

    /**
     * @return the attributes
     */
    public List<CodeNode> getAttributes()
    {
        return attributes;
    }

    /**
     * @return the methods
     */
    public List<CodeNode> getMethods()
    {
        return methods;
    }

    /**
     * @return the pNode
     */
    public PackageNode getpNode()
    {
        return pNode;
    }

    /**
     * @param pNode the pNode to set
     */
    public void setpNode(PackageNode pNode)
    {
        this.pNode = pNode;
    }

}
