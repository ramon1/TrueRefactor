/**
 * 
 */
package truerefactor.genetic;

import java.util.List;

import truerefactor.graph.CodeGraph;
import truerefactor.graph.GraphView;
import truerefactor.refactor.RefactoringController;

/**
 * @author Isaac
 *
 */
public class GeneticAlgorithm implements GraphView {

    private CodeGraph graph;
    private RefactoringController controller;
    private static int MAX_ITERATIONS;
    private static double MUTATION_RATE;
    private static int NEW_INDIVIDUAL_MAX_SIZE;
    private static int POP_SIZE;
    
    /**
     * 
     * @param controller
     */
    public GeneticAlgorithm(RefactoringController controller) {
        this.controller = controller;
    }
    
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
     * @return
     */
    public Allele newAllele() {
        // TODO method stub
        throw new UnsupportedOperationException("Method not yet impemented");
    }
    
    /**
     * 
     * @return
     */
    public Individual newIndividual() {
     // TODO method stub
        throw new UnsupportedOperationException("Method not yet impemented");
    }
    
    /**
     * 
     * @param population
     */
    public void retainPopulation(List<Individual> population) {
        
    }
}
