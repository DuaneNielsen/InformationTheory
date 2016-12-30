package theory.tests;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

import theory.Ensemble;
import theory.Interval;
import theory.IntervalSet;
import theory.NotAProbabilityDistribution;

public class IntervalSetTest {

	@Test
	public void testBentCoinIntervalSet() throws NotAProbabilityDistribution {
		
		String[] coin = {"0","1"};
		double[] distrib = {0.9,0.1};
	
		Ensemble<String> bentCoin = new Ensemble<String>(new Random(), coin, distrib);
		IntervalSet<String> bentCoinIntervals = bentCoin.toInterval();
		
		System.out.println(bentCoinIntervals);
		
		// assert that the two intervals in the set are adjacent
		Iterator<Interval<String>> iter = bentCoinIntervals.iterator();
		Interval<String> first = iter.next();
		Interval<String> second = iter.next();
		assertTrue(first.adjacent(second));
	}

}
