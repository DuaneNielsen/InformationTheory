package channel;

import java.util.HashMap;
import java.util.Map;

public class QuantizerFactory {
	
	protected IEncoder encoder;
	protected IDecoder decoder;
	
	protected int intervalSize;
	protected int indexSize;
	
	static public QuantizerFactory twoBit() {
		
		QuantizerFactory qf = new QuantizerFactory();
		
		Map<String,String> encode = new HashMap<String,String>();
		encode.put("00", "0");
		encode.put("01", "0");
		encode.put("10", "1");
		encode.put("11", "1");
		
		qf.encoder = new QuantizeEncoder(encode, 2);

		Map<String,String> decode = new HashMap<String,String>();
		decode.put("0", "00");
		decode.put("1", "11");
		
		qf.decoder = new QuantizeDecoder(decode, 1);
		
		return qf;
	}

	
	
	public IEncoder getEncoder() {
		return encoder;
	}

	public IDecoder getDecoder() {
		return decoder;
	}
	
	
	
	
}
