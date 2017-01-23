package channel;

import java.util.*;

public class QuantizeEncoder implements IEncoder {

	protected int intervalSize;
	protected int indexSize;
	
	protected Map<String,String> code = new HashMap<String,String>();

	public QuantizeEncoder(Map<String,String> code, int intervalSize) {
		this.intervalSize = intervalSize;
		this.code = code;
	}
	
	public String encode(String signal) {
		StringBuffer signalBuffer = new StringBuffer(signal);
				
		// pad the signal with 0's
		int remainder = signal.length() % intervalSize;
		while ( remainder > 0 ) {
			remainder --;
			signalBuffer.append("0");
		}
		
		StringBuffer sb = new StringBuffer();
		int beginBlock = 0;
		int endBlock = beginBlock + intervalSize;
		
		while (beginBlock < signalBuffer.length()) {			
			sb.append(code.get(signalBuffer.substring(beginBlock, endBlock)));
			beginBlock = beginBlock + intervalSize;
			endBlock = beginBlock + intervalSize;
		}
		
		return sb.toString();
	}

}
