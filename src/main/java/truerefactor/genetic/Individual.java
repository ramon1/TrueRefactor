package truerefactor.genetic;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Wahl, Scott
 * 
 */
public class Individual {

	private List<Allele> chromosome;
	private double fitness;
	
	/**
	 * 
	 */
	public Individual(){
		chromosome = new LinkedList<Allele>();
	}

	/**
	 * 
	 * @param pTwo
	 * @return
	 */
	public List<Individual> crossover(Individual pTwo){
		List<Individual> children = new LinkedList<Individual>();
		
		children.add(new Individual());
		children.add(new Individual());

		int minSize = (getSize() < pTwo.getSize()) ? getSize() : pTwo.getSize();
		int maxSize = (getSize() < pTwo.getSize()) ? pTwo.getSize() : getSize();
		
		int i = 0;
		for (; i < minSize; i++){
			if (Math.random() < 0.5){
				children.get(0).addAllele(getAllele(i));
				children.get(1).addAllele(pTwo.getAllele(i));
			}
			else{
				children.get(0).addAllele(pTwo.getAllele(i));
				children.get(1).addAllele(getAllele(i));
			}
		}
		
		for (; i < maxSize; i++){
			if (Math.random() < 0.5)
				children.get(0).addAllele((i < getSize() ? getAllele(i) : pTwo.getAllele(i)));
			else
				children.get(1).addAllele((i < getSize() ? getAllele(i) : pTwo.getAllele(i)));
		}
		return children;
	}
	
	/**
	 * 
	 * @param type
	 * @param index
	 * @param allele
	 */
	public void mutate(int type, int index, Allele allele){
		switch(type){
			case 0: chromosome.remove(index); break;
			case 1: chromosome.add(index, allele); break;
			case 2: chromosome.add(index, allele); break;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSize(){
		return chromosome.size();
	}
	
	/**
	 * 
	 * @param fitness
	 */
	public void setFitness(double fitness){
		this.fitness = fitness;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getFitness(){
		return fitness;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Allele getAllele(int index){
		return chromosome.get(index);
	}
	
	public List<Allele> getChromosome(){
		return chromosome;
	}

	/**
	 * 
	 * @param allele
	 */
	public void addAllele(Allele allele){
		chromosome.add(allele);
	}
	
	public void print(){
		for (Allele e : chromosome)
			e.print();
		System.out.println();
	}
}
