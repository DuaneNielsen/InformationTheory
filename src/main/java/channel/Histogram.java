package channel;

//Reads an input file of test scores (integers) and displays a
//text histogram of the score distribution.

import java.util.*;

public class Histogram {
 public static final int CURVE = 6;   // adjustment to each exam score
 
	public static void printIntegers(List<Integer> list)  {

		// array of counters of test scores from 0 - 100 inclusive
		int[] counts = new int[101];

		// read file into counts array
		// example: if score is 87, then do counts[87]++

		Iterator<Integer> it = list.iterator();

		while (it.hasNext()) {
			int score = it.next();

			// curve the exam score (but cap it at 100 points)
			score = Math.min(score + CURVE, 100);

			counts[score]++;
		}

		// print stars for each student who got a particular score
		// example: if counts[87] is 6, print "87: ******"
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0) {
				System.out.print(i + ": ");
				for (int j = 0; j < counts[i]; j++) {
					System.out.print("*");
				}
				System.out.println();

			}
		}
	}
	
	public static void printDouble(List<Double> list)  {

		// array of counters of test scores from 0 - 100 inclusive
		int[] counts = new int[101];

		// read file into counts array
		// example: if score is 87, then do counts[87]++

		Iterator<Double> it = list.iterator();

		while (it.hasNext()) {
			double score = it.next();
			counts[quantize(score)]++;
		}

		// print stars for each student who got a particular score
		// example: if counts[87] is 6, print "87: ******"
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0) {
				System.out.print(i + ": ");
				for (int j = 0; j < counts[i]; j++) {
					System.out.print("*");
				}
				System.out.println();

			}
		}
	}
	
	// return integer between 0 and 100
	private static int quantize(double probablity) {
		return (int)(probablity * 100);
	}
	
}