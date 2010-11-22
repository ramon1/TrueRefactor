/**
 * 
 */
package truerefactor.refactor;

import truerefactor.graph.CodeGraph;

/**
 * @author Isaac
 *
 */
public abstract class CodeSmell {

    /**
     * 
     */
    protected CodeGraph graph;
    
    /**
     * 
     * @return
     */
    public abstract double measure();
}
