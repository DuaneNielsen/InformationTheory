package utils.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import utils.Trie;
import utils.TrieElement;

public class TrieTest {

	@Test
	public void testPath() {
		
		Trie<String> t = new Trie<String>();
		
		List<TrieElement<String>> l = t.addChild("a").addChild("b").addChild("r").addChild("a").addChild("c").addChild("a").addChild("d").addChild("a").addChild("b").addChild("r").addChild("a").getPath();
		
		String s = new String();
		
		for(TrieElement<String> c: l) {
			s = s.concat(c.getValue());
		}
		
		assertEquals("abracadabra",s);
	
	}

	@Test
	public void testPathOfValues() {
		
		Trie<String> t = new Trie<String>();
		
		List<String> l = t.addChild("a").addChild("b").addChild("r").addChild("a").addChild("c").addChild("a").addChild("d").addChild("a").addChild("b").addChild("r").addChild("a").getPathOfValues();
		
		String s = new String();
		
		for(String c: l) {
			s = s.concat(c);
		}
		
		assertEquals("abracadabra",s);
	
	}	
	
	@Test
	public void findChild() {
		Trie<String> t = new Trie<String>();
		
		TrieElement<String> leaf = t.addChild("a").addChild("b").addChild("r").addChild("a").addChild("c").addChild("a").addChild("d").addChild("a").addChild("b").addChild("r").addChild("a");
		
		
		List<String> l = leaf.getPathOfValues();
		
		TrieElement<String> current = t.getRoot();
		for(String c: l) {
			current = current.findChild(c);
			assertTrue(current != null);
		}
		
	}
	
}
