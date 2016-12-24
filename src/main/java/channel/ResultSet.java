package channel;

import java.text.*;
import java.util.*;

public class ResultSet {

	public double errorRate;
	public double rate;
	
	public List<Result> results = new ArrayList<Result>();
	
	public void add(Result result) {
		results.add(result);
	}
	
	public void computeStats() {
		int totalErrors = 0;
		int totalLength = 0;
		double totalRate = 0;
		for (Result result: results) {
			totalErrors += result.errors;
			totalLength += result.length;
			totalRate += result.rate();
		}
		this.errorRate = (double)totalErrors/(double)totalLength;
		this.rate = totalRate/(double)results.size();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newline = System.getProperty("line.separator");
		for (Result result: results) {
			sb.append(result.toString() + newline);
		}
		this.computeStats();
		NumberFormat formatter = new DecimalFormat("#0.000000");
		sb.append("ErrorRate: " + formatter.format(errorRate) + " Rate: " + formatter.format(rate)  +  newline);
		
		return sb.toString();
	}
	
}
