package theory.tests;

import static org.junit.Assert.*;


import org.junit.Test;

import theory.*;

public class IntervalTests {

	@Test
	public void testEqual() {
		Interval<String> i1 = new Interval<String>(new Symbol<String>("a",0.3),0.0,0.3);
		Interval<String> i2 = new Interval<String>(new Symbol<String>("b",0.3),0.5,0.3);
		assertFalse(i1.adjacent(i1));
		assertTrue(i1.contains(i1));
		assertTrue(i1.equal(i1));
		assertFalse(i2.adjacent(i2));
		assertTrue(i2.contains(i2));
		assertTrue(i2.equal(i2));
	}	
	
	@Test
	public void testAdjacent() {
		Interval<String> i1 = new Interval<String>(new Symbol<String>("a",0.5),0.0,0.5);
		Interval<String> i2 = new Interval<String>(new Symbol<String>("b",0.5),0.5,0.5);
		assertTrue(i1.adjacent(i2));
		assertTrue(i2.adjacent(i1));
		assertFalse(i1.contains(i2));
		assertFalse(i2.contains(i1));
	}

	@Test
	public void testContains() {
		Interval<String> i1 = new Interval<String>(new Symbol<String>("a",0.5),0.0,0.5);
		Interval<String> i2 = new Interval<String>(new Symbol<String>("b",0.3),0.1,0.3);
		assertFalse(i1.adjacent(i2));
		assertFalse(i2.contains(i1));
		assertTrue(i1.contains(i2));
		assertTrue(i2.isContainedBy(i1));
	}	
	
	@Test
	public void testApart() {
		Interval<String> i1 = new Interval<String>(new Symbol<String>("a",0.4),0.0,0.4);
		Interval<String> i2 = new Interval<String>(new Symbol<String>("b",0.3),0.5,0.3);
		assertFalse(i1.adjacent(i2));
		assertFalse(i1.contains(i2));
		assertFalse(i2.contains(i1));
		assertTrue(i1.apart(i2));
		assertTrue(i2.apart(i1));
	}		

	@Test
	public void testOverlaps() {
		Interval<String> i1 = new Interval<String>(new Symbol<String>("a",0.4),0.0,0.4);
		Interval<String> i2 = new Interval<String>(new Symbol<String>("b",0.3),0.3,0.3);
		assertFalse(i1.adjacent(i2));
		assertFalse(i1.contains(i2));
		assertFalse(i2.contains(i1));
		assertFalse(i1.apart(i2));
		assertFalse(i2.apart(i1));
		assertTrue(i1.overlaps(i2));
		assertTrue(i2.overlaps(i1));
		assertEquals(0.1, i2.overlap(i1),0.001);
		assertEquals(0.1, i1.overlap(i2),0.001);
	}		
	
	
}
