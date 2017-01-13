package theory;

import java.util.*;

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
 * FastSymbol is designed for lazy evaluation, the design principle is that 
 * memory and compute are not deployed unless required, but once requested
 * results are cached so future evaluations are fast
 * 
 * @author niedu02
 *
 * @param <T>
 */
public class FastEnsemble<T extends Comparable<T>> implements IEnsemble<T> {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	protected List<T> alphabet = new ArrayList<T>();
	protected Map<T,Symbol<T>> symbolizedAlpha = new HashMap<T,Symbol<T>>();;
	
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

	public FastEnsemble(T[] alphabet, INDArray probVector ) {
		toList(alphabet);
		this.probVector = probVector;
	}	
	
	public FastEnsemble(T[] alphabet, double[] probVector ) {
		toList(alphabet);
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
		List<Symbol<T>> listOfSymbols = new ArrayList<Symbol<T>>();
		// consider adding a second arraylist for symbols
		for (T key: alphabet) {
			listOfSymbols.add(getSymbol(key));
		}
		return listOfSymbols;
	}

	public int alphabetLength() {
		return alphabet.size();
	}

	public Symbol<T> getSymbol(T key) {
		Symbol<T> s = symbolizedAlpha.get(alphabet.indexOf(key));
		if (s != null) return s;
		else {
			s = new Symbol<T>(key,probabilityOfOccurence(key));
			symbolizedAlpha.put(key, s);
		}
		return s;
	}
	
	private int columnOf(T key) {
		return alphabet.indexOf(key);
	}
	
	private int columnOf(Symbol<T> key) {
		return alphabet.indexOf(key.getSymbol());
	}
	
	private void toList(T[] alphabet) {
		this.alphabet = new ArrayList<T>();
		for (T o: alphabet) {
			this.alphabet.add(o);
		}
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
	
}
