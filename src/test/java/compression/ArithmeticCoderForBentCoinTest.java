package compression;

import static org.junit.Assert.*;

import org.junit.Test;
import compression.ArithmeticCoderForBentCoin;
import theory.NotAProbabilityDistribution;

public class ArithmeticCoderForBentCoinTest {

	@Test
	public void testFairCoin() throws NotAProbabilityDistribution {
		ArithmeticCoderForBentCoin coder = new ArithmeticCoderForBentCoin();
		coder.bentCoin = new BentCoinEnsemble(new double[] { 0.5, 0.5 });
		testCoder(coder);
		assertEquals(0,0);
	}
	
	@Test
	public void testBentCoin() throws NotAProbabilityDistribution {
		ArithmeticCoderForBentCoin coder = new ArithmeticCoderForBentCoin();
		coder.bentCoin = new BentCoinEnsemble(new double[] { 0.9, 0.1 });
		testCoder(coder);
		assertEquals(0,0);
	}

	@Test
	public void testBentCoin6040() throws NotAProbabilityDistribution {
		ArithmeticCoderForBentCoin coder = new ArithmeticCoderForBentCoin();
		coder.bentCoin = new BentCoinEnsemble(new double[] { 0.6, 0.4 });
		testCoder(coder);
		assertEquals(0,0);
	}
	
	
	protected void testCoder(ArithmeticCoderForBentCoin coder) throws NotAProbabilityDistribution {
		coder.splitCodingTableByDistribution(coder.bentCoin);
//		System.out.println(coder.codingTable);
		coder.splitCodingTableByDistribution(coder.bentCoin);
//		System.out.println(coder.codingTable);
		coder.splitCodingTableByDistribution(coder.bentCoin);
//		System.out.println(coder.codingTable);
//		System.out.println(coder.codingTable.toInterval());
//		System.out.println(coder.binaryDistributionDepth());
//		System.out.println(coder.findDeepestBinaryInterval());
		coder.labelDeepestIntervals();
		System.out.println(coder.binaryTable);
		System.out.println();
	}

}
