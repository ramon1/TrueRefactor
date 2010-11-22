/**
 * 
 */
package truerefactor.genetic;

import java.util.ArrayList;
import java.util.List;

import truerefactor.graph.CodeNode;
import truerefactor.refactor.Refactoring;

/**
 * 
 * @author Isaac
 *
 */
public class Allele {

    /**
     * 
     */
    private Refactoring refactoring;
    /**
     * 
     */
    private List<CodeNode> operands;
    
    /**
     * 
     * @param clone
     */
    public Allele(Allele clone) {
        
    }
    
    /**
     * 
     * @param nodes
     */
    public Allele(CodeNode ... nodes) {
        setOperands(nodes);
    }
    
    /**
     * 
     * @return
     */
    public List<CodeNode> getOperands() {
        return operands;
    }
    
    /**
     * 
     * @return
     */
    public Refactoring getRefactoring() {
        return refactoring;
    }
    
    /**
     * 
     * @param nodes
     */
    public void setOperands(CodeNode ... nodes) {
        operands = new ArrayList<CodeNode>();
        for (int i = 0; i < nodes.length; i++) {
            operands.add(nodes[i]);
        }
    }
    
    /**
     * 
     * @param refactoring
     */
    public void setRefactoring(Refactoring refactoring) {
        this.refactoring = refactoring;
    }
    
    /**
     * 
     */
    public void print() {
        // TODO stub method
        throw new UnsupportedOperationException("Method not yet implemented");
    }
}
