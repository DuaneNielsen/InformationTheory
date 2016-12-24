package channel;

public class BentCoin implements IGenerator {
	
	private double bias;


	public BentCoin(double bias) {
		this.bias = bias;
	}
	
	
	/* (non-Javadoc)
	 * @see channel.IGenerator#generate(int)
	 */
	public String generate(int length) {
		StringBuilder signal = new StringBuilder();
		for (int i = 0; i < length; i++ ) {
			if (Math.random() < bias ) {
				signal.append('0');
			} else {
				signal.append('1');
			}
		}
		return signal.toString();
	}
}
