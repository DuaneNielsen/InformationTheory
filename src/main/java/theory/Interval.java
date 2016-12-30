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
	
	public boolean apart(Interval<T> i) {
		return (this.max < i.min) || (i.max < this.min);
	}
	
	public boolean adjacent(Interval<T> i) {
		return (this.max == i.min) || (i.max == this.min);
	}
	
	public boolean contains(Interval<T> i) {
		return (this.min <= i.min ) && (this.max >= i.max);   
	}
	
	public boolean isContainedBy(Interval<T> i) {
		return (i.min <= this.min) && (i.max >= this.max);
	}
	
	public boolean overlaps(Interval<T> i) {
		return (this.max > i.min) || (i.max > this.min);
	}
	
	public boolean equal(Interval<T> i) {
		return (this.min == i.min) && (this.max == i.max);
	}
	
	public double length() {
		return symbol.probability;
	}
	
	@Override
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
