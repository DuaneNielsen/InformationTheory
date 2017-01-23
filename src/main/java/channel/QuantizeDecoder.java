package channel;

import java.util.*;

public class QuantizeDecoder implements IDecoder {

	protected Map<String,String> code = new HashMap<String,String>();
	protected int indexSize;
	
	public QuantizeDecoder(Map<String, String> code, int indexSize) {
		super();
		this.code = code;
		this.indexSize = indexSize;
	}

	public String decode(String recievedSignal) {
		StringBuffer sb = new StringBuffer();
		for (int index = 0; index * indexSize < recievedSignal.length(); index++ ) {
			String key = recievedSignal.substring(index, index + indexSize);
			sb.append(code.get(key));
		}
		return sb.toString();
	}

}
