package theory.tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.buffer.util.DataTypeUtil;

import theory.*;

public class GeneratorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String[] coin = {"0","1"};
		double[] distrib = {0.5,0.5};
		
		DataTypeUtil.setDTypeForContext(DataBuffer.Type.DOUBLE);
		IEnsemble<String> bentCoin = new FastEnsemble<String>(coin, distrib);
		NaiveEnsembleGenerator<String> gen = new NaiveEnsembleGenerator<String>(new Random(), bentCoin);
		
		int count0 = 0;
		int count1 = 0;
		int number_trials = 10000;
		
		for (int i = 0; i < number_trials; i++) {
			Symbol<String> s = gen.generateRandom();
			if (s.getSymbol() == "0") {
				count0++;
			}
			else count1++;
		}
		
		assertEquals(distrib[0],(double)count0/(double)number_trials,0.01 );
		assertEquals(distrib[1],(double)count1/(double)number_trials,0.01 );
	}

}
