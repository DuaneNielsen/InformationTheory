package theory;

import java.util.*;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class JointEnsemble<X extends Comparable<X>, Y extends Comparable<Y>> {

	INDArray jointprob;
	INDArray marginalX;
	INDArray marginalY;
	List<Symbol<X>> x = new ArrayList<Symbol<X>>();
	List<Symbol<Y>> y = new ArrayList<Symbol<Y>>();

	public JointEnsemble(List<Symbol<X>> x, List<Symbol<Y>> y, double[][] probabilities) {
		this.x = x;
		this.y = y;
		
		this.jointprob = Nd4j.create(probabilities);

		System.out.println(this.jointprob);
		INDArray marginalX = jointprob.sum(0);
		INDArray marginalY = jointprob.sum(1);
		System.out.println(marginalX);
		System.out.println(marginalY);
		assert (marginalX.columns() == x.size());
		assert (marginalY.columns() == y.size());
		for (int i = 0; i < marginalX.columns(); i++) {
			x.get(i).setProbability(marginalX.getDouble(0, i));
			y.get(i).setProbability(marginalY.getDouble(0, i));
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

}
