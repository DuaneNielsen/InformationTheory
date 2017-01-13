package theory.tests;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.commons.math3.analysis.function.Log;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.buffer.util.DataTypeUtil;
import org.nd4j.linalg.api.ndarray.INDArray;

import theory.FastJointEnsemble;
import theory.IEnsemble;
import theory.IJointEnsemble;
import theory.JointEnsemble;
import theory.Symbol;
import theory.SymbolNotFound;

public class JointEnsembleTest {

	List<Symbol<String>> symbolsX = new ArrayList<Symbol<String>>();
	List<Symbol<String>> symbolsY = new ArrayList<Symbol<String>>();
	double[][] jointprobability;
	IJointEnsemble<String, String> joint;
	FastJointEnsemble<String, String> fastjoint;
	
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
		
		DataTypeUtil.setDTypeForContext(DataBuffer.Type.DOUBLE);
		fastjoint = new FastJointEnsemble<String, String>(new String[]{"Cloudy","Clear","Overcast","Sunny"}, 
				new String[]{"Hot","Cold","Balmy","Crisp"}, jointprobability);

	}

	@Test
	public void testJointEnsemble() throws SymbolNotFound {

		Symbol<String> cloudy = joint.getSymbolX("Cloudy");
		Symbol<String> sunny = joint.getSymbolX("Sunny");
		Symbol<String> hot = joint.getSymbolY("Hot");
		Symbol<String> crisp = joint.getSymbolY("Crisp");

		assertEquals(1.0 / 8.0, joint.getProbability("Cloudy", "Hot"), 0.0);
		assertEquals(1.0 / 32.0, joint.getProbability("Cloudy", "Crisp"), 0.0);
		assertEquals(1.0 / 4.0, joint.getProbability("Sunny", "Hot"), 0.0);
		assertEquals(0.0 / 8.0, joint.getProbability("Sunny", "Crisp"), 0.0);

	}
	
	@Test
	public void testFastJointEnsemble() throws SymbolNotFound {

		DataTypeUtil.setDTypeForContext(DataBuffer.Type.DOUBLE);
		joint = new FastJointEnsemble<String, String>(new String[]{"Cloudy","Clear","Overcast","Sunny"}, 
				new String[]{"Hot","Cold","Balmy","Crisp"}, jointprobability);		

		assertEquals(1.0 / 8.0, joint.getProbability("Cloudy", "Hot"), 0.0);
		assertEquals(1.0 / 32.0, joint.getProbability("Cloudy", "Crisp"), 0.0);
		assertEquals(1.0 / 4.0, joint.getProbability("Sunny", "Hot"), 0.0);
		assertEquals(0.0 / 8.0, joint.getProbability("Sunny", "Crisp"), 0.0);

	}	

	@Test
	public void testMarginals() {
		IEnsemble<String> row = joint.marginalRow();
		IEnsemble<String> column = joint.marginalColumn();
		Symbol<String> cloudy = row.getSymbol("Cloudy");
		Symbol<String> sunny = row.getSymbol("Sunny");
		Symbol<String> hot = column.getSymbol("Hot");
		Symbol<String> crisp = column.getSymbol("Crisp");
		
		assertEquals(1.0/4.0,cloudy.getProbability(),0.0);
		assertEquals(1.0/4.0,sunny.getProbability(),0.0);

		assertEquals(1.0/2.0,hot.getProbability(),0.0);
		assertEquals(1.0/8.0,crisp.getProbability(),0.0);
		
		assertEquals(7.0/4.0,column.entropy(),0.0);
		assertEquals(2.0, row.entropy(),0.0);
	}	
	
	@Test
	public void testFastMarginals() {
		DataTypeUtil.setDTypeForContext(DataBuffer.Type.DOUBLE);
		joint = new FastJointEnsemble<String, String>(new String[]{"Cloudy","Clear","Overcast","Sunny"}, 
				new String[]{"Hot","Cold","Balmy","Crisp"}, jointprobability);
		IEnsemble<String> row = joint.marginalRow();
		IEnsemble<String> column = joint.marginalColumn();
		Symbol<String> cloudy = row.getSymbol("Cloudy");
		Symbol<String> sunny = row.getSymbol("Sunny");
		Symbol<String> hot = column.getSymbol("Hot");
		Symbol<String> crisp = column.getSymbol("Crisp");
		
		assertEquals(1.0/4.0,cloudy.getProbability(),0.0);
		assertEquals(1.0/4.0,sunny.getProbability(),0.0);

		assertEquals(1.0/2.0,hot.getProbability(),0.0);
		assertEquals(1.0/8.0,crisp.getProbability(),0.0);
		
		assertEquals(2.0,row.entropy(),0.0);
		assertEquals(7.0/4.0, column.entropy(),0.0);
	}
	
	@Test
	public void testShannon() throws SymbolNotFound {
		Symbol<String> cloudy = joint.getSymbolX("Cloudy");
		Symbol<String> sunny = joint.getSymbolX("Sunny");
		Symbol<String> hot = joint.getSymbolY("Hot");
		Symbol<String> crisp = joint.getSymbolY("Crisp");
		
		INDArray info = joint.shannonInformation();
		
		double p = joint.getProbability("Cloudy", "Hot");
		
		assertEquals(1.0/8.0,p,0.0);
		assertEquals(informationOfOccurence(p), info.getDouble(0, 0),0.0);
		
		//System.out.println(info);
		double entropy = joint.entropy();
		assertEquals(27.0/8.0, entropy, 0.0);
		
	}

	@Test
	public void testFastShannon() throws SymbolNotFound {
		DataTypeUtil.setDTypeForContext(DataBuffer.Type.DOUBLE);
		joint = new FastJointEnsemble<String, String>(new String[]{"Cloudy","Clear","Overcast","Sunny"}, 
				new String[]{"Hot","Cold","Balmy","Crisp"}, jointprobability);
		
		INDArray info = joint.shannonInformation();
		
		double p = joint.getProbability("Cloudy", "Hot");
		
		assertEquals(1.0/8.0,p,0.0);
		assertEquals(informationOfOccurence(p), info.getDouble(0, 0),0.0);
		
		//System.out.println(info);
		double entropy = joint.entropy();
		assertEquals(27.0/8.0, entropy, 0.0);
		
	}
	
	@Test
	public void conditionalProbabability() {
		assertEquals(1.0/2.0,fastjoint.conditionalOnRow("Cloudy").probabilityOfOccurence("Hot"),0.0);
		assertEquals(1.0/4.0,fastjoint.conditionalOnRow("Cloudy").probabilityOfOccurence("Cold"),0.0);
		assertEquals(1.0/8.0,fastjoint.conditionalOnRow("Cloudy").probabilityOfOccurence("Balmy"),0.0);
		assertEquals(1.0/8.0,fastjoint.conditionalOnRow("Cloudy").probabilityOfOccurence("Crisp"),0.0);
		
		assertEquals(1.0/4.0,fastjoint.conditionalOnRow("Clear").probabilityOfOccurence("Hot"),0.0);
		assertEquals(1.0/2.0,fastjoint.conditionalOnRow("Clear").probabilityOfOccurence("Cold"),0.0);
		assertEquals(1.0/8.0,fastjoint.conditionalOnRow("Clear").probabilityOfOccurence("Balmy"),0.0);
		assertEquals(1.0/8.0,fastjoint.conditionalOnRow("Clear").probabilityOfOccurence("Crisp"),0.0);
	}
	
	
	public double informationOfOccurence(double p) {
		Log log = new Log();
		double info =  log.value((1.0)/p) /log.value(2);
		if (Double.isFinite(info)) {return info;} else {return (double)0.0;}
	}
	
	
}
