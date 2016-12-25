package theory.tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import theory.Ensemble;

public class EnsembleTest {

	@Test
	public void test() {
		
		String[] coin = {"0","1"};
		double[] distrib = {0.9,0.1};
	
		Ensemble<String> bentCoin = new Ensemble<String>(new Random(), coin, distrib);
		
		double shannonInformationOfZero = bentCoin.informationOfOccurence("0");
		double shannonInformationOfOne = bentCoin.informationOfOccurence("1");
		
		assertEquals(shannonInformationOfZero, 0.15, 0.1);
		assertEquals(shannonInformationOfOne, 3.3, 0.1);

	}

}
