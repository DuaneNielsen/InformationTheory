package compression;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import theory.Ensemble;
import theory.IEnsemble;
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
	}

	private void printResults(IEnsemble<String> ensemble) {
		double ratio = (double)totalLengthCompressed/(double)totalLengthInput;

		System.out.println();
		System.out.print(ensemble + " ");
		System.out.print("totalLengthCompressed " +  totalLengthCompressed + " totalLengthInput " + totalLengthInput  );
		System.out.print(" compression ratio " + ratio);
		System.out.println(" Entropy of ensemble : " + ensemble.entropy());
		System.out.println();
		System.out.println();
		totalLengthCompressed = 0;
		totalLengthInput = 0;
		runs = 0;
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
		trialRun(bentCoin, 20);
	}

	@Test
	public void testCompressCoolCoin() throws NotAProbabilityDistribution {
		BentCoinEnsemble bentCoin = new BentCoinEnsemble(new double[] { 0.999, 0.001 });
		trialRun(bentCoin, 20);
	}
	
	@Test
	public void testCompressHardCoin() throws NotAProbabilityDistribution {
		BentCoinEnsemble bentCoin = new BentCoinEnsemble(new double[] { 0.6, 0.4 });
		trialRun(bentCoin, 20);
	}	

	@Test
	public void testCompressPerfectCoin() throws NotAProbabilityDistribution {
		BentCoinEnsemble bentCoin = new BentCoinEnsemble(new double[] { 0.5, 0.5 });
		trialRun(bentCoin, 20);
	}	
	
	@Test
	public void testYinglish() throws NotAProbabilityDistribution {
		Ensemble<String> bentCoin = new Ensemble<String>(new Random(), new String[] {"a","b","c","d"}, new double[] { 0.5, 0.1 ,0.1,0.3});
		trialRun(bentCoin, 20);
	}		
	
	private void trialRun(Ensemble<String> bentCoin, int runs) {
		for (int i = 0; i < runs; i++) {
			testCoder(generate(bentCoin, 20), bentCoin);
		}
		printResults(bentCoin);
	}
	
	
	
	private String generate(Ensemble<String> ensemble, int length) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < length; i++) {
			s.append(ensemble.generate().getSymbol());
		}
		return s.toString();
	}

	private void testCoder(String input, IEnsemble<String> bentCoin) {
		ArithmeticCoder coder = new ArithmeticCoder(bentCoin);
		
		String compressed = coder.compress(input);
		
		totalLengthCompressed += compressed.length();
		totalLengthInput += input.length();
		runs++;
		
		String decompressed = coder.decompress(compressed, input.length());
		System.out.print(input); System.out.println(" : " + compressed + " : " + decompressed);

		
		assertEquals(input,decompressed);
	}
	
	

}
