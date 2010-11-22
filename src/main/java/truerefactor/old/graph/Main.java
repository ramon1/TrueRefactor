package truerefactor.old.graph;

import java.io.File;

import truerefactor.old.JavaParseGraph;
import truerefactor.old.genetic.GeneticAlgorithm;


public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
        	if (handleArguments(args)) {
        		CodeGraph graph = new CodeGraph();
        		JavaParseGraph parser = new JavaParseGraph();
        		
        		parser.process(graph, args[0]);

        		/*System.out.println("Prior to Refactoring");
        		System.out.println(graph.toString());*/

        		GeneticAlgorithm gp = new GeneticAlgorithm(20, 0.05, 100, 10);
        		gp.refactor(graph);
        	}
        	else {
        		printHelp();
        	}
        } else {
        	printHelp();
        }
    }
    
    public static boolean handleArguments(String[] args) {
    	File rootDir = new File(args[0]);
    	if (rootDir.isDirectory()) {
    		return true;    		
    	}
    	
    	return false;
    }
    
    public static void printHelp() {
    	System.out.println("ERRS - Epistemic Retention Refactoring System");
    	System.out.println("---------------------------------------------");
    	System.out.println("usage: ");
    	System.out.println("\t java -jar errs <dir>");
    	System.out.println("\n\t where: <dir> is the root directory of Java source code files to be processed.");
    	
    }
}
