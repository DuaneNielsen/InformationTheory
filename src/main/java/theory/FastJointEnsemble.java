package theory;

import java.util.*;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.BooleanIndexing;
import org.nd4j.linalg.indexing.conditions.Conditions;
import org.nd4j.linalg.ops.transforms.Transforms;

public class FastJointEnsemble<ROW extends Comparable<ROW>, COLUMN extends Comparable<COLUMN> > implements IJointEnsemble<ROW, COLUMN> {

	protected INDArray jointprob;
	protected INDArray information = null;
	protected FastEnsemble<ROW> marginalRow = null;
	protected FastEnsemble<COLUMN> marginalColumn = null;	
	protected List<ROW> rows = new ArrayList<ROW>();
	protected List<COLUMN> columns = new ArrayList<COLUMN>();
	
	public FastJointEnsemble(ROW[] rows, COLUMN[] columns, double[][] probabilities) {
		for (ROW symbol: rows) {
			this.rows.add(symbol);
		}
		for (COLUMN symbol: columns) {
			this.columns.add(symbol);
		}
		this.jointprob = Nd4j.create(probabilities);
	}
	
	public FastJointEnsemble(List<ROW> rows, List<COLUMN> columns, double[][] probabilities) {
		this.rows = rows;
		this.columns = columns;
		this.jointprob = Nd4j.create(probabilities);
	}	

	public FastJointEnsemble(List<ROW> rows, List<COLUMN> columns, INDArray probabilities) {
		this.rows = rows;
		this.columns = columns;
		this.jointprob = probabilities;
	}		
	
	public IEnsemble<ROW> marginalRow() {
		if (marginalRow == null)  return this.marginalRow = new FastEnsemble<ROW>(rows,jointprob.sum(0));
		return marginalRow;
	}

	public IEnsemble<COLUMN> marginalColumn() {
		if (marginalColumn == null)  this.marginalColumn = new FastEnsemble<COLUMN>(columns,jointprob.sum(1).transpose());
		return marginalColumn;
	}

	public Symbol<ROW> getSymbolX(ROW symbol) throws SymbolNotFound {
		Symbol<ROW> s = marginalRow().getSymbol(symbol);	
		if (s == null) throw new SymbolNotFound();
		return s;
	}

	public Symbol<COLUMN> getSymbolY(COLUMN symbol) throws SymbolNotFound {
		Symbol<COLUMN> s = marginalColumn().getSymbol(symbol);	
		if (s == null) throw new SymbolNotFound();
		return s;
	}

	public double getProbability(ROW sx, COLUMN sy) {
		return jointprob.getDouble(getRow(sx), getColumn(sy));
	}

	public double entropy() {
		lazyEvalShannonInformation();
		return jointprob.mul(information).sumNumber().doubleValue();
	}

	public INDArray shannonInformation() {
		lazyEvalShannonInformation();
		return information;
	}
	
	private void lazyEvalShannonInformation(){
		if (information == null) {
			// 1/log(p) = -log(p)
			information = Transforms.log(jointprob, true);
			information.negi();		
			// replace NaN with 0.0
			BooleanIndexing.replaceWhere(information, 0.0, Conditions.isInfinite());
			//convert to base2 by dividing by log(2)
			INDArray logtwo = Nd4j.onesLike(jointprob).mul(2.0);
			logtwo = Transforms.log(logtwo);
			information.divi(logtwo);	
		}
	}

	private int getRow(ROW row) {
		return rows.indexOf(row);
	}
	
	private int getColumn(COLUMN column) {
		return columns.indexOf(column);
	}
	
}
