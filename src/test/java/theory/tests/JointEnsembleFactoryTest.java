package theory.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.shape.Shape;
import org.nd4j.linalg.factory.Nd4j;

import theory.IJointEnsemble;
import theory.JointEnsembleFactory;

public class JointEnsembleFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		IJointEnsemble<Integer, Integer> joint = JointEnsembleFactory.maxMutualMaxMarginal(4, 2);
		
		System.out.println(joint.marginalColumn().getAlphabet());
		
		assertEquals(1.0,joint.marginalColumn().entropy(),0.0);
		assertEquals(2.0,joint.marginalRow().entropy(),0.0);
	}
	
	@Test
	public void testPermutation() {
		INDArray perm = JointEnsembleFactory.createPermutationMatrix(4, 2);
		
		System.out.println(perm);
		
		
		perm = JointEnsembleFactory.createPermutationMatrix(16, 2);
		INDArray columnVector = perm.sum(1);
		
		INDArray testColumnVector = Nd4j.ones(16, 1);
		INDArray testColumnVectorZero = Nd4j.zeros(16, 1);
		
		System.out.println(columnVector);
		
		assertTrue(columnVector.equals(testColumnVector));
		assertFalse(columnVector.equals(testColumnVectorZero));
		
		assertTrue(JointEnsembleFactory.createPermutationMatrix(5, 3)==null);
		
		
	}
	
	@Test
	public void testMaxMarginalWithNoise() {
		IJointEnsemble<Integer, Integer> joint = JointEnsembleFactory.maxMarginalWithUniformNoise(2, 2, 0.5);
		
		System.out.println(joint);
		assertTrue(joint.isAValidProbabilityDistribution(0.2));

	}
	
}
