package compression;

import static org.junit.Assert.*;

import org.junit.Test;

import theory.Interval;
import theory.Symbol;

public class BalancedBinaryArithmeticCodeTableTest {

	
	
	@Test
	public void testFindbinaryInterval1() {
		testFindBinaryInterval(new Interval<String> (new Symbol<String>("0",0.25),0.25,0.25), 0.25, 0.5);
		testFindBinaryInterval(new Interval<String> (new Symbol<String>("0",0.25),0.0,0.75), 0.0, 0.5);
		testFindBinaryInterval(new Interval<String> (new Symbol<String>("0",0.25),0.0,0.125), 0.0, 0.125);
		testFindBinaryInterval(new Interval<String> (new Symbol<String>("0",0.25),0.0,0.1), 0.0, 0.0625);
		testFindBinaryInterval(new Interval<String> (new Symbol<String>("0",0.25),0.9,0.1), 0.9375, 1.0);
	}
	
	
	public void testFindBinaryInterval(Interval<String> i, double expectedmin, double expectedmax) {
		BalancedBinaryArithmeticCodeTable bct = new BalancedBinaryArithmeticCodeTable();
		Interval<String> interval = bct.intervalOf(bct.findBinaryElement(i));
		assertEquals(expectedmin,interval.min,0.0);
		assertEquals(expectedmax,interval.max,0.0);
	}
	

}
