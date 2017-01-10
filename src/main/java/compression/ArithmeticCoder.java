package compression;

import java.util.*;

import com.github.duanielsen.trie.TrieElement;

import theory.Ensemble;
import theory.Interval;
import theory.Symbol;

public class ArithmeticCoder {

	ArithmeticCodeTable<String> predictor = new ArithmeticCodeTable<String>();
	BalancedBinaryArithmeticCodeTable bct = new BalancedBinaryArithmeticCodeTable();
	public Ensemble<String> ensemble;

	public ArithmeticCoder(Ensemble<String> ensemble) {
		super();
		this.ensemble = ensemble;
	}	
	
	public String compress(String input) {
		
		List<Symbol<String>> inputWord = word(input, ensemble);
		TrieElement<Symbol<String>> inputElement = predictor.findWord(inputWord, ensemble);
		Interval<String> inputInterval = predictor.intervalOf(inputElement);
		TrieElement<Symbol<String>> binaryElement = bct.findBinaryElement(inputInterval);
		List<Symbol<String>> compressedWord = binaryElement.getPathOfValues();
		return toString(compressedWord);
	}
	
	public String decompress(String compressed, int length) {
		
		List<Symbol<String>> binaryWord = word(compressed,bct.binaryEnsemble);
		TrieElement<Symbol<String>> compressedElement = bct.findWord(binaryWord, bct.binaryEnsemble);
		Interval<String> compressedInterval = bct.intervalOf(compressedElement);
		TrieElement<Symbol<String>> decompressedLeaf = predictor.findProbableElementAtDepth(compressedInterval,ensemble, length);
		List<Symbol<String>> decompressedWord = decompressedLeaf.getPathOfValues();
		return toString(decompressedWord);
	}

	private List<Symbol<String>> word(String input, Ensemble<String> ensemble ) {
		List<Symbol<String>> word = new ArrayList<Symbol<String>>();
		for (char c: input.toCharArray() ) {
			String s = Character.toString(c);
			Symbol<String> symbol = ensemble.findSymbol(s);
			word.add(symbol);
		}
		return word;
	}

	private String toString(List<Symbol<String>> word) {
		StringBuilder sb = new StringBuilder();
		for (Symbol<String> s: word) {
			sb.append(s.getSymbol());
		}
		return sb.toString();
	}
	
}
