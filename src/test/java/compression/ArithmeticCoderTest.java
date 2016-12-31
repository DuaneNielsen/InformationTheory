package compression;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import theory.Ensemble;
import theory.NotAProbabilityDistribution;

public class ArithmeticCoderTest {

	public int totalLengthCompressed;
	public int totalLengthInput;
	public int runs;
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() {
		
		double ratio = (double)totalLengthCompressed/(double)totalLengthInput;
		System.out.println("totalLengthCompressed " +  totalLengthCompressed + " totalLengthInput " + totalLengthInput  );
		System.out.println("compression ratio " + ratio);
	}
	
	// I think this test shows the limits of using double to encode probabilities
	public void glitchyCompress() throws NotAProbabilityDistribution {
		String input = "00100000000011001100000001010000000000010000100000000000000001100110000000000100";
		BentCoinEnsemble bentCoin = new BentCoinEnsemble(new double[] { 0.9, 0.1 });
		testCoder(input, bentCoin);
	}

	
	@Test
	public void testCompress() throws NotAProbabilityDistribution {
		String input = "00000000";
		BentCoinEnsemble bentCoin = new BentCoinEnsemble(new double[] { 0.9, 0.1 });
		testCoder(input, bentCoin);
		
	}
	
	@Test
	public void testCompressRandom() throws NotAProbabilityDistribution {
		BentCoinEnsemble bentCoin = new BentCoinEnsemble(new double[] { 0.9, 0.1 });
		for (int i = 0; i < 10; i++) {
			testCoder(generate(bentCoin, 4), bentCoin);
		}
	}
	
	private String generate(Ensemble<String> ensemble, int length) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < length; i++) {
			s.append(ensemble.generate().getSymbol());
		}
		return s.toString();
	}

	private void testCoder(String input, BentCoinEnsemble bentCoin) {
		ArithmeticCoder coder = new ArithmeticCoder(bentCoin);
		
		String compressed = coder.compress(input);
		
		totalLengthCompressed += compressed.length();
		totalLengthInput += input.length();
		runs++;
		
		String decompressed = coder.decompress(compressed);
		System.out.print(input); System.out.println(" : " + compressed + " : " + decompressed);
		//assertTrue(input.length() + 2 >= compressed.length());
	}
	
	

}
