package channel.tests;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;


import channel.*;
import theory.Ensemble;

public class ChannelTest {
	
	public ChannelTest() {};

	@Test
	public void runBinaryChannel() {
		Driver driver = new Driver();
		driver.channel = new BinaryChannel(0.1);
		ResultSet resultSet = driver.run(10, 10);
		System.out.println(resultSet);
		assertEquals(0.1, resultSet.errorRate, 0.1);
		assertEquals(1.00, resultSet.rate, 0.01);
	}

	@Test
	public void runTriplicateVSBinaryChannel() {
		Driver driver = new Driver();
		driver.channel = new BinaryChannel(0.1);
		driver.encoder = new TriplicateEncoder();
		driver.decoder = new TriplicateDecoder();
		ResultSet resultSet = driver.run(10, 10);
		System.out.println(resultSet);
		assertEquals(0.03, resultSet.errorRate, 0.06);
		assertEquals(0.33, resultSet.rate, 0.01);
	}
	
	@Test
	public void runTriplicateVSBinaryChannelManyTimesAndHistogram() {
		Driver driver = new Driver();
		driver.channel = new BinaryChannel(0.1);
		driver.encoder = new TriplicateEncoder();
		driver.decoder = new TriplicateDecoder();
		
		List<ResultSet> results = new ArrayList<ResultSet>();
		List<Double> errorRate = new ArrayList<Double>();
		List<Double> rate = new ArrayList<Double>();

		int i = 0;
		while ( i < 100 ) {
			i++;
			ResultSet resultset = driver.run(10, 10);
			resultset.computeStats();
			results.add(resultset);
			errorRate.add(resultset.errorRate);
			rate.add(resultset.rate);
			assertEquals("Error Rate out of range", 0.03, resultset.errorRate,  0.06);
			assertEquals("Transmission rate unexpected ", 0.33, resultset.rate, 0.01);
		}
		
		System.out.println("errorRate");
		Histogram.printDouble(errorRate);
		System.out.println();
		System.out.println("rate");
		Histogram.printDouble(rate);
		System.out.println();;
	}

	@Test
	public void runBentCoinEnsemble() {
		
		System.out.println("Running Bent Coin Using Ensemble");
		
		Driver driver = new Driver();

		// set up a bent coin using generic Ensemble notation
		String[] coin = {"0","1"};  // alphabet
		double[] distrib = {0.9,0.1}; // distribution
		Ensemble<String> bentCoin = new Ensemble<String>(new Random(), coin, distrib);
		
		driver.generator = bentCoin;
		driver.channel = new BinaryChannel(0.1);
		ResultSet resultSet = driver.run(10, 10);
		System.out.println(resultSet);
		
		assertEquals(0.1, resultSet.errorRate, 0.1);
		assertEquals(1.0, resultSet.rate, 0.01);
	}		
	
	
}
