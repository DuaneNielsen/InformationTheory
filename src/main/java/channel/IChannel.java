package channel;

public interface IChannel {

	String send(String signal);

	double getNoise();

	void setNoise(double noise);

}