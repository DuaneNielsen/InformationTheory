package theory.tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import theory.Ensemble;
import theory.EnsembleOld;
import theory.NotAProbabilityDistribution;

public class EnsembleTest {

	@Test
	public void test() throws NotAProbabilityDistribution {
		
		String[] coin = {"0","1"};
		double[] distrib = {0.9,0.1};
	
		EnsembleOld<String> bentCoin = new EnsembleOld<String>(new Random(), coin, distrib);
		
		double shannonInformationOfZero = bentCoin.informationOfOccurence("0");
		assertEquals(shannonInformationOfZero, 0.15, 0.01);
		
		double shannonInformationOfOne = bentCoin.informationOfOccurence("1");
		assertEquals(shannonInformationOfOne, 3.3, 0.1);
		
		double shannonEntropy = bentCoin.entropy();
		assertEquals(0.465, shannonEntropy, 0.01 );

	}
	
	@Test
	public void testNewEnsemble() throws NotAProbabilityDistribution {
		
		String[] coin = {"0","1"};
		double[] distrib = {0.9,0.1};
	
		Ensemble<String> bentCoin = new Ensemble<String>(new Random(), coin, distrib);
		
		double shannonInformationOfZero = bentCoin.informationOfOccurence("0");
		assertEquals(shannonInformationOfZero, 0.15, 0.01);
		
		double shannonInformationOfOne = bentCoin.informationOfOccurence("1");
		assertEquals(shannonInformationOfOne, 3.3, 0.1);
		
		double shannonEntropy = bentCoin.entropy();
		assertEquals(0.465, shannonEntropy, 0.01 );

	}	

}
