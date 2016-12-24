package channel;

public class Result {
	public String signal;
	public String encoded;
	public String recieved;
	public String decoded;
	public int errors;
	public int length;

	public Result(String signal, String encoded, String recieved, String decoded) {
		this.signal = signal;
		this.encoded = encoded;
		this.recieved = recieved;
		this.decoded = decoded;
		this.errors = errors();
		this.length = signal.length();
	}
	

	public double rate() {
		return (double)signal.length() / (double)encoded.length();
	}
	
	private int errors() {
		int errors = 0;
		for (int i = 0; i < signal.length(); i++) {
			if (signal.charAt(i) != decoded.charAt(i)) {errors++;}
		}
		return errors;
	}
	
	public String toString() {
		String newline = System.getProperty("line.separator");
		return new String(
				"signal:   " + signal + newline +
				"encoded:  " + encoded + newline +
				"recieved: " + recieved + newline +
				"decoded:  " + decoded	);
		
	}
	
}
