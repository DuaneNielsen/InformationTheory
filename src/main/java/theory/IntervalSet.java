package theory;

import java.util.*;


public class IntervalSet<T extends Comparable<T>> implements Iterable<Interval<T>> {

	protected List<Interval<T>> intervals;

	public IntervalSet(Ensemble<T> ensemble) {
		super();
		this.intervals = new ArrayList<Interval<T>>();
		double offset = 0;
		for (Symbol<T> symbol : ensemble.getAlphabet()) {
			this.intervals.add(new Interval<T>(symbol, offset,symbol.getProbability()));
			offset += symbol.getProbability();
		}
	}
	
	public String toString() {
		return intervals.toString();
	}

	public Iterator<Interval<T>> iterator() {
		return intervals.iterator();
	}
	
}
