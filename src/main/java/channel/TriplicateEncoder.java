package channel;

public class TriplicateEncoder implements IEncoder {

	public String encode (String signal) {
		StringBuilder s = new StringBuilder();
		for ( int i = 0; i < signal.length(); i++ ) {
			s.append(signal.charAt(i)).append(signal.charAt(i)).append(signal.charAt(i));
		}
		return s.toString();
	}
	
}
