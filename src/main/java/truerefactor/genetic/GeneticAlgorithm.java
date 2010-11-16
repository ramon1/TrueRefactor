package truerefactor.genetic;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import truerefactor.RefactorController;
import truerefactor.graph.ClassNode;
import truerefactor.graph.CodeGraph;
import truerefactor.graph.FieldNode;
import truerefactor.graph.GraphView;
import truerefactor.graph.MethodNode;

/**
 * @author Wahl, Scott
 */
public class GeneticAlgorithm {

    private int popSize;
    private double mutationRate;
    private int maxIterations;
    private int NEW_INDIVIDUAL_MAXSIZE;
    private CodeGraph graph;
    private RefactorController refactor;

    /**
     * @param popSize
     * @param mutationrate
     * @param maxIterations
     * @param indMaxSize
     */
    public GeneticAlgorithm(RefactorController refactor, int popSize, double mutationRate, int maxIterations,
            int indMaxSize)
    {
        this.popSize = popSize;
        this.mutationRate = mutationRate;
        this.maxIterations = maxIterations;
        NEW_INDIVIDUAL_MAXSIZE = indMaxSize;
        this.refactor = refactor;
    }

    /**
     * @param graph
     * @return
     */
    public void refactor(CodeGraph graph)
    {
        try
        {
            this.graph = (CodeGraph) graph.clone();
        }
        catch (CloneNotSupportedException noclone)
        {

        }
        GraphView.print(graph);

        Vector<Individual> population = new Vector<Individual>();

        for (int i = 0; i < popSize; i++)
            population.add(newIndividual());

        int generation = 0;

        do
        {
            generation++;
            // System.out.println(generation);
            Vector<Individual> newGeneration = new Vector<Individual>();
            newGeneration.addAll(population);
            // fitness proportional selection, crossover, and mutation
            newGeneration.addAll(createChildren(population));
            // fitness proportional retention without replacement
            // and force keeping of single best individual

            population.clear();
            population.addAll(retainPopulation(newGeneration));
        }
        while (generation < maxIterations);

        Individual bestIndividual = null;
        for (Individual i : population)
        {
            if (bestIndividual == null)
                bestIndividual = i;
            else if (Metrics.calcFitness(applyIndividual(bestIndividual)) > Metrics.calcFitness(applyIndividual(i)))
                bestIndividual = i;
        }

        System.out.println("\n\n");
        System.out.println("Best Found: ");
        bestIndividual.print();
        System.out.println();
        GraphView.print(applyIndividual(bestIndividual));
        // return applyIndividual(bestIndividual);
    }

    /**
     * @param nodes
     * @return
     */
    private Individual newIndividual()
    {
        Individual ind = new Individual();

        int size = (int) (Math.random() * NEW_INDIVIDUAL_MAXSIZE - 2) + 2;

        for (int i = 0; i < size; i++)
            ind.addAllele(newAllele());

        return ind;
    }

    /**
     * @param nodes
     * @return
     */
    private Allele newAllele()
    {
        // Currently alleles are limited to allow refactorings for
        // move field and move method

        // additional refactorings will require changing most of the
        // below code

        Allele newAllele;
        // Move Method
        if (Math.random() < 0.5)
        {
            List<MethodNode> methods = graph.getMethods();
            List<ClassNode> classes = graph.getClasses();

            int methodIndex = (int) (Math.random() * methods.size());
            int newParentIndex = (int) (Math.random() * classes.size());

            newAllele = new Allele(methods.get(methodIndex), classes.get(newParentIndex));
        }
        else
        {
            List<FieldNode> fields = graph.getFields();
            List<ClassNode> classes = graph.getClasses();

            int fieldIndex = (int) (Math.random() * fields.size());
            int newParentIndex = (int) (Math.random() * classes.size());

            newAllele = new Allele(fields.get(fieldIndex), classes.get(newParentIndex));
        }

        // return the result
        return newAllele;
    }

    /**
     * @param parents
     * @return
     */
    private List<Individual> createChildren(List<Individual> parents)
    {
        int popSize = parents.size();
        double[] normSelection = new double[popSize];

        List<Individual> nextGeneration = new LinkedList<Individual>();

        for (int i = 0; i < normSelection.length; i++)
        {
            double addVal = Metrics.calcFitness(applyIndividual(parents.get(i)));
            normSelection[i] = (i == 0 ? 0 : normSelection[i - 1]) + addVal;
        }

        for (int i = 0; i < normSelection.length; i++)
            normSelection[i] /= normSelection[normSelection.length - 1];

        for (int i = 0; i < popSize / 2; i++)
        {
            double pOneSelVal = Math.random();
            double pTwoSelVal = Math.random();

            Individual pOne = null;
            Individual pTwo = null;
            for (int j = 0; j < normSelection.length; j++)
            {
                if (pOneSelVal < normSelection[j])
                {
                    pOne = parents.get(j);
                    break;
                }
            }

            for (int j = 0; j < normSelection.length; j++)
            {
                if (pTwoSelVal < normSelection[j])
                {
                    pTwo = parents.get(j);
                    break;
                }
            }
            // crossover of the selected parents
            List<Individual> newChildren = pOne.crossover(pTwo);

            // mutate each new child based on mutation rate
            for (int j = 0; j < newChildren.size(); j++)
            {
                if (Math.random() < mutationRate)
                {
                    int type = (int) (Math.random() * 3);

                    int index = (int) (Math.random() * newChildren.get(j).getSize());

                    if (newChildren.get(j).getSize() == 1 && type == 0)
                        type = 1;

                    // apply appropriate mutation
                    Allele newAllele = null;

                    // if type is 0, mutation is a deletion and
                    // a new allele is unnecessary
                    if (type == 0)
                        ;

                    // if type is 1, mutation is insertion of a
                    // new allele necessitating generation of a new one
                    else if (type == 1)
                        newAllele = newAllele();

                    // if type is 2, mutation is modification of an
                    // existing allele
                    else if (type == 2)
                    {
                        int newParentInd = (int) (Math.random() * graph.getClasses().size());
                        newAllele = new Allele(newChildren.get(j).getAllele(index));
                        newAllele.setNewParent(graph.getClasses().get(newParentInd));
                    }

                    newChildren.get(j).mutate(type, index, newAllele);
                }
            }

            // add the new children to the population
            nextGeneration.addAll(newChildren);
        }

        return nextGeneration;
    }

    /**
     * @param generation
     * @return
     */
    private List<Individual> retainPopulation(List<Individual> generation)
    {
        List<Individual> toKeep = new LinkedList<Individual>();

        double overallBest = Double.NEGATIVE_INFINITY;
        double average = 0;

        List<Double> fitnessVals = new Vector<Double>();
        for (Individual i : generation)
            fitnessVals.add(Metrics.calcFitness(applyIndividual(i)));

        for (int i = 0; i < popSize; i++)
        {
            double bestVal = Double.NEGATIVE_INFINITY;
            int bestInd = -1;

            for (int j = 0; j < generation.size(); j++)
            {
                if (bestVal < fitnessVals.get(j))
                {
                    bestVal = fitnessVals.get(j);
                    bestInd = j;

                    if (bestVal > overallBest)
                        overallBest = bestVal;
                }
            }

            toKeep.add(generation.remove(bestInd));
            average += fitnessVals.remove(bestInd) / popSize;
        }

        System.out.println(overallBest + "," + average);

        return toKeep;
    }

    /**
     * @param bestInd
     * @return
     */
    private CodeGraph applyIndividual(Individual ind)
    {
        try
        {
            CodeGraph clone = (CodeGraph) graph.clone();

            List<Allele> chromosome = ind.getChromosome();

            for (Allele allele : chromosome)
            {
                // apply refactorings given by the individual
                if (allele.getType().equals("MoveMethod"))
                    refactor.moveMethod(allele.getMethod(), allele.getNewParent());
                else if (allele.getType().equals("MoveField"))
                    refactor.moveField(allele.getField(), allele.getNewParent());
            }
            return clone;
        }
        catch (CloneNotSupportedException noClone)
        {
            return graph;
        }
    }
}