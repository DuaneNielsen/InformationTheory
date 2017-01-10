package theory;

import java.util.*;

import org.apache.commons.math3.analysis.function.Log;

import channel.IGenerator;

public class Ensemble<T extends Comparable<T>> implements IGenerator {

	// An Ensemble is a random variable, a finite alphabet, and a probability
	// distribution representing the likelihood of occurrence of the letter of
	// the alphabet in a sequence

	protected Random randomVariable;
	protected List<Symbol<T>> alphabet = new ArrayList<Symbol<T>>();
	private Log log = new Log();
	private Map<T,Symbol<T>> map = new HashMap<T,Symbol<T>>();
	
	public Ensemble( Random randomVariable, T[] alphabet, double[] probabilityDistribution ) throws NotAProbabilityDistribution {		
		this.randomVariable = randomVariable;		
		for (int i = 0; i < alphabet.length; i++) {
			this.alphabet.add(new Symbol<T>(alphabet[i], probabilityDistribution[i]));
		}
		init();
	}
	
	public Ensemble( Random randomVariable, List<Symbol<T>> symbols ) throws NotAProbabilityDistribution {		
		this.randomVariable = randomVariable;		
		this.alphabet = symbols;
		init();
	}
	
	public void init() throws NotAProbabilityDistribution {
		checkDistribution();
		generateMap();
	}

	protected void generateMap() {
		map = new HashMap<T,Symbol<T>>();
		for (Symbol<T> symbol: this.alphabet) {
			map.put(symbol.getSymbol(), symbol);
		}
	}
	
	protected void checkDistribution() throws NotAProbabilityDistribution {
		double total = 0; 
		for (Symbol<T> symbol: this.alphabet) {
			total += symbol.getProbability();
		}
		if (total < 0.9999999) throw new NotAProbabilityDistribution();
		if (total > 1.0000001) throw new NotAProbabilityDistribution();
	}
	
	/**
	 * The probability of occurrence of a symbol in the ensemble
	 * @param symbol type
	 * @return the probability of the occurrence of symbol
	 */
	public double probabilityOfOccurence(T symbol) {
		return map.get(symbol).getProbability();
	}
	
	/**
	 * The probability of occurrence of a symbol in the ensemble
	 * @param the actual symbol (just a bit of sugar)
	 * @return the probability of the occurrence of symbol
	 */
	public double probabilityOfOccurence(Symbol<T> symbol) {
		return map.get(symbol.getSymbol()).getProbability();
	}
	
	/**
	 * The probability of occurrence of a symbol in the ensemble
	 * @param symbol
	 * @return the probability of the occurrence of symbol
	 */	
	public double informationOfOccurence(Symbol<T> symbol) {
		return informationOfOccurence(symbol.getSymbol());
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
		for (Symbol<T> symbol : alphabet ) {
			entropy += ( informationOfOccurence(symbol) * probabilityOfOccurence(symbol));
		}
		return entropy;
	}
	
	// TODO implement hash function using arithmetic coding
	// the below simply starts from the first on the list and moves along the list in order
	// this is Order N, but we should be able to do Order 1 or close to it....
	public Symbol<T> generate() {
		double randomValue = randomVariable.nextDouble();
		Iterator<Symbol<T>> iter = alphabet.iterator();
		Symbol<T> symbol = iter.next();
		double interval = symbol.getProbability();
		while (randomValue > interval ) {
			symbol = iter.next();
			interval += symbol.getProbability();
		}
		return symbol;
	}

	/**
	 * returns a IntervalSet in the form { ( a, 0 - P(a) ), ( b, P(a) - P(a) + P(b) ) ... }
	 * where P(a) = the probability of symbol a
	 * @return
	 */
	public IntervalSet<T> toInterval() {
		return new IntervalSet<T>(this);
	}
	
	public Random getRandomVariable() {
		return randomVariable;
	}

	public void setRandomVariable(Random randomVariable) {
		this.randomVariable = randomVariable;
	}

	public List<Symbol<T>> getAlphabet() {
		return alphabet;
	}

	public int alphabetLength() {
		return alphabet.size();
	}
	
	public void setAlphabet(List<Symbol<T>> alphabet) throws NotAProbabilityDistribution {
		this.alphabet = alphabet;
		init();
	}
	
	public Symbol<T> findSymbol(T key) {
		return map.get(key);
	}
	
	public Symbol<T> findSymbol(Symbol<T> key) {
		return map.get(key.getSymbol());
	}
		
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (Symbol<T> symbol : this.getAlphabet()) {
			sb.append(symbol); 
		}
		sb.append("}");
		return sb.toString();
	}
	
}
