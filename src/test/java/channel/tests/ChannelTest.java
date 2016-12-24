package channel.tests;

import java.util.*;

import org.junit.*;


import channel.*;

public class ChannelTest {
	
	public ChannelTest() {};

	@Test
	public void runBinaryChannel() {
		Driver driver = new Driver();
		driver.channel = new BinaryChannel(0.1);
		ResultSet resultSet = driver.run(10, 10);
		System.out.println(resultSet);
	}

	@Test
	public void runTriplicateVSBinaryChannel() {
		Driver driver = new Driver();
		driver.channel = new BinaryChannel(0.1);
		driver.encoder = new TriplicateEncoder();
		driver.decoder = new TriplicateDecoder();
		ResultSet resultSet = driver.run(10, 10);
		System.out.println(resultSet);
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
		}
		
		System.out.println("errorRate");
		Histogram.printDouble(errorRate);
		System.out.println();
		System.out.println("rate");
		Histogram.printDouble(rate);
		System.out.println();;
	}
	
	
}
