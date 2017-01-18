package theory.tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import theory.Symbol;
import theory.SymbolPair;

public class SymbolPairTest {

	Symbol<String> a = new Symbol<String>("a");
	Symbol<String> b = new Symbol<String>("b");
	Symbol<String> c = new Symbol<String>("c");
	Symbol<String> d = new Symbol<String>("d");
	
	Symbol<String> _1 = new Symbol<String>("_1");
	Symbol<String> _2 = new Symbol<String>("_2");
	Symbol<String> _3 = new Symbol<String>("_3");
	Symbol<String> _4 = new Symbol<String>("_4");
	
	SymbolPair<String,String> s_a_1 = new SymbolPair<String,String>(a,_1);
	SymbolPair<String,String> s_a_4 = new SymbolPair<String,String>(a,_4);
	SymbolPair<String,String> s_d_1 = new SymbolPair<String,String>(d,_1);
	SymbolPair<String,String> s_d_4 = new SymbolPair<String,String>(d,_4);
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		assertTrue(s_a_1.equals(s_a_1));
		assertFalse(s_a_1.equals(s_a_4));
		assertTrue(s_a_1.equals(new SymbolPair<String,String>(new Symbol<String>("a"), new Symbol<String>("_1"))));
	}
	
}
