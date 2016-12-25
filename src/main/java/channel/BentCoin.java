package channel;

public class BentCoin implements IGenerator {

	private double bias;

	public BentCoin(double bias) {
		this.bias = bias;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see channel.IGenerator#generate(int)
	 */
	public String generate() {
		if (Math.random() < bias) {
			return "0";
		} else {
			return "1";
		}
	}

}
