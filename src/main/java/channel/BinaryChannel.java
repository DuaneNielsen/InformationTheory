package channel;

public class BinaryChannel implements IChannel {

	public double noise = 0.0;

	public BinaryChannel() {};
	
	public BinaryChannel(double noise) {
		this.noise = noise;
	}

	/* (non-Javadoc)
	 * @see channel.IChannel#send(java.lang.String)
	 */
	public String send(String signal) {
		StringBuilder transmitted = new StringBuilder(signal);
		for (int i = 0; i < signal.length(); i++) {
			if (Math.random() < noise) {
				char c = signal.charAt(i);
				if (c == '0') {
					c = '1';
				}
				if (c == '1') {
					c = '0';
				}
				transmitted.setCharAt(i, c);
			}
		}
		return transmitted.toString();
	}

	/* (non-Javadoc)
	 * @see channel.IChannel#getNoise()
	 */
	public double getNoise() {
		return noise;
	}

	/* (non-Javadoc)
	 * @see channel.IChannel#setNoise(double)
	 */
	public void setNoise(double noise) {
		this.noise = noise;
	}

	
	
}
