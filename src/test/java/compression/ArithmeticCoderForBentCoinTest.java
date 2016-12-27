package compression;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;
import compression.ArithmeticCoderForBentCoin;
import theory.NotAProbabilityDistribution;

public class ArithmeticCoderForBentCoinTest {

	@Test
	public void test() throws NotAProbabilityDistribution {
		ArithmeticCoderForBentCoin coder = new ArithmeticCoderForBentCoin();
		coder.bentCoin = new BentCoinEnsemble(new double[] { 0.5, 0.5 });
		coder.splitAllByDistribution(coder.bentCoin);
		System.out.println(coder.codingTable.getDistribAsList());
		coder.splitAllByDistribution(coder.bentCoin);
		System.out.println(coder.codingTable.getDistribAsList());
		coder.splitAllByDistribution(coder.bentCoin);
		System.out.println(coder.codingTable.getDistribAsList());
	}
	
	
	
	
}
