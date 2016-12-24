package channel;

public class Driver {

	public IGenerator generator = new BentCoin(0.1);
	public IChannel channel = new BinaryChannel();
	public Receiver receiver = new Receiver();

	public IEncoder encoder = new NoEncoder();
	public IDecoder decoder = new NoDecoder();
	
	
	public Driver () {
	}
	
	public ResultSet run(int runs, int length) {
		
		ResultSet results = new ResultSet();
		
		for (int i = 0; i < runs; i++) {
			String signal = generator.generate(length);
			String encoded = encoder.encode(signal);
			String recieved = channel.send(encoded);
			String decoded = decoder.decode(recieved);
			results.add(new Result(signal, encoded, recieved, decoded));
		}		
		return results;
	}
	
}
