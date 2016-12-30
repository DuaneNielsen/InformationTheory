package utils;

public class Trie<T extends Comparable<T>> {
	
	protected TrieElement<T> root = new TrieElement<T>(null, null);
	
	public TrieElement<T> getRoot() {
		return root;
	}
	
	public TrieElement<T> addChild(T value) {
		return root.addChild(value);
	}
	
}
