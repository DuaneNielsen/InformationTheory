package theory;

import java.util.ArrayList;
import java.util.List;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.BooleanIndexing;
import org.nd4j.linalg.indexing.conditions.Conditions;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Ensemble is used to describe a single random variable in information theory
 * 
 * The Ensemble is a set of symbols that occur each with a given probability
 * 
 * Symbols in this ensemble can be any Comparable java object
 * 
 * @author niedu02
 *
 * @param <T>
 */
public class FastEnsemble<T extends Comparable<T>> implements IEnsemble<T> {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	protected List<T> alphabet = new ArrayList<T>();
	protected List<Symbol<T>> symbolizedAlpha = null;
	
	/** note that you will need to decide if you
	 * want double or float precision because INDArrays
	 * globally are assigned double/float
	 * 
	 * to set double add the below line to the app initialization
	 * DataTypeUtil.setDTypeForContext(DataBuffer.Type.DOUBLE);
	 * 
	 */
	protected INDArray probVector;
	protected INDArray infoVector = null;
	protected Double entropyOfEnsemble = null;

	public FastEnsemble(T[] alphabet, double[] probVector ) {
		this.alphabet = new ArrayList<T>();
		for (T o: alphabet) {
			this.alphabet.add(o);
		}
		this.probVector = Nd4j.create(probVector);
	}
	
	public FastEnsemble(List<T> alphabet, double[] probVector ) {
		this.alphabet = alphabet;
		this.probVector = Nd4j.create(probVector);
	}
	
	public FastEnsemble(List<T> alphabet, INDArray probVector ) {
		this.alphabet = alphabet;
		this.probVector = probVector;
	}
	
	public double probabilityOfOccurence(T symbol) {
		return probVector.getDouble(0, columnOf(symbol));
	}

	public double probabilityOfOccurence(Symbol<T> symbol) {
		return probVector.getDouble(0, columnOf(symbol));
	}
	
	public double informationOfOccurence(Symbol<T> symbol) {
		lazyEvalInfoVector();
		return infoVector.getDouble(0, columnOf(symbol));
	}

	public double informationOfOccurence(T symbol) {
		lazyEvalInfoVector();
		return infoVector.getDouble(0, columnOf(symbol));
	}

	public double entropy() {
		if (entropyOfEnsemble != null) return entropyOfEnsemble;
		lazyEvalInfoVector();
		INDArray infoSum = probVector.mul(infoVector);
		this.entropyOfEnsemble = infoSum.sumNumber().doubleValue();
		return this.entropyOfEnsemble;
	}

	public List<Symbol<T>> getAlphabet() {
		lazyBuildAlphabet();
		return symbolizedAlpha;
	}

	public int alphabetLength() {
		return alphabet.size();
	}

	public Symbol<T> getSymbol(T key) {
		lazyBuildAlphabet();
		return symbolizedAlpha.get(alphabet.indexOf(key));
	}
	
	private int columnOf(T key) {
		return alphabet.indexOf(key);
	}
	
	private int columnOf(Symbol<T> key) {
		return alphabet.indexOf(key.getSymbol());
	}
	
	private void lazyEvalInfoVector() {
		if ( infoVector == null ) {
			// 1/log(p) = -log(p)
			infoVector = Transforms.log(probVector, true);
			infoVector.negi();		
			// replace NaN with 0.0
			BooleanIndexing.replaceWhere(infoVector, 0.0, Conditions.isInfinite());
			//convert to base2 by dividing by log(2)
			INDArray logtwo = Nd4j.onesLike(probVector).mul(2.0);
			logtwo = Transforms.log(logtwo);
			infoVector.divi(logtwo);
		}
	}
	
	private void lazyBuildAlphabet(){
		if  ( symbolizedAlpha == null ) {
			symbolizedAlpha = new ArrayList<Symbol<T>>();
			for (T o: alphabet) {
				symbolizedAlpha.add(new Symbol<T>(o,probabilityOfOccurence(o)));
			}
		}
	}
	
}
