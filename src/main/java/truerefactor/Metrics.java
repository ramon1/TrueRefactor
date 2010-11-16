/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package truerefactor;

import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import truerefactor.graph.ClassNode;
import truerefactor.graph.CodeGraph;
import truerefactor.graph.Edge;
import truerefactor.graph.FieldNode;
import truerefactor.graph.MethodNode;
import truerefactor.graph.Node;

import com.sun.corba.se.impl.orbutil.graph.Graph;


/**
 * Metrics - 
 *
 * @author Isaac Griffith
 */
public class Metrics {

	/**
	 * Calculates the CyclomaticComplexity for the given Graph
	 * @param mNode
	 */    
    public static int calculateLocalCyclomaticComplexity(MethodNode mNode) {
    	int numEdges = 0;
    	for (Node n : mNode.getNodes()) {
    		for (Edge e : n.getEdges()) {
    			if (e instanceof Use) {
    				continue;
    			}
    			
    			numEdges++;
    		}
    	}
    	
    	return numEdges - mNode.getNodes().size() + 1;
    }

    /**
     * Calculates weighted methods per class for the provided graph
     * @param graph
     */
    public static double calculateWMC(CodeGraph graph) {
    	List<ClassNode> classes = graph.getClasses();
    	int localWMC = 0;
    	int totalWMC = 0;
    	double retVal = 0;
    	
    	for (ClassNode cNode : classes) {
    		localWMC = 0;
    		for (MethodNode mNode : cNode.getMethods()) {
    			localWMC += calculateLocalCyclomaticComplexity(mNode);
    		}
    		totalWMC += localWMC;
    	}
    	if (classes.size() == 0)
    		return -1.0;
    	
    	return (totalWMC / classes.size());
    }

    /**
     * Calculates Lack of Cohesion
     * @param graph
     */
    public static double calculateLCOM(CodeGraph graph) {
    	double totalCohesion = 0;
    	double classCohesion = 1.0;
    	double cohesionFactor = 0;
    	List<ClassNode> classes = graph.getClasses();
    	
    	for (ClassNode source : classes) {
    		classCohesion = 1.0;
    		for (FieldNode field : source.getAttributes()) {
    			// count number of methods connected to this field
    			int count = 0;
    			for (MethodNode method : source.getMethods()) {
    				if (graph.isEdge(field, method) || graph.isEdge(method, field)) {
    					count++;
    				}
    			}
    			
    			classCohesion *= ((field.getEdges().size() - 1) - count) / (field.getEdges().size() - 1);
    		}
    		
    		totalCohesion += classCohesion;
    	}
    	
    	if (classes.size() == 0)
    		return -1.0;
    	
    	cohesionFactor = totalCohesion / classes.size();
    	return cohesionFactor;
    }

    /**
     * Calculates Coupling Between Objects
     * @param graph
     */
    public static double calculateCBO(CodeGraph graph) {
    	int coupling = 0;
    	double couplingFactor = 0.0;
    	List<ClassNode> classes = graph.getClasses();
    	
    	for (ClassNode source : classes) {
    		for (ClassNode dest : classes) {
    			if (dest.equals(source))
    				continue;
    			
    			for (MethodNode destMethod : dest.getMethods()) {
    				for (MethodNode sourceMethod : source.getMethods()) {
    					if (graph.isEdge(sourceMethod, destMethod)) {
    						coupling++;
    					}
    				}
    				
    				for (FieldNode sourceField : source.getAttributes()) {
    					if (graph.isEdge(sourceField, destMethod)) {
    						coupling++;
    					}
    				}
    			}
    		}
    	}
    	
    	if (classes.size() == 0)
    		return -1.0;
    	
    	couplingFactor = coupling / classes.size();
    	return couplingFactor;
    }
}
