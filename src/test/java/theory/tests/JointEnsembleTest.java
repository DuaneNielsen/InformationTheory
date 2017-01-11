package theory.tests;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.commons.math3.analysis.function.Log;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;

import theory.Ensemble;
import theory.JointEnsemble;
import theory.Symbol;
import theory.SymbolNotFound;

public class JointEnsembleTest {

	List<Symbol<String>> symbolsX = new ArrayList<Symbol<String>>();
	List<Symbol<String>> symbolsY = new ArrayList<Symbol<String>>();
	double[][] jointprobability;
	JointEnsemble<String, String> joint;

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

		jointprobability = new double[][] { 
			{ 1.0 / 8.0,  1.0 / 16.0, 1.0 / 32.0, 1.0 / 32.0 },
			{ 1.0 / 16.0, 1.0 / 8.0,  1.0 / 32.0, 1.0 / 32.0 }, 
			{ 1.0 / 16.0, 1.0 / 16.0, 1.0 / 16.0, 1.0 / 16.0 },
			{ 1.0 / 4.0,  0.0 / 1.0,  0.0 / 1.0,  0.0 / 1.0 }, };

		joint = new JointEnsemble<String, String>(symbolsX, symbolsY, jointprobability);

	}

	@Test
	public void testJointEnsemble() throws SymbolNotFound {

		Symbol<String> cloudy = joint.getSymbolX("Cloudy");
		Symbol<String> sunny = joint.getSymbolX("Sunny");
		Symbol<String> hot = joint.getSymbolY("Hot");
		Symbol<String> crisp = joint.getSymbolY("Crisp");

		assertEquals(1.0 / 8.0, joint.getProbability(cloudy, hot), 0.0);
		assertEquals(1.0 / 4.0, joint.getProbability(cloudy, crisp), 0.0);
		assertEquals(1.0 / 32.0, joint.getProbability(sunny, hot), 0.0);
		assertEquals(0.0 / 8.0, joint.getProbability(sunny, crisp), 0.0);

	}

	@Test
	public void testMarginals() {
		Ensemble<String> marginalX = joint.marginalX();
		Ensemble<String> marginalY = joint.marginalY();
		Symbol<String> cloudy = marginalX.findSymbol("Cloudy");
		Symbol<String> sunny = marginalX.findSymbol("Sunny");
		Symbol<String> hot = marginalY.findSymbol("Hot");
		Symbol<String> crisp = marginalY.findSymbol("Crisp");
		
		assertEquals(1.0/2.0,cloudy.getProbability(),0.0);
		assertEquals(1.0/8.0,sunny.getProbability(),0.0);

		assertEquals(1.0/4.0,hot.getProbability(),0.0);
		assertEquals(1.0/4.0,crisp.getProbability(),0.0);
		
		assertEquals(7.0/4.0,marginalX.entropy(),0.0);
		assertEquals(2.0, marginalY.entropy(),0.0);
	}
	
	@Test
	public void testShannon() throws SymbolNotFound {
		Symbol<String> cloudy = joint.getSymbolX("Cloudy");
		Symbol<String> sunny = joint.getSymbolX("Sunny");
		Symbol<String> hot = joint.getSymbolY("Hot");
		Symbol<String> crisp = joint.getSymbolY("Crisp");
		
		INDArray info = joint.shannonInformation();
		
		double p = joint.getProbability(cloudy, hot);
		assertEquals(informationOfOccurence(p), info.getDouble(0, 0),0.0);
		
		//System.out.println(info);
		double entropy = joint.entropy();
		assertEquals(27.0/8.0, entropy, 0.0);
		
	}

	public double informationOfOccurence(double p) {
		Log log = new Log();
		double info =  log.value((1.0)/p) /log.value(2);
		if (Double.isFinite(info)) {return info;} else {return (double)0.0;}
	}
	
	
}
