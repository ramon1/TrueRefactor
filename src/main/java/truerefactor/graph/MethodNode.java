/**
 * 
 */
package truerefactor.graph;

import java.util.List;

/**
 * @author Isaac
 *
 */
public class MethodNode extends CodeNode {

    /**
     * 
     */
    private List<CodeNode> statements;
    /**
     * 
     */
    private List<String> parameters;
    /**
     * 
     */
    private ClassNode cNode;
    
    /**
     * @return the parameters
     */
    public List<String> getParameters()
    {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(List<String> parameters)
    {
        this.parameters = parameters;
    }

    /**
     * @return the cNode
     */
    public ClassNode getcNode()
    {
        return cNode;
    }

    /**
     * @param cNode the cNode to set
     */
    public void setcNode(ClassNode cNode)
    {
        this.cNode = cNode;
    }

    /**
     * 
     * @param identifier
     * @param code
     */
    public MethodNode(String identifier, String code)
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