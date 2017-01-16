package theory.tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.buffer.util.DataTypeUtil;

import theory.FastJointEnsemble;
import theory.IJointEnsemble;
import theory.JointEnsemble;
import theory.NaiveJointEnsembleGenerator;
import theory.Symbol;
import theory.SymbolPair;

public class JointGeneratorTest {
	List<Symbol<String>> symbolsX = new ArrayList<Symbol<String>>();
	List<Symbol<String>> symbolsY = new ArrayList<Symbol<String>>();
	double[][] jointprobability;
	IJointEnsemble<String, String> joint;
	FastJointEnsemble<String, String> fastjoint;
	NaiveJointEnsembleGenerator<String, String> gen;
	
	Symbol<String> a = new Symbol<String>("a");
	Symbol<String> b = new Symbol<String>("b");
	Symbol<String> c = new Symbol<String>("c");
	Symbol<String> d = new Symbol<String>("d");
	
	Symbol<String> _1 = new Symbol<String>("_1");
	Symbol<String> _2 = new Symbol<String>("_2");
	Symbol<String> _3 = new Symbol<String>("_3");
	Symbol<String> _4 = new Symbol<String>("_4");
	
	
	@Before
	public void setUp() throws Exception {
		
		symbolsX.add(a);
		symbolsX.add(b);
		symbolsX.add(c);
		symbolsX.add(d);

		symbolsY.add(_1);
		symbolsY.add(_2);
		symbolsY.add(_3);
		symbolsY.add(_4);

		jointprobability = new double[][] { 
			{ 1.0 / 8.0,  1.0 / 16.0, 1.0 / 32.0, 1.0 / 32.0 },
			{ 1.0 / 16.0, 1.0 / 8.0,  1.0 / 32.0, 1.0 / 32.0 }, 
			{ 1.0 / 16.0, 1.0 / 16.0, 1.0 / 16.0, 1.0 / 16.0 },
			{ 1.0 / 4.0,  0.0 / 1.0,  0.0 / 1.0,  0.0 / 1.0 }, };

		joint = new JointEnsemble<String, String>(symbolsX, symbolsY, jointprobability);
		
		DataTypeUtil.setDTypeForContext(DataBuffer.Type.DOUBLE);
		fastjoint = new FastJointEnsemble<String, String>(new String[]{"a","b","c","d"}, 
				new String[]{"_1","_2","_3","_4"}, jointprobability);
		gen = new NaiveJointEnsembleGenerator<String, String>(new Random(), new Random(), fastjoint);

	}

	@Test
	public void test() {
		
		int num_trials = 10000;
		SymbolPair<String,String> s_a_1 = new SymbolPair<String,String>(a,_1);
		SymbolPair<String,String> s_a_4 = new SymbolPair<String,String>(a,_4);
		SymbolPair<String,String> s_d_1 = new SymbolPair<String,String>(d,_1);
		SymbolPair<String,String> s_d_4 = new SymbolPair<String,String>(d,_4);
		int a_1 = 0;
		int a_4 = 0;
		int d_1 = 0;
		int d_4 = 0;
		
		for (int i = 0; i < num_trials; i++ ) {
			SymbolPair<String,String> pair = gen.generateRandom(); 
			if (pair.equals(s_a_1)) {a_1++;}
			if (pair.equals(s_a_4)) a_4++;
			if (pair.equals(s_d_1)) d_1++;
			if (pair.equals(s_d_4)) d_4++;
		}

		assertEquals(fastjoint.getProbability(a.getSymbol(), _4.getSymbol()), (double)a_1/(double)num_trials,0.01);
		assertEquals(fastjoint.getProbability(a.getSymbol(), _1.getSymbol()), (double)a_4/(double)num_trials,0.01);
		assertEquals(fastjoint.getProbability(d.getSymbol(), _4.getSymbol()), (double)d_1/(double)num_trials,0.01);
		assertEquals(fastjoint.getProbability(d.getSymbol(), _1.getSymbol()), (double)d_4/(double)num_trials,0.01);
		
	}

}
