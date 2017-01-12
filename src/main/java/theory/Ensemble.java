package theory;

import java.util.*;

import org.apache.commons.math3.analysis.function.Log;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import channel.IGenerator;

public class Ensemble<T extends Comparable<T>> implements IGenerator, IEnsemble<T> {

	// An Ensemble is a random variable, a finite alphabet, and a probability
	// distribution representing the likelihood of occurrence of the letter of
	// the alphabet in a sequence

		
	private Logger log = LoggerFactory.getLogger(this.getClass());
	protected Random randomVariable;
	protected List<Symbol<T>> alphabet = new ArrayList<Symbol<T>>();
	private INDArray probVector;
	private Log LOG = new Log();
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
	
	public void init() {
		checkDistribution();
		generateMap();
	}

	protected void populateProbabilityVector() {
		probVector = Nd4j.zeros(this.alphabetLength());
		for (int i = 0; i < this.alphabetLength(); i++) {
			probVector.put(0, i, alphabet.get(i).getProbability() );
		}
	}
	
	protected void generateMap() {
		map = new HashMap<T,Symbol<T>>();
		for (Symbol<T> symbol: this.alphabet) {
			map.put(symbol.getSymbol(), symbol);
		}
	}
	
	protected boolean checkDistribution() {
		double total = 0; 
		for (Symbol<T> symbol: this.alphabet) {
			total += symbol.getProbability();
		}
		if (total < 0.9999999 | total > 1.0000001) {
			log.warn("Ensemble probability does not total to 1.0 it totals: " + total);
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see theory.IEnseble#probabilityOfOccurence(T)
	 */
	public double probabilityOfOccurence(T symbol) {
		return map.get(symbol).getProbability();
	}
	
	/* (non-Javadoc)
	 * @see theory.IEnseble#probabilityOfOccurence(theory.Symbol)
	 */
	public double probabilityOfOccurence(Symbol<T> symbol) {
		return map.get(symbol.getSymbol()).getProbability();
	}
	
	/* (non-Javadoc)
	 * @see theory.IEnseble#informationOfOccurence(theory.Symbol)
	 */	
	public double informationOfOccurence(Symbol<T> symbol) {
		return informationOfOccurence(symbol.getSymbol());
	}
	
	/* (non-Javadoc)
	 * @see theory.IEnseble#informationOfOccurence(T)
	 */
	public double informationOfOccurence(T symbol) {
		double info =  LOG.value(1/probabilityOfOccurence(symbol))/LOG.value(2);
		if (Double.isFinite(info)) {return info;} else {return (double)0.0;}
	}
	
	/* (non-Javadoc)
	 * @see theory.IEnseble#entropy()
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

	/* (non-Javadoc)
	 * @see theory.IEnseble#getAlphabet()
	 */
	public List<Symbol<T>> getAlphabet() {
		return alphabet;
	}

	/* (non-Javadoc)
	 * @see theory.IEnseble#alphabetLength()
	 */
	public int alphabetLength() {
		return alphabet.size();
	}
	
	public void setAlphabet(List<Symbol<T>> alphabet) throws NotAProbabilityDistribution {
		this.alphabet = alphabet;
		init();
	}
	
	/* (non-Javadoc)
	 * @see theory.IEnseble#getSymbol(T)
	 */
	public Symbol<T> getSymbol(T key) {
		return map.get(key);
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
