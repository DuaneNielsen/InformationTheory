package channel;

public class NoEncoder implements IEncoder {

	public String encode(String signal) {
		return signal;
	}

}
