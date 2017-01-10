package theory.tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import theory.JointEnsemble;
import theory.Symbol;

public class JointEnsembleTest {

	List<Symbol<String>> symbolsX = new ArrayList<Symbol<String>>();
	List<Symbol<String>> symbolsY = new ArrayList<Symbol<String>>();
	double [][] jointprobability;
	
	@Before
	public void setUp() throws Exception {
		symbolsX.add(new Symbol<String>("Cloudy"));
		symbolsX.add(new Symbol<String>("Clear"));
		symbolsX.add(new Symbol<String>("Overcast"));
		symbolsX.add(new Symbol<String>("Sunny"));
		
		symbolsY.add(new Symbol<String>("Hot"));
		symbolsY.add(new Symbol<String>("Cold"));
		symbolsY.add(new Symbol<String>("Balmy"));
		symbolsY.add(new Symbol<String>("Crisp"));

		
		jointprobability = new double[][]{
			{1.0/8.0 ,1.0/16.0 ,1.0/32.0 ,1.0/32.0},
			{1.0/16.0,1.0/8.0 ,1.0/32.0 ,1.0/32.0},
			{1.0/16.0,1.0/16.0,1.0/16.0,1.0/16.0},
			{1.0/4.0 ,0.0/1.0,0.0/1.0,0.0/1.0},
			};
	}

	@Test
	public void testJointEnsemble() {
		JointEnsemble<String,String> joint = new JointEnsemble<String,String>(symbolsX, symbolsY, jointprobability);
	}

	@Test
	public void testMarginalX() {
		fail("Not yet implemented");
	}

}
