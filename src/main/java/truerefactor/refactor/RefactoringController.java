/**
 * 
 */
package truerefactor.refactor;

import java.util.List;

import truerefactor.genetic.Individual;
import truerefactor.genetic.Metric;
import truerefactor.graph.DiGraph;
import truerefactor.graph.GraphView;
import truerefactor.old.graph.CodeGraph;

/**
 * @author Isaac
 *
 */
public class RefactoringController implements GraphView {

    private DiGraph model;
    private GraphView view;
    private List<Metric> metrics;
    private List<RefactoringCommand> refactorCommands;
    private List<CodeSmell> smells;
    
    /*
     * (non-Javadoc)
     * @see truerefactor.graph.GraphView#update()
     */
    @Override
    public void update()
    {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * 
     * @param graph
     * @param individual
     */
    public void refactor(CodeGraph graph, Individual individual) {
        
    }
    
    /**
     * 
     * @param individual
     */
    public void applyIndividual(Individual individual) {
        
    }
}
