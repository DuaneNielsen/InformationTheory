package weighingproblem.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import weighingproblem.Scales;
import weighingproblem.WeighResult;

public class ScaleTest {

	@Test
	public void testEquals() {
		Scales s = new Scales();
		s.leftscale.add(s.heavy);
		s.rightscale.add(s.heavy);
		assertEquals(WeighResult.EQUAL, s.weigh());
	}

	@Test
	public void testLeft() {
		Scales s = new Scales();
		s.leftscale.add(s.light);
		s.rightscale.add(s.heavy);
		assertEquals(WeighResult.LEFT_LIGHT, s.weigh());
	}

	@Test
	public void testRight() {
		Scales s = new Scales();
		s.leftscale.add(s.heavy);
		s.rightscale.add(s.light);
		assertEquals(WeighResult.RIGHT_LIGHT, s.weigh());
	}
	
	
}
