package theory;

import java.util.*;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

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
	
	public static List<Integer> integerSymbols(int length) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < length; i++) {
			list.add(i);
		}
		return list;
	}
	
}
