package compression;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;

import theory.Ensemble;
import theory.Interval;
import theory.NotAProbabilityDistribution;
import theory.Symbol;
import utils.TrieElement;

public class ArithmeticCodeTableTest {

	
	
	@Test
	public void test() throws NotAProbabilityDistribution {
		
		ArithmeticCodeTable<String> coder = new ArithmeticCodeTable<String>();
		
		String[] coin = {"0","1"};
		double[] distrib = {0.9,0.1};
	
		Ensemble<String> bentCoin = new Ensemble<String>(new Random(), coin, distrib);
		ArrayList<Symbol<String>> word = new ArrayList<Symbol<String>>();
		Symbol<String> zero = bentCoin.findSymbol("0");
		Symbol<String> one = bentCoin.findSymbol("1");
		word.add(zero);
		word.add(zero);
		word.add(zero);
		word.add(zero);
		word.add(zero);
		word.add(zero);

		 TrieElement<Symbol<String>> wordRoot = coder.findWord(word, bentCoin);
		
		assertEquals(Math.pow(zero.getProbability(), wordRoot.getPath().size() ), coder.probabilityOfWord(wordRoot), 0.001 );
		System.out.println(coder.intervalOf(word,bentCoin));
		
	}

	@Test
	public void testBinaryDistribution() throws NotAProbabilityDistribution {
		
		ArithmeticCodeTable<String> coder = new ArithmeticCodeTable<String>();
		
		String[] coin = {"0","1"};
		double[] distrib = {0.5,0.5};
	
		Ensemble<String> bentCoin = new Ensemble<String>(new Random(), coin, distrib);
		ArrayList<Symbol<String>> word1 = new ArrayList<Symbol<String>>();
		ArrayList<Symbol<String>> word0 = new ArrayList<Symbol<String>>();
		Symbol<String> zero = bentCoin.findSymbol("0");
		Symbol<String> one = bentCoin.findSymbol("1");
		word0.add(zero);
		word1.add(one);

		TrieElement<Symbol<String>> wordOne = coder.findWord(word0, bentCoin);
		TrieElement<Symbol<String>> wordZero = coder.findWord(word1, bentCoin);
		Interval<String> interval0 = coder.intervalOf(word0,bentCoin);
		Interval<String> interval1 = coder.intervalOf(word1,bentCoin);

		assertEquals(Math.pow(zero.getProbability(), wordOne.getPath().size() ), coder.probabilityOfWord(wordOne), 0.001 );
		assertEquals(0.0, interval0.min, 0.0);
		assertEquals(0.5, interval0.max, 0.0);
		assertEquals(0.5, interval1.min, 0.0);
		assertEquals(1.0, interval1.max, 0.0);
	}
	
	@Test
	public void testBinaryToDepth3() throws NotAProbabilityDistribution {
		ArithmeticCodeTable<String> coder = new ArithmeticCodeTable<String>();
		
		String[] coin = {"0","1"};
		double[] distrib = {0.5,0.5};
	
		Ensemble<String> bentCoin = new Ensemble<String>(new Random(), coin, distrib);

		int depth = 3;
		List<List<Symbol<String>>> list = new CopyOnWriteArrayList<List<Symbol<String>>>();
		list.add(new ArrayList<Symbol<String>>());
		
		while (depth > 0) {
			depth--;
			Iterator<List<Symbol<String>>> iter = list.iterator(); 
			while (iter.hasNext()) {
				List<Symbol<String>> word = iter.next();
				for(Symbol<String> symbol : bentCoin.getAlphabet()) {
					ArrayList<Symbol<String>> newWord = new ArrayList<Symbol<String>>(word);
					newWord.add(symbol);
					list.add(newWord);
				}
				list.remove(word);
			}
		}
		
		
		double offset = 0;
		for (List<Symbol<String>> word: list) {		

			Interval<String> interval = coder.intervalOf(word,bentCoin);
			System.out.println(word + " " + interval);
			
			assertEquals(offset, interval.min, 0.0);
			assertEquals(offset + 0.125, interval.max, 0.0);
			offset += 0.125;
		}
		
	}

	@Test
	public void testBentDistribution() throws NotAProbabilityDistribution {
		ArithmeticCodeTable<String> coder = new ArithmeticCodeTable<String>();
		
		String[] coin = {"0","1"};
		double[] distrib = {0.9,0.1};
	
		Ensemble<String> bentCoin = new Ensemble<String>(new Random(), coin, distrib);

		int depth = 3;
		List<List<Symbol<String>>> list = new CopyOnWriteArrayList<List<Symbol<String>>>();
		list.add(new ArrayList<Symbol<String>>());
		
		while (depth > 0) {
			depth--;
			Iterator<List<Symbol<String>>> iter = list.iterator(); 
			while (iter.hasNext()) {
				List<Symbol<String>> word = iter.next();
				for(Symbol<String> symbol : bentCoin.getAlphabet()) {
					ArrayList<Symbol<String>> newWord = new ArrayList<Symbol<String>>(word);
					newWord.add(symbol);
					list.add(newWord);
				}
				list.remove(word);
			}
		}
		
		
		double offset = 0;
		for (List<Symbol<String>> word: list) {		

			Interval<String> interval = coder.intervalOf(word,bentCoin);
			System.out.println(word + " " + interval);
			
			//assertEquals(offset, interval.min, 0.0);
			//assertEquals(offset + 0.125, interval.max, 0.0);
			offset += 0.125;
		}
		
	}	
	
}
