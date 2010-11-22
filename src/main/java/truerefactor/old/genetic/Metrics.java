package truerefactor.old.genetic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import truerefactor.old.graph.ClassNode;
import truerefactor.old.graph.CodeGraph;
import truerefactor.old.graph.Edge;
import truerefactor.old.graph.FieldNode;
import truerefactor.old.graph.MethodNode;

public class Metrics {
    /**
     * @param result
     * @return
     */
    public static double calcFitness(CodeGraph graph)
    {
        double fitness = 1;

        double lcomRatio = 0.5;
        double cboRatio = 0.5;

        List<ClassNode> classes = graph.getClasses();
        List<MethodNode> methods = graph.getMethods();
        List<FieldNode> fields = graph.getFields();

        // fitness -= Metrics.calculateGlobalCyclomaticComplexity(mNode);
        // fitness -= wmcRatio * Metrics.calculateWMC(result);
        fitness += lcomRatio * calculateLCOM(classes);
        fitness -= cboRatio * calculateCBO(classes, fields, methods);

        return fitness;
    }

    /**
     * Calculates Lack of Cohesion
     * 
     * @param graph
     */
    public static double calculateLCOM(List<ClassNode> classes)
    {
        double totalCohesion = 0;
        double classCohesion = 1.0;
        double cohesionFactor = 0;

        for (ClassNode c : classes)
        {
            classCohesion = 1.0;

            for (FieldNode f : c.getFields())
            {
                double count = 0;
                for (MethodNode m : c.getMethods())
                {
                    if (e.getDestType().equals("Field"))
                    {
                        if (e.getSource().equals(m.getName()) && e.getDest().equals(f.getName()))
                            count++;
                    }

                }
                classCohesion += count;
            }
            totalCohesion += classCohesion * (c.getMethods().size() == 0 ? 0 : 1.0 / c.getMethods().size());
        }
        if (classes.size() == 0)
            return 0;

        cohesionFactor = totalCohesion / classes.size();
        return cohesionFactor;
    }

    /**
     * Calculates Coupling Between Objects
     * 
     * @param graph
     */
    public static double calculateCBO(List<ClassNode> classes, List<FieldNode> fields, List<MethodNode> methods)
    {
        double coupling = 0;
        double couplingFactor = 0.0;

        for (ClassNode c : classes)
        {
            Set<String> classNames = new HashSet<String>();
            for (MethodNode m : c.getMethods())
            {
                for (Edge e : edges)
                {
                    if (e.getSource().equals(m.getName()))
                    {
                        if (e.getType().equals("Field"))
                        {
                            for (FieldNode field : fields)
                            {
                                if (field.getName().equals(e.getDestination()))
                                {
                                    if (!classNames.contains(field.getParentClass().getName()))
                                        classNames.add(field.getParentClass().getName());
                                }
                            }
                        }
                        else if (e.getType().equals("Method"))
                        {
                            for (MethodNode mStar : methods)
                            {
                                if (mStar.getName().equals(e.getDestination()))
                                {
                                    if (!classNames.contains(mStar.getParentClass().getName()))
                                        classNames.add(mStar.getParentClass().getName());
                                }
                            }
                        }
                    }
                }
            }
            coupling += classNames.size();
        }
        if (classes.size() == 0)
            return 0;

        couplingFactor = coupling / classes.size();
        return couplingFactor;
    }
}
