package theory.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import theory.Symbol;

public class SymbolTest {

	Symbol<String> a = new Symbol<String>("a");
	Symbol<String> b = new Symbol<String>("b");
	Symbol<String> c = new Symbol<String>("c");
	Symbol<String> d = new Symbol<String>("d");
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		assertTrue(a.equals(a));
		assertFalse(a.equals(b));
		assertTrue(a.equals(new Symbol<String>("a")));
	}

}
