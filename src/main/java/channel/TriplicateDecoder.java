package channel;

public class TriplicateDecoder implements IDecoder {

	public TriplicateDecoder() {};
	
	private int pos = 0;
	char[] triple = new char[3];
	
	public String decode(String encoded) {
				
		StringBuilder decoded = new StringBuilder();
		
		for (int i = 0; i < encoded.length(); i++ ) {
			char value = infer(encoded.charAt(i));
			if (value != 'N') {
				decoded.append(value);
			}
		}
		return decoded.toString();
	}

	private char infer(char c) {
		
		char inferred_value = 'N';
		
		if (pos < 2) {
			triple[pos] = c;
			pos++;
		} else {
			triple[pos] = c;
			inferred_value = vote(triple[0], triple[1], triple[2]);
			pos = 0;
		}
		
		return inferred_value;
	}

	private char vote(char one, char two, char three) {
		int vote1 = Character.getNumericValue(one);
		int vote2 = Character.getNumericValue(two);
		int vote3 = Character.getNumericValue(three);	
		int total = vote1 + vote2 + vote3;
		if ( total >= 2 ) { return '1'; }
		return '0';
	}
	
}
