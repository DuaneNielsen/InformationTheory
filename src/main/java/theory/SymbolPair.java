package theory;

public class SymbolPair<ROW extends Comparable<ROW>,COLUMN extends Comparable<COLUMN>> {

	public Symbol<ROW> row; 
	public Symbol<COLUMN> column;
	
	public SymbolPair(Symbol<ROW> row, Symbol<COLUMN> column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	public boolean equals(SymbolPair<ROW,COLUMN> o) {
		return (this.compareTo(o) == 0);
	}
	
	public int compareTo(SymbolPair<ROW,COLUMN> o) {
		int row_dist = row.compareTo(o.row);
		int column_dist = column.compareTo(o.column);
		int distance;
		if (Math.abs(row_dist) > Math.abs(column_dist)) {
			distance = row_dist;
		} else {
			distance = column_dist;
		}
		return distance;
	}
	
	public String toString() {
		return row.toString() + " " + column.toString();
	}
	
}