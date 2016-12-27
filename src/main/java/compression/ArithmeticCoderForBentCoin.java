package compression;

import java.util.*;

import theory.Ensemble;
import theory.NotAProbabilityDistribution;

public class ArithmeticCoderForBentCoin {

	public Ensemble bentCoin;
	public Ensemble<String> codingTable;
	public List<Double> intervals = new ArrayList<Double>();
	
	public ArithmeticCoderForBentCoin() {
		super();
		try {
			this.bentCoin = new BentCoinEnsemble(new double[] { 0.5, 0.5 });
			this.codingTable = new BentCoinEnsemble(new double[] { 0.5, 0.5 });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * splits all intervals in the current set by the given probability distribution
	 * @param probability
	 */
	public void splitAllByDistribution(Ensemble<String> ensemble) {
		List<Double> newDistrib = new ArrayList<Double>();
		List<String> newAlphabet = new ArrayList<String>();
		
	    for (String symbol: ensemble.getAlphabet() ) {
	    	double p = ensemble.probabilityOfOccurence(symbol);
	    	for (String code: codingTable.getAlphabetAsList() ) {
	    		newAlphabet.add(code + symbol);
	    		newDistrib.add(codingTable.probabilityOfOccurence(code) * p);
	    	}
	    }
				
//		for (Double p: ensemble.getDistrib()) {
//			for (Double i: codingTable.getDistrib()) {
//				newDistrib.add(i * p);
//				
//			}				
//		}
	    
	    codingTable.setAlphabet(newAlphabet);
	    codingTable.setDistrib(newDistrib.stream().mapToDouble(i->i).toArray());
	    try {
			codingTable.init();
		} catch (NotAProbabilityDistribution e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		//intervals =  newDistrib;
	}

	/**
	 * splits all intervals in the current set by the given probability distribution
	 * @param probability
	 */
	public void splitAllByDistribution(double[] probability) {
		List<Double> newValues = new ArrayList<Double>();
		
		for (Double p: probability) {
			for (Double i: intervals) {
				newValues.add(i * p);
			}				
		}
		
		intervals =  newValues;
	}
	
	
	public String compress(String signal) {
		StringBuilder c = new StringBuilder();
		return null;
	}

}
