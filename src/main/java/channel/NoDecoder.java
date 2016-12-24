package channel;

public class NoDecoder implements IDecoder {

	public String decode(String recievedSignal) {
		return recievedSignal;
	}

}
