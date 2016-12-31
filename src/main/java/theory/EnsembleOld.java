package theory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.analysis.function.Log;

import channel.IGenerator;

public class EnsembleOld<T> implements IGenerator {

	// An Ensemble is a random variable, a finite alphabet, and a probability
	// distribution representing the likelihood of occurrence of the letter of
	// the alphabet in a sequence

	protected Random randomVariable;
	protected T[] alphabet;
	protected double[] distrib;
	private Log log = new Log();
	
	private Map<T,Double> probabilityOf = new HashMap<T,Double>();
	
	public EnsembleOld( Random randomVariable, T[] alphabet, double[] probabilityDistribution ) throws NotAProbabilityDistribution {
		this.randomVariable = randomVariable;
		this.alphabet = alphabet;
		this.distrib = probabilityDistribution;
		init();
	}
	
	public void init() throws NotAProbabilityDistribution {
		checkDistribution(this.distrib);
		probabilityOf = new HashMap<T,Double>();
		for (int i = 0; i < alphabet.length; i++) {
			probabilityOf.put(this.alphabet[i], this.distrib[i]);
		}
	}
	
	private void checkDistribution(double[] probabilityDistribution) throws NotAProbabilityDistribution {
		double total = 0; 
		for (double prob: probabilityDistribution) {
			total += prob;
		}
		if (total < 0.9999999) throw new NotAProbabilityDistribution();
		if (total > 1.0000001) throw new NotAProbabilityDistribution();
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
		double info =  log.value(1/probabilityOfOccurence(symbol))/log.value(2);
		if (Double.isFinite(info)) {return info;} else {return (double)0.0;}
	}
	
	/**
	 * Shannon entropy is the average information content for the Ensemble
	 * @return the Shannon entropy of the Ensemble in bits
	 */
	public double entropy() {
		double entropy = 0;
		for (T symbol : alphabet ) {
			entropy += ( informationOfOccurence(symbol) * probabilityOfOccurence(symbol));
		}
		return entropy;
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

	public Random getRandomVariable() {
		return randomVariable;
	}

	public void setRandomVariable(Random randomVariable) {
		this.randomVariable = randomVariable;
	}

	public T[] getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(T[] alphabet) {
		this.alphabet = alphabet;
	}

	@SuppressWarnings("unchecked")
	public void setAlphabet(List<T> alphabet) {
		this.alphabet = (T[]) alphabet.toArray();
	}
	
	
	public double[] getDistrib() {
		return distrib;
	}

//	public List<Double> getDistribAsList() {
//		return DoubleStream.of(distrib).boxed().collect(Collectors.toList());
//	}
//	
	
	public void setDistrib(double[] distrib) {
		this.distrib = distrib;
	}

//	public void setDistrib(List<Double> distrib) {
//		this.distrib = distrib.stream().mapToDouble(i->i).toArray();
//	}

	public List<T> getAlphabetAsList() {
		return new ArrayList<T>(Arrays.asList(alphabet));
	}


	//	Object getOccurence() {
//		double random = randomVariable.nextDouble();
//		
//	}

	
}
