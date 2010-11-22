/**
 * 
 */
package truerefactor.input;

import java.io.File;

import truerefactor.graph.CodeGraph;

/**
 * @author Isaac
 *
 */
public class CodeGraphBuilder {

    /**
     * 
     */
    private GraphParser parser;
       
    /**
     * 
     * @param parser
     */
    public CodeGraphBuilder(GraphParser parser) {
        this.parser = parser;
    }
    
    /**
     * 
     * @param graph
     * @param source
     */
    public void process(CodeGraph graph, File source) {
        
    }
    
    /**
     * 
     * @param graph
     * @param source
     */
    public void parseAndBuildFromFile(CodeGraph graph, File source) {
        
    }
}
