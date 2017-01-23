package channel.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import channel.IEncoder;
import channel.QuantizeEncoder;
import channel.QuantizerFactory;

public class QuantizeEncoderTest {

	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEncode() {
		IEncoder q = QuantizerFactory.twoBit().getEncoder();
		assertEquals("0",q.encode("00"));
		assertEquals("00",q.encode("0001"));
		assertEquals("0011",q.encode("00011011"));
		assertEquals("0011",q.encode("0001101"));
	}

}
