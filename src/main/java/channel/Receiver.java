package channel;

public class Receiver implements IReceiver {

	private String receivedSignal;
	
	/* (non-Javadoc)
	 * @see channel.Ireciever#recieved(java.lang.String)
	 */
	public String recieved(String signal){
		this.receivedSignal = signal;
		return signal;
	}

	public String getRecievedSignal() {
		// TODO Auto-generated method stub
		return receivedSignal;
	}
	
}
