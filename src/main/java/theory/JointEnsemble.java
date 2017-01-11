package theory;

import java.util.*;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.BooleanIndexing;
import org.nd4j.linalg.indexing.conditions.Conditions;
import org.nd4j.linalg.ops.transforms.Transforms;

public class JointEnsemble<X extends Comparable<X>, Y extends Comparable<Y>> {

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

		INDArray marginalX = jointprob.sum(0);
		INDArray marginalY = jointprob.sum(1);
		System.out.println(y.size());
		System.out.println(marginalY.rows());
		System.out.println(marginalY);
		assert (marginalX.columns() == x.size());
		assert (marginalY.rows() == y.size());
		
		for (int i = 0; i < marginalX.columns(); i++) {
			x.get(i).setProbability(marginalX.getDouble(0, i));
		}

		for (int j = 0; j < marginalY.rows(); j++) {
			y.get(j).setProbability(marginalY.getDouble(j, 0));
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

	public Ensemble<X> marginalX() {
		Ensemble<X> ensemble = null;
		try {
			ensemble = new Ensemble<X>(new Random(), x);
		} catch (NotAProbabilityDistribution e) {
			e.printStackTrace();
		}
		return ensemble;
	}
	
	public Ensemble<Y> marginalY() {
		Ensemble<Y> ensemble = null;
		try {
			ensemble = new Ensemble<Y>(new Random(), y);
		} catch (NotAProbabilityDistribution e) {
			e.printStackTrace();
		}
		return ensemble;
	}
	
	public Symbol<X> getSymbolX(X symbol) throws SymbolNotFound {
		Symbol<X> x = xmap.get(symbol);
		if (x == null) throw new SymbolNotFound();
		return x;	
	}

	public Symbol<Y> getSymbolY(Y symbol) throws SymbolNotFound {
		Symbol<Y> y = ymap.get(symbol);
		if (y == null) throw new SymbolNotFound();
		return y;
	}
	
	public double getProbability(Symbol<X> sx, Symbol<Y> sy) {
		int column = this.x.indexOf(sx);
		int row = this.y.indexOf(sy);
		return jointprob.getDouble(row, column);
	}
	
	public double entropy() {
		INDArray info = shannonInformation();
		System.out.println(info);
		INDArray entropy = jointprob.mul(info);
		System.out.println(entropy);
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
