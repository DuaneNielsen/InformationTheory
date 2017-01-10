package theory;

public class Symbol<T extends Comparable<? super T>> implements Comparable<Symbol<T>> {
	
	protected T symbol;
	protected Double probability = 0.0;
	protected Object label;
	
	public Symbol(T symbol) {
		super();
		this.symbol = symbol;
	}
	
	public Symbol(T symbol, Double probability) {
		super();
		this.symbol = symbol;
		this.probability = probability;
	}

	public T getSymbol() {
		return symbol;
	}

	public void setSymbol(T symbol) {
		this.symbol = symbol;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("( " + this.symbol.toString() + " , " + this.probability.toString());
		if (label != null) sb.append("(" + label + ")");
		sb.append(")");
		return sb.toString() ;
	}
	

	
	public int hashCode() {
		return this.symbol.hashCode();
	}

	public Object getLabel() {
		return label;
	}

	public void setLabel(Object label) {
		this.label = label;
	}
	
	
	public boolean equals(T obj) {
		return (this.symbol.compareTo(obj) == 0);
	}
	
	public boolean equals(Symbol<T> obj) {
		return ( this.symbol.compareTo(obj.symbol) == 0 );
	}

	public int compareTo(Symbol<T> o) {
		return this.symbol.compareTo(o.symbol);
	}	
	
}
