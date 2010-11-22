/**
 * 
 */
package truerefactor.input;

import japa.parser.ast.CompilationUnit;
import truerefactor.graph.AttributeNode;
import truerefactor.graph.ClassNode;
import truerefactor.graph.MethodNode;
import truerefactor.graph.PackageNode;
import truerefactor.graph.StatementNode;

/**
 * @author Isaac
 *
 */
public class CPPGraphParser extends GraphParser {
    
    public static String LANGUAGE = "C++";
    
    /*
     * (non-Javadoc)
     * @see truerefactor.input.GraphParser#buildConnections(japa.parser.ast.CompilationUnit)
     */
    @Override
    public void buildConnections(CompilationUnit cu)
    {
        // TODO Auto-generated method stub
        
    }

    /*
     * (non-Javadoc)
     * @see truerefactor.input.GraphParser#addAttribute(truerefactor.graph.ClassNode, java.lang.String, java.lang.String)
     */
    @Override
    public AttributeNode addAttribute(ClassNode cNode, String fieldName, String code)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see truerefactor.input.GraphParser#addStatement(truerefactor.graph.ClassNode, java.lang.String, java.lang.String)
     */
    @Override
    public StatementNode addStatement(ClassNode cNode, String type, String code)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see truerefactor.input.GraphParser#addMethod(truerefactor.graph.ClassNode, java.lang.String, java.lang.String[], java.lang.String)
     */
    @Override
    public MethodNode addMethod(ClassNode cNode, String methodName, String[] params, String code)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see truerefactor.input.GraphParser#addPackage(java.lang.String)
     */
    @Override
    public PackageNode addPackage(String packageName)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see truerefactor.input.GraphParser#addClass(java.lang.String, java.lang.String)
     */
    @Override
    public ClassNode addClass(String className, String packageName)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
