package compression;

import java.util.*;

import com.github.duanielsen.trie.TrieElement;

import theory.Ensemble;
import theory.Interval;
import theory.NotAProbabilityDistribution;
import theory.Symbol;

public class BalancedBinaryArithmeticCodeTable extends ArithmeticCodeTable<String> {

	public static String[] binaryAlphabet = { "0", "1" };
	public static double[] binaryProbability = { 0.5, 0.5 };
	public Ensemble<String> binaryEnsemble;

	public BalancedBinaryArithmeticCodeTable() {
		super();
		try {
			binaryEnsemble = new Ensemble<String>(new Random(), binaryAlphabet, binaryProbability);
		} catch (NotAProbabilityDistribution e) {
			e.printStackTrace();
		}
	}
	
	TrieElement<Symbol<String>> findBinaryElement(Interval<?> inputInterval) {
		return this.findBinaryElement(inputInterval, this.binaryEnsemble);
	}
	
	
//	TrieElement<Symbol<String>> findBinaryElement(Interval<?> inputInterval) {
//		TrieElement<Symbol<String>> current = predictor.getRoot();
//		TrieElement<Symbol<String>> sideWithMostOverlap = null;
//
//		Interval<String> binaryInterval = null;
//
//		while (true) {
//			double maxOverlap = 0.0;
//			this.spawnAllChildren(current, binaryEnsemble);
//			for (TrieElement<Symbol<String>> child : current.getChildren()) {
//				binaryInterval = this.intervalOf(child);
//
//				if (binaryInterval.isContainedBy(inputInterval)) {
//					return child;
//				}
//				// slip to the side with the larger overlap
//				if (binaryInterval.overlaps(inputInterval)) {
//					double overlap = binaryInterval.overlap(inputInterval);
//					if (overlap > maxOverlap) {
//						maxOverlap = overlap;
//						sideWithMostOverlap = child;
//					}
//				}
//			}
//			current = sideWithMostOverlap;
//		}
//	}

}
