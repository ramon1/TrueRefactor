/**
 * 
 */
package truerefactor.input;

import japa.parser.ast.CompilationUnit;

import java.io.File;

import truerefactor.graph.AttributeNode;
import truerefactor.graph.ClassNode;
import truerefactor.graph.CodeGraph;
import truerefactor.graph.MethodNode;
import truerefactor.graph.PackageNode;
import truerefactor.graph.StatementNode;

/**
 * @author Isaac
 *
 */
public abstract class GraphParser {

    public static final String LANGUAGE = "";
    /**
     * 
     */
    protected CodeGraph graph;
    /**
     * 
     */
    protected File file;
    
    /**
     * 
     * @param cu
     */
    public abstract void buildConnections(CompilationUnit cu);
    
    /**
     * 
     * @param cNode
     * @param fieldName
     * @param code
     * @return
     */
    public abstract AttributeNode addAttribute(ClassNode cNode, String fieldName, String code);
    
    /**
     * 
     * @param cNode
     * @param type
     * @param code
     * @return
     */
    public abstract StatementNode addStatement(ClassNode cNode, String type, String code);
    
    /**
     * 
     * @param cNode
     * @param methodName
     * @param params
     * @param code
     * @return
     */
    public abstract MethodNode addMethod(ClassNode cNode, String methodName, String[] params, String code);
    
    /**
     * 
     * @param packageName
     * @return
     */
    public abstract PackageNode addPackage(String packageName);
    
    /**
     * 
     * @param className
     * @param packageName
     * @return
     */
    public abstract ClassNode addClass(String className, String packageName);
}
