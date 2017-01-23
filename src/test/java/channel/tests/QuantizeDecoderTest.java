package channel.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import channel.QuantizeDecoder;

public class QuantizeDecoderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDecode() {
		
		Map<String,String> code = new HashMap<String,String>();
		code.put("0", "00");
		code.put("1", "11");
		
		QuantizeDecoder d = new QuantizeDecoder(code, 1);
		
		assertEquals("00",d.decode("0"));
		assertEquals("11",d.decode("1"));
		assertEquals("0011",d.decode("01"));
		assertEquals("1100",d.decode("10"));
	}

}
