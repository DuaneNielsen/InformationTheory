package theory;

import java.util.*;

public class NaiveEnsembleGenerator<T extends Comparable<T>> {
	
	private Random random;
	private IEnsemble<T> ensemble;
	private List<Double> interval = new ArrayList<Double>();
	
	public NaiveEnsembleGenerator(Random random, IEnsemble<T> ensemble) {
		super();
		this.random = random;
		this.ensemble = ensemble;
		double position = 0.0;
		for (Symbol<T> s : ensemble.getAlphabet()) {
			position = position + s.getProbability();
			interval.add(position);
		}
	}
	
	public Symbol<T> generateRandom() {
		Symbol<T> s = null;
		Double rand = random.nextDouble();
		int i = 0;
		while (rand >= interval.get(i)) {
			if ( i + 1 == interval.size()) break;
			i++;
		}
		s =  ensemble.getAlphabet().get(i);
		return s;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for (Double inter : interval ) {
			sb.append(inter).append(" ");
		}
		
		return sb.toString();
	}
	
}
