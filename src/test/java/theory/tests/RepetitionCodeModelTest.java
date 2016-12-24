package theory.tests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.junit.*;

import theory.RepetitionCodeModel;

public class RepetitionCodeModelTest {

	public RepetitionCodeModel rcm = new RepetitionCodeModel();
	private NumberFormat formatter = new DecimalFormat("#0.000000");
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void test() {
		
		double f = 0.1;
		
		double errorRate = rcm.probabilityOfErrorOnTriplicate(f);
		
		double estimateErrorRate = 3 * Math.pow(f, 2);
		
		double tolerance = 0.05;
		
		if ((errorRate <= estimateErrorRate + tolerance) || (errorRate >= estimateErrorRate - tolerance)) {
			System.out.println("Expected errorRate " + formatter.format(errorRate) + " with tolerance " + formatter.format(tolerance) + " and got " + formatter.format(errorRate));
			return;
		}
		
		fail("Expected errorRate " + formatter.format(errorRate) + " with tolerance " + formatter.format(tolerance) + " but got " + formatter.format(errorRate));
	}
	
	@Test
	public void testModelTriplicate() {
		
		double f = 0.1;
		
		double errorRate = rcm.probabilityOfErrorOnTriplicate(f);
		
		double calculatedRate = rcm.probabilityOfError(f, 3);
		
		assertEquals(errorRate, calculatedRate, 0.000001);

	}
	
	@Test
	public void testModelQuadlicate() {
		
		double f = 0.1;
		
		double errorRate = rcm.probabilityOfErrorOnQuadlicate(f);
		
		double calculatedRate = rcm.probabilityOfError(f, 4);
		
		assertEquals(errorRate, calculatedRate, 0.000001);

	}

}
