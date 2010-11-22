/**
 * 
 */
package truerefactor.graph;

import java.util.List;

/**
 * @author Isaac
 *
 */
public class PackageNode extends CodeNode {

    /**
     * 
     */
    private List<ClassNode> classes;
    
    /**
     * 
     * @param identifier
     * @param code
     */
    public PackageNode(String identifier, String code)
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
