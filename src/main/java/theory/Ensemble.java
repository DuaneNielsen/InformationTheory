package theory;

import java.util.*;
import org.apache.commons.math3.analysis.function.Log;

import channel.IGenerator;

public class Ensemble<T> implements IGenerator {

	// An Ensemble is a random variable, a finite alphabet, and a probability
	// distribution representing the likelihood of occurrence of the letter of
	// the alphabet in a sequence

	private Random randomVariable;
	private T[] alphabet;
	private double[] distrib;
	private Log log = new Log();
	
	private Map<T,Double> probabilityOf = new HashMap<T,Double>();
	
	public Ensemble( Random randomVariable, T[] alphabet, double[] probabilityDistribution ) {
		this.randomVariable = randomVariable;
		this.alphabet = alphabet;
		this.distrib = probabilityDistribution;
		for (int i = 0; i < alphabet.length; i++) {
			probabilityOf.put(this.alphabet[i], this.distrib[i]);
		}
	}
	
	/**
	 * The probability of occurrence of a symbol in the ensemble
	 * @param symbol
	 * @return the probability of the occurrence of symbol
	 */
	public double probabilityOfOccurence(T symbol) {
		return probabilityOf.get(symbol);
	}
	
	/**
	 *  returns the Shannon information content in bits, of an isolated occurrence of a member of the alphabet
	 * @param symbol
	 * @return Shannon information content for symbol in bits
	 */
	public double informationOfOccurence(T symbol) {
		return log.value(1/probabilityOfOccurence(symbol))/log.value(2);
	}

	/**
	 * Shannon entropy is the average information content for the Ensemble
	 * @return the Shannon entropy of the Ensemble in bits
	 */
	public double entropy() {
		double entropy = 0;
		for (T symbol : alphabet ) {
			entropy += informationOfOccurence(symbol);
		}
		return entropy/(double)alphabet.length;
	}
	
	// TODO implement hash function using arithmetic coding
	// the below simply starts from the first on the list and moves along the list in order
	// this is Order N, but we should be able to do Order 1 or close to it....
	public T generate() {
		double randomValue = randomVariable.nextDouble();
		double interval = distrib[0];
		int i = 0;
		while (randomValue > interval ) {
			interval += distrib[i];
			i++;
		}
		return alphabet[i];
	}
	
//	Object getOccurence() {
//		double random = randomVariable.nextDouble();
//		
//	}

}
