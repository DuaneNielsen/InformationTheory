package theory;

import org.apache.commons.math3.util.CombinatoricsUtils;

public class RepetitionCodeModel {

	// flipped is the flip probability during transmission on a Binary channel

	public double probabilityOfErrorOnTriplicate(double flipped) {

		double not_flipped = (double) 1.0 - flipped;
		double probabilityOfError = 0;
		probabilityOfError += (Math.pow(flipped, 3)); // all 3 flipped		
		
		long binomicalCoeff = CombinatoricsUtils.binomialCoefficient(3, 2);
		double probFlipped = (Math.pow(flipped, 2));
		
		probabilityOfError +=  (double)binomicalCoeff *  not_flipped  * probFlipped ;

		return probabilityOfError;
	}

	public double probabilityOfErrorOnQuadlicate(double flipped) {
		double not_flipped = (double) 1.0 - flipped;
		double probabilityOfError = 0;
		probabilityOfError += (Math.pow(flipped, 4)); // all 4 flipped
		probabilityOfError += CombinatoricsUtils.binomialCoefficient(4, 3) * (Math.pow(not_flipped, 1))
				* (Math.pow(flipped, 3)); // 3 flipped
		probabilityOfError += CombinatoricsUtils.binomialCoefficient(4, 2) * (Math.pow(not_flipped, 2))
				* (Math.pow(flipped, 2)); // 2 flipped
		return probabilityOfError;
	}

	// computes the theoretical probability of error for a repition code of n
	// repititions, for a given probability of flipping a bit during
	// transmission
	public double probabilityOfError(double flipped, int repititions) {
		double not_flipped = (double) 1.0 - flipped;
		double probabilityOfError = 0;
		int number_flips = repititions;
		
		// if half or more of the repititions are flipped, then we will have an error
		int number_flips_required_to_get_error = (int) Math.ceil((double)repititions / 2);

		probabilityOfError += (Math.pow(flipped, repititions));
		number_flips--;
		
		while (number_flips >= number_flips_required_to_get_error) {
			int number_of_bits_flipped = number_flips;
			int number_of_bits_not_flipped = repititions - number_flips;

			// Combinations are used because there are multiple ways a bit can
			// be flipped in a set, eg: 001, 010, 100 = 3 ways to flip
			
			long binomicalCoeff = CombinatoricsUtils.binomialCoefficient(repititions, number_of_bits_flipped);
			double probNotFlipped = (Math.pow(not_flipped, number_of_bits_not_flipped));
			double probFlipped = (Math.pow(flipped, number_of_bits_flipped));
			
			probabilityOfError += binomicalCoeff * probNotFlipped * probFlipped;
			number_flips--;
		}

		return probabilityOfError;

	}

}
