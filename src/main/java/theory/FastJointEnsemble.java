package theory;

import java.util.*;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.BooleanIndexing;
import org.nd4j.linalg.indexing.conditions.Conditions;
import org.nd4j.linalg.ops.transforms.Transforms;

public class FastJointEnsemble<ROW extends Comparable<ROW>, COLUMN extends Comparable<COLUMN>>
		implements IJointEnsemble<ROW, COLUMN> {

	protected INDArray jointprob;
	protected INDArray information = null;
	protected FastEnsemble<ROW> marginalRow = null;
	protected FastEnsemble<COLUMN> marginalColumn = null;
	protected List<ROW> rows = new ArrayList<ROW>();
	protected List<COLUMN> columns = new ArrayList<COLUMN>();

	public FastJointEnsemble(ROW[] rows, COLUMN[] columns, double[][] probabilities) {
		for (ROW symbol : rows) {
			this.rows.add(symbol);
		}
		for (COLUMN symbol : columns) {
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
		if (marginalRow == null)
			return this.marginalRow = new FastEnsemble<ROW>(rows, jointprob.sum(1).transpose());
		return marginalRow;
	}

	public IEnsemble<COLUMN> marginalColumn() {
		if (marginalColumn == null)
			this.marginalColumn = new FastEnsemble<COLUMN>(columns, jointprob.sum(0));
		return marginalColumn;
	}

	public Symbol<ROW> getSymbolRow(ROW symbol) throws SymbolNotFound {
		Symbol<ROW> s = marginalRow().getSymbol(symbol);
		if (s == null)
			throw new SymbolNotFound();
		return s;
	}

	public Symbol<COLUMN> getSymbolColumn(COLUMN symbol) throws SymbolNotFound {
		Symbol<COLUMN> s = marginalColumn().getSymbol(symbol);
		if (s == null)
			throw new SymbolNotFound();
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

	/**
	 * return the conditional probability of the row types, given the column
	 * 
	 * @param symbol
	 * @return
	 */
	public IEnsemble<ROW> conditionalOnColumn(COLUMN symbol) {
		Number marginal = marginalColumn().getSymbol(symbol).getProbability();
		INDArray conditional = jointprob.getColumn(getColumn(symbol)).mul(marginal).transpose();
		return new FastEnsemble<ROW>(rows, conditional);
	}

	/**
	 * return the conditional probability of the column, given the row
	 * 
	 * @param symbol
	 * @return
	 */
	public IEnsemble<COLUMN> conditionalOnRow(ROW symbol) {
		Number marginal = marginalRow().getSymbol(symbol).getProbability();
		INDArray conditional = jointprob.getRow(getRow(symbol)).div(marginal);
		return new FastEnsemble<COLUMN>(columns, conditional);
	}

	public double entropyColumnConditionalRow() {
		// calculate the marginal probs
		INDArray marginalprobRow = jointprob.sum(1);
		// multiply the givens by the marginals
		INDArray conditionalprobs = jointprob.divColumnVector(marginalprobRow);
		// compute the information content of the conditionals
		INDArray information = information(conditionalprobs);
		// multiply by the conditionals, then add to get the entropy for each conditional
		INDArray conditionalEntropy = information.muli(conditionalprobs).sum(1);
		return conditionalEntropy.mulColumnVector(marginalprobRow).sumNumber().doubleValue();
	}

		
	public double entropyRowConditionalColumn() {
		// calculate the marginal probs
		INDArray marginalprobRowVector = jointprob.sum(0);
		// multiply the givens by the marginals
		INDArray conditionalprobs = jointprob.divRowVector(marginalprobRowVector);
		// compute the information content of the conditionals
		INDArray information = information(conditionalprobs);
		// multiply by the conditionals, then add to get the entropy for each conditional
		INDArray conditionalEntropyRowVector = information.muli(conditionalprobs).sum(0);
		return conditionalEntropyRowVector.mulRowVector(marginalprobRowVector).sumNumber().doubleValue();
	}

	private void lazyEvalShannonInformation() {
		if (information == null) {
			// 1/log(p) = -log(p)
			information = Transforms.log(jointprob, true);
			information.negi();
			// replace NaN with 0.0
			BooleanIndexing.replaceWhere(information, 0.0, Conditions.isInfinite());
			// convert to base2 by dividing by log(2)
			INDArray logtwo = Nd4j.onesLike(jointprob).mul(2.0);
			logtwo = Transforms.log(logtwo);
			information.divi(logtwo);
		}
	}
	
	private INDArray information(INDArray probabilityDistribution) {
		// 1/log(p) = -log(p)
		INDArray information = Transforms.log(probabilityDistribution, true);
		information.negi();
		// replace NaN with 0.0
		BooleanIndexing.replaceWhere(information, 0.0, Conditions.isInfinite());
		// convert to base2 by dividing by log(2)
		INDArray logtwo = Nd4j.onesLike(jointprob).mul(2.0);
		logtwo = Transforms.log(logtwo);
		information.divi(logtwo);
		return information;
	}	

	public Number getMutualInformation() {
		return null;
	}

	private int getRow(ROW row) {
		return rows.indexOf(row);
	}

	private int getColumn(COLUMN column) {
		return columns.indexOf(column);
	}

}
