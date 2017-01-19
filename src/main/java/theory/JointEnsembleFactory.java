package theory;

import java.util.*;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

public class JointEnsembleFactory {

	/**
	 * returns a joint ensemble where all probabilities are equal (uniform) 
	 * 
	 * 
	 * @param rows
	 * @param columns
	 * @return
	 */
	public static IJointEnsemble<Integer,Integer> uniform(int rows, int columns) {
		INDArray uniform = Nd4j.zeros(rows, columns);
		double uniformprob = 1.0/((double)rows*(double)columns); 
		uniform.addi(uniformprob);
		List<Integer> input = integerSymbols(rows); 
		List<Integer> output = integerSymbols(columns);		
		return new FastJointEnsemble<Integer,Integer>(input, output, uniform);
	}
	
	public static IJointEnsemble<Integer,Integer> maxMutualMaxMarginal(int rows, int columns) {
		
		double divisor = 1.0/(rows);
		INDArray probabilities = createPermutationMatrix(rows, columns);
		probabilities.muli(divisor);	 
		List<Integer> input = integerSymbols(rows); 
		List<Integer> output = integerSymbols(columns);			
		return new FastJointEnsemble<Integer, Integer>(input, output, probabilities);
	}
	
	/**
	 * Creates a matrix in the form
	 * 
	 * | 0.75 , 0.25 |
	 * | 0.25 , 0.75 |
	 * 
	 * based on the noise parameter 0.5
	 * 
	 * The idea is increasing the noise parameter will reduce the confidence about anything a learner can conclude
	 * for all conditionals in the distribution uniformly
	 * 
	 * @param rows
	 * @param columns
	 * @param noise
	 * @return
	 */
	public static IJointEnsemble<Integer,Integer> maxMarginalWithUniformNoise(int rows, int columns, double noise) {
		
		INDArray perm = createPermutationMatrix(rows, columns);
		INDArray noiseBase = Nd4j.ones(rows, columns);
		noiseBase.divi(columns);
		perm.muli((1.0-noise));
		noiseBase.muli(noise);
		perm.addi(noiseBase);
		perm.divi(rows);
		List<Integer> input = integerSymbols(rows); 
		List<Integer> output = integerSymbols(columns);			
		return new FastJointEnsemble<Integer, Integer>(input, output, perm);
	}
	

	public static INDArray createPermutationMatrix(int rows, int columns) {
		
		if (rows % columns != 0 ) return null;
		
		INDArray identity = Nd4j.eye(columns);
		int copies = rows/columns;
		
		INDArray replicant = Nd4j.zeros(rows,columns);
		for (int c = 0; c < copies; c++ ) {
			INDArrayIndex[] index = new INDArrayIndex[2];
			int begin = c*identity.rows();
			int end = begin + identity.rows();
			index[0] = NDArrayIndex.interval(begin, end);
			index[1] = NDArrayIndex.all();
			replicant.put(index,identity);				
		}
		return replicant;
	}
	
	
	public static List<Integer> integerSymbols(int length) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < length; i++) {
			list.add(i);
		}
		return list;
	}
	
}
