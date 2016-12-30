package compression;

import java.util.*;

import theory.Ensemble;
import theory.Interval;
import theory.IntervalSet;
import theory.NotAProbabilityDistribution;
import theory.Symbol;
import org.apache.commons.math3.analysis.function.Log;

public class ArithmeticCoderForBentCoin {

	/**
	 * using Ensembles was totally not the way to do this
	 * I should have used Trie's, it would be way more elegant and perform better too
	 */
	
	public Ensemble<String> bentCoin;
	public Ensemble<String> codingTable;
	public Ensemble<String> binaryTable;
	public List<Double> intervals = new ArrayList<Double>();
	private Log log = new Log();

	public ArithmeticCoderForBentCoin() {
		super();
		try {
			this.bentCoin = new BentCoinEnsemble(new double[] { 0.5, 0.5 });
			this.codingTable = new BentCoinEnsemble(new double[] { 0.5, 0.5 });
			this.binaryTable = new BentCoinEnsemble(new double[] { 0.5, 0.5 });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Double smallestProbability() {
		double smallest = 1.0;
		for (Symbol<String> symbol: codingTable.getAlphabet()) {
			if (symbol.getProbability() < smallest) {
				smallest = symbol.getProbability();
			}
		}
		return smallest;
	}
	
	
	public void compressBinaryTable() {
		int counter = 0;
		String current = null;
		String prev = null; 
		for (Symbol<String> s : binaryTable.getAlphabet()) {
			prev = current;
			current = (String)s.getLabel();
			if (current == prev) {
				counter++;
			}
		}
	}
	
	public void labelDeepestIntervals() {
		for (Interval<String> binaryInterval: findDeepestBinaryInterval() ) {
			for (Interval<String> predictedInterval: codingTable.toInterval() ) {
				if (binaryInterval.isContainedBy(predictedInterval)) {
					binaryTable.findSymbol(binaryInterval.getSymbol()).setLabel(predictedInterval.getSymbol());
				}
			}			
		}
	}
	
	public IntervalSet<String> findDeepestBinaryInterval() {
		IntervalSet<String> intervalList = codingTable.toInterval();
		int depth = binaryDistributionDepth();
		IntervalSet<String> deepestInterval = splitBinaryTableToDepth(depth);
		return deepestInterval;
	}

	public int binaryDistributionDepth() {
		// compute the smallest binary interval needed to complete the code
		// by taking the logarithm of the smallest interval in the set
		double smallest = smallestProbability();
		double numberOfSteps = -(log.value(smallest)/log.value(2.0));
		return (int)Math.floor(numberOfSteps);
	}
	
	
	public void resetBinaryTable() {
		try {
			this.binaryTable = new BentCoinEnsemble(new double[] { 0.5, 0.5 });
		} catch (NotAProbabilityDistribution e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IntervalSet<String> splitBinaryTableToDepth(int depth) {
		resetBinaryTable();
		IntervalSet<String> deepest = null;
		while (depth > 0 ) {
			depth--;
			deepest = splitBinaryTable();
		}
		return deepest;
	}
	
	public IntervalSet<String> splitBinaryTable() {
		try {
			this.binaryTable = splitAllByDistribution(new BentCoinEnsemble( new double[] { 0.5, 0.5 }), this.binaryTable );
		} catch (NotAProbabilityDistribution e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return binaryTable.toInterval();
	}

	public void splitCodingTableByDistribution(Ensemble<String> ensemble) throws NotAProbabilityDistribution {
		this.codingTable = splitAllByDistribution(ensemble, this.codingTable);
	}

	/**
	 * splits all intervals in the current set by the given probability
	 * distribution
	 * 
	 * @param probability
	 * @throws NotAProbabilityDistribution
	 */
	private Ensemble<String> splitAllByDistribution(Ensemble<String> ensemble, Ensemble<String> ensembleToSplit) throws NotAProbabilityDistribution {
		List<Symbol<String>> newSymbols = new ArrayList<Symbol<String>>();

		for (Symbol<String> symbol : ensemble.getAlphabet()) {
			double p = ensemble.probabilityOfOccurence(symbol);
			for (Symbol<String> code : ensembleToSplit.getAlphabet()) {
				newSymbols.add(new Symbol<String>(code.getSymbol() + symbol.getSymbol(),
						ensembleToSplit.probabilityOfOccurence(code) * p));
			}
		}

		return new Ensemble<String>(new Random(), newSymbols);
	}

	/**
	 * splits all intervals in the current set by the given probability
	 * distribution
	 * 
	 * @param probability
	 */
	public void splitAllByDistribution(double[] probability) {
		List<Double> newValues = new ArrayList<Double>();

		for (Double p : probability) {
			for (Double i : intervals) {
				newValues.add(i * p);
			}
		}

		intervals = newValues;
	}

	public String compress(String signal) {
		StringBuilder c = new StringBuilder();
		return c.toString();
	}

}
