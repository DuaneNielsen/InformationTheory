package theory;

import java.util.Comparator;

public class Interval<T  extends Comparable<T>> implements Comparator<Interval<T>> {

	protected Symbol<T> symbol; 
	public double min;
	public double max;
	
	protected Object label;
		
	public Interval(Symbol<T> symbol, double min, double length ) {
		super();
		this.symbol = symbol;
		this.min = min;
		this.max = min + length;
	}		
	
	public enum states {
		OVERLAP, ADJACENT, CONTAINED, APART 
	}
	
	public boolean apart(Interval<?> i) {
		return (this.max < i.min) || (i.max < this.min);
	}
	
	public boolean adjacent(Interval<?> i) {
		return (this.max == i.min) || (i.max == this.min);
	}
	
	public boolean contains(Interval<?> i) {
		return (this.min <= i.min ) && (this.max >= i.max);   
	}
	
	public boolean isContainedBy(Interval<?> inputInterval) {
		return (inputInterval.min <= this.min) && (inputInterval.max >= this.max);
	}
	
	public boolean overlaps(Interval<?> i) {
		return (this.max > i.min) || (i.max > this.min);
	}
	
	public boolean equal(Interval<?> i) {
		return (this.min == i.min) && (this.max == i.max);
	}
	
	public double length() {
		return max - min;
	}
	
	/**
	 * returns the length of overlap between two intervals
	 * @return
	 */
	public double overlap(Interval<?> i) {
		double overlap = 0.0;
		if (this.min < i.min) {
			overlap =  this.max - i.min;
		}
		else overlap = i.max - this.min;
		return overlap;
	}
	

	// TODO this is a hack I need to think more carefully about
	public int compare(Interval<T> int1, Interval<T> int2) {
		return (int) (int1.min - int2.min) * 10000;
	}
	
	public String toString() {
		return "(" + symbol.getSymbol().toString() + "," + min + "-" + max + ")";
	}

	public Symbol<T> getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol<T> symbol) {
		this.symbol = symbol;
	}

	public Object getLabel() {
		return label;
	}

	public void setLabel(Object label) {
		this.label = label;
	}
	
	public Interval<T> combineAdjacent(Interval<T> b, Symbol<T> s) {
		if  ( ! this.adjacent(b) ) return null;
		double min = Math.min(this.min, b.min);
		double max = Math.max(this.max, b.max);
		s.setProbability(max - min);
		return new Interval<T>(s, min, max-min);
	}

}
