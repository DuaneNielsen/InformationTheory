package theory;

import java.util.*;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.BooleanIndexing;
import org.nd4j.linalg.indexing.conditions.Conditions;
import org.nd4j.linalg.ops.transforms.Transforms;

public class JointEnsemble<X extends Comparable<X>, Y extends Comparable<Y>> implements IJointEnsemble<X, Y> {

	INDArray jointprob;
	INDArray marginalX;
	INDArray marginalY;
	List<Symbol<X>> x = new ArrayList<Symbol<X>>();
	List<Symbol<Y>> y = new ArrayList<Symbol<Y>>();
	Map<X,Symbol<X>> xmap = new HashMap<X,Symbol<X>>();
	Map<Y,Symbol<Y>> ymap = new HashMap<Y,Symbol<Y>>();

	public JointEnsemble(List<Symbol<X>> x, List<Symbol<Y>> y, double[][] probabilities) {
		this.x = x;
		this.y = y;
		
		this.jointprob = Nd4j.create(probabilities);

		INDArray marginalX = jointprob.sum(1);
		INDArray marginalY = jointprob.sum(0);
		assert (marginalX.rows() == x.size());
		assert (marginalY.columns() == y.size());
		
		for (int i = 0; i < marginalX.rows(); i++) {
			x.get(i).setProbability(marginalX.getDouble(i, 0));
		}

		for (int j = 0; j < marginalY.columns(); j++) {
			y.get(j).setProbability(marginalY.getDouble(0, j));
		}
		
		generateMap();
	}
	
	protected void generateMap() {
		xmap = new HashMap<X,Symbol<X>>();
		for (Symbol<X> symbol: this.x) {
			xmap.put(symbol.getSymbol(), symbol);
		}
		ymap = new HashMap<Y,Symbol<Y>>();
		for (Symbol<Y> symbol: this.y) {
			ymap.put(symbol.getSymbol(), symbol);
		}		
	}

	/* (non-Javadoc)
	 * @see theory.IJointEnsemble#marginalX()
	 */
	public IEnsemble<X> marginalRow() {
		IEnsemble<X> ensemble = null;
		try {
			
			ensemble = new Ensemble<X>(new Random(),x);
		} catch (NotAProbabilityDistribution e) {
			e.printStackTrace();
		}
		return ensemble;
	}
	
	/* (non-Javadoc)
	 * @see theory.IJointEnsemble#marginalY()
	 */
	public IEnsemble<Y> marginalColumn() {
		IEnsemble<Y> ensemble = null;
		try {
			ensemble = new Ensemble<Y>(new Random(), y);
		} catch (NotAProbabilityDistribution e) {
			e.printStackTrace();
		}
		return ensemble;
	}
	
	/* (non-Javadoc)
	 * @see theory.IJointEnsemble#getSymbolX(X)
	 */
	public Symbol<X> getSymbolRow(X symbol) throws SymbolNotFound {
		Symbol<X> x = xmap.get(symbol);
		if (x == null) throw new SymbolNotFound();
		return x;	
	}

	/* (non-Javadoc)
	 * @see theory.IJointEnsemble#getSymbolY(Y)
	 */
	public Symbol<Y> getSymbolColumn(Y symbol) throws SymbolNotFound {
		Symbol<Y> y = ymap.get(symbol);
		if (y == null) throw new SymbolNotFound();
		return y;
	}
	
	/* (non-Javadoc)
	 * @see theory.IJointEnsemble#getProbability(theory.Symbol, theory.Symbol)
	 */
	public double getProbability(X sx, Y sy) {
		int row = this.x.indexOf(xmap.get(sx));
		int column = this.y.indexOf(ymap.get(sy));
		return jointprob.getDouble(row, column);
	}
	
	/* (non-Javadoc)
	 * @see theory.IJointEnsemble#entropy()
	 */
	public double entropy() {
		INDArray info = shannonInformation();
		INDArray entropy = jointprob.mul(info);
		return entropy.sumNumber().doubleValue();
	}
	
	/*
	 * returns a matrix with the shannon information in bits of each element in the joint distribution 
	 */
	public INDArray shannonInformation() {
		
		// 1/log(p) = -log(p)
		INDArray logprob = Transforms.log(jointprob, true);
		logprob.negi();		
		// replace NaN with 0.0
		BooleanIndexing.replaceWhere(logprob, 0.0, Conditions.isInfinite());
		//convert to base2 by dividing by log(2)
		INDArray logtwo = Nd4j.onesLike(jointprob).mul(2.0);
		logtwo = Transforms.log(logtwo);
		logprob.divi(logtwo);
		return logprob;
	}
	
}
