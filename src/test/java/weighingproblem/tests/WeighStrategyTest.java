package weighingproblem.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import theory.NotAProbabilityDistribution;
import weighingproblem.Scales;
import weighingproblem.WeighStrategy;

public class WeighStrategyTest {

	@Test
	public void testEntropyOf6v6Step() throws NotAProbabilityDistribution {
		assertEquals(1.0, new WeighStrategy().entropyOf6v6Step(),0.01);
	}

	@Test
	public void testEntropyOf5v5Step() throws NotAProbabilityDistribution {
		assertEquals(1.48, new WeighStrategy().entropyOf5v5Step(),0.01);
	}
	
	@Test
	public void testEntropyOf4v4Step() throws NotAProbabilityDistribution {
		assertEquals(1.58, new WeighStrategy().entropyOf4v4Step(),0.01);
	}
	
	@Test
	public void testEntropyOf3v3Step() throws NotAProbabilityDistribution {
		assertEquals(1.50, new WeighStrategy().entropyOf3v3Step(),0.01);
	}	
	
	@Test
	public void testGenericFunction() throws NotAProbabilityDistribution {
		assertEquals(1.0, new WeighStrategy().entropyOfWeighing(12, 12),0.01);
		assertEquals(1.48, new WeighStrategy().entropyOfWeighing(10, 12),0.01);
		assertEquals(1.58, new WeighStrategy().entropyOfWeighing(8, 12),0.01);
		assertEquals(1.50, new WeighStrategy().entropyOfWeighing(6, 12),0.01);
		assertEquals(1.25, new WeighStrategy().entropyOfWeighing(4, 12),0.01);
		assertEquals(0.82, new WeighStrategy().entropyOfWeighing(2, 12),0.01);
	}
	
	@Test
	public void weighStrategyTest() throws Exception {
		for (int i =0; i < 10; i++) {
			assertEquals (1, new WeighStrategy().greedyEntropyStrategy(new Scales()));
			System.out.println();
		}
	}

	@Test
	public void weighStrategyTest13() throws Exception {
		for (int i =0; i < 10; i++) {
			assertEquals (1, new WeighStrategy().greedyEntropyStrategy(new Scales(13)));
			System.out.println();
		}
	}

	@Test
	public void weighStrategyTest203() throws Exception {
		for (int i =0; i < 10; i++) {
			assertEquals (1, new WeighStrategy().greedyEntropyStrategy(new Scales(203)));
			System.out.println();
		}
	}
	
	@Test
	public void testBinaryArithmetic() {
		assertEquals(12, (13 & (Integer.MAX_VALUE - 1) ));
	}
	
}
