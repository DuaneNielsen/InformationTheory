package compression;

import theory.*;
import utils.Trie;
import utils.TrieElement;

import java.util.*;

public class ArithmeticCoder<S extends Comparable<S>> {

	public Trie<Symbol<S>> predictor = new Trie<Symbol<S>>();

	public Interval<S> intervalOf(List<Symbol<S>> word, Ensemble<S> ensemble) {

		TrieElement<Symbol<S>> wordRoot = findWord(word, ensemble);
		double length = probabilityOfWord(wordRoot);
		double offset = offsetOfWord(wordRoot);
		return new Interval<S>(wordRoot.getValue(), offset, length);
	}

	/**
	 * gives the root symbol for the word, creates the word if it does not exist
	 * 
	 * also generates all siblings along the words path
	 * 
	 * @param word
	 * @return the root symbol of the word
	 */
	public TrieElement<Symbol<S>> findWord(List<Symbol<S>> word, Ensemble<S> ensemble) {
		TrieElement<Symbol<S>> current = predictor.getRoot();
		TrieElement<Symbol<S>> next = null;

		for (Symbol<S> symbol : word) {
			next = current.findChild(symbol);
			if (next == null) {
				spawnAllChildren(current, ensemble);
				current = current.findChild(symbol);
			} else {
				current = next;
			}
		}

		return current;
	}

	/**
	 * populates all children for the given element using probabilities and
	 * symbols in the ensemble
	 * 
	 * @param parent
	 * @param ensemble
	 * @return
	 */

	public TrieElement<Symbol<S>> spawnAllChildren(TrieElement<Symbol<S>> parent, Ensemble<S> ensemble) {
		for (Symbol<S> symbol : ensemble.getAlphabet()) {
			if (parent.findChild(symbol) == null) {
				parent.addChild(symbol);
			}
		}
		return parent;
	}

	public Double probabilityOfWord(TrieElement<Symbol<S>> wordRoot) {
		double probability = wordRoot.getValue().getProbability();
		TrieElement<Symbol<S>> current = wordRoot.getParent();
		while (current.getParent() != null) {
			probability = probability * current.getValue().getProbability();
			current = current.getParent();
		}
		return probability;
	}

	public Double offsetOfWord(TrieElement<Symbol<S>> wordRoot) {
		TrieElement<Symbol<S>> current = wordRoot;
		double offset = 0;

		while (current.getParent() != null) {
			for (TrieElement<Symbol<S>> sibling : current.getOlderSiblings()) {
				offset += probabilityOfWord(sibling);
			}
			current = current.getParent();
		}
		return offset;
	}

}
