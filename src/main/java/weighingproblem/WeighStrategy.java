package weighingproblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import theory.Ensemble;
import theory.NotAProbabilityDistribution;

public class WeighStrategy {

	public WeighStrategy() {
	}

	public double entropyOf6v6Step() throws NotAProbabilityDistribution {
		double entropy = 0;
		WeighResult[] alphabet = { WeighResult.LEFT_LIGHT, WeighResult.EQUAL, WeighResult.RIGHT_LIGHT };
		double[] distribution = { (6.0 / 12.0), 0.0, (6.0 / 12.0) };
		Ensemble<WeighResult> ballsEnsemble;
		ballsEnsemble = new Ensemble<WeighResult>(new Random(), alphabet, distribution);
		entropy = ballsEnsemble.entropy();
		return entropy;
	}

	public double entropyOf5v5Step() throws NotAProbabilityDistribution {
		WeighResult[] alphabet = { WeighResult.LEFT_LIGHT, WeighResult.EQUAL, WeighResult.RIGHT_LIGHT };
		double[] distribution = { (5.0 / 12.0), (2.0 / 12.0), (5.0 / 12.0) };
		Ensemble<WeighResult> ballsEnsemble = new Ensemble<WeighResult>(new Random(), alphabet, distribution);
		double entropy = ballsEnsemble.entropy();
		return entropy;
	}

	public double entropyOf4v4Step() throws NotAProbabilityDistribution {
		WeighResult[] alphabet = { WeighResult.LEFT_LIGHT, WeighResult.EQUAL, WeighResult.RIGHT_LIGHT };
		double[] distribution = { (4.0 / 12.0), (4.0 / 12), (4.0 / 12.0) };
		Ensemble<WeighResult> ballsEnsemble = new Ensemble<WeighResult>(new Random(), alphabet, distribution);
		double entropy = ballsEnsemble.entropy();
		return entropy;
	}

	public double entropyOf3v3Step() throws NotAProbabilityDistribution {
		WeighResult[] alphabet = { WeighResult.LEFT_LIGHT, WeighResult.EQUAL, WeighResult.RIGHT_LIGHT };
		double[] distribution = { (3.0 / 12.0), (6.0 / 12.0), (3.0 / 12.0) };
		Ensemble<WeighResult> ballsEnsemble = new Ensemble<WeighResult>(new Random(), alphabet, distribution);
		double entropy = ballsEnsemble.entropy();
		return entropy;
	}

	/**
	 * Computes the Shannon Entropy of a given weighing
	 * 
	 * @param ballsWeighed
	 *            - an even number of balls placed on the scales, the TOTAL
	 *            number of balls weighed
	 * @param ballsWithUnknownProperties
	 *            - the number of balls left that we suspect of being the light
	 *            one
	 * @return the entropy
	 * @throws NotAProbabilityDistribution
	 *             if the probability distribution doesn't add up to 1.0
	 */
	public double entropyOfWeighing(int ballsWeighed, int ballsWithUnknownProperties)
			throws NotAProbabilityDistribution {

		
		WeighResult[] alphabet = { WeighResult.LEFT_LIGHT, WeighResult.EQUAL, WeighResult.RIGHT_LIGHT };		
		double prob_left_heavy = (double) ballsWeighed / 2.0 / (double) ballsWithUnknownProperties;
		double prob_right_heavy = (double) ballsWeighed / 2.0 / (double) ballsWithUnknownProperties;
		double prob_balanced = (double) (ballsWithUnknownProperties - (ballsWeighed))
				/ (double) ballsWithUnknownProperties;

		double[] distribution = { prob_left_heavy, prob_balanced, prob_right_heavy };
		Ensemble<WeighResult> ballsEnsemble = new Ensemble<WeighResult>(new Random(), alphabet, distribution);
		double entropy = ballsEnsemble.entropy();
		return entropy;
	}

	public int greedyEntropyStrategy(Scales scales) throws Exception {
		int iterations = 0;
		
		while (scales.table.size() > 1) {

			iterations++;
			// how many balls are suspect of being the light ball, initially all
			// of
			// them
			int ballsWithUnknownProperties = scales.table.size();
			int rightNumberOfBallsToWeigh = weighingWithBestValue(ballsWithUnknownProperties);

			// Now put the balls on the scale

			for (int b = 0; b < rightNumberOfBallsToWeigh; b += 2) {
				scales.leftscale.add(scales.table.remove(scales.table.size() - 1));
				scales.rightscale.add(scales.table.remove(scales.table.size() - 1));
			}

			// Now weigh it

			WeighResult result = scales.weigh();
			
			System.out.println(scales.leftscale);
			System.out.println(scales.rightscale);
			System.out.println(result.toString());

			switch (result) {
			case EQUAL:
				// then the light ball is still on the table, we eliminated all
				// the balls on the scales
				scales.leftscale.clear();
				scales.rightscale.clear();
				break;
			case LEFT_LIGHT:
				// then the light ball is among the left ones on the scale
				scales.rightscale.clear();
				scales.table.clear();
				// clear out the ones we know are not light, and move the
				// suspect ones back to the table
				scales.table.addAll(scales.leftscale);
				scales.leftscale.clear();
				break;
			case RIGHT_LIGHT:
				scales.leftscale.clear();
				scales.table.clear();
				scales.table.addAll(scales.rightscale);
				scales.rightscale.clear();
				break;
			default:
				break;
			}
		}
		
		System.out.println("found the light ball with " + iterations + " iterations");
		
		return scales.table.get(0);

	}

	private int weighingWithBestValue(int ballsWithUnknownProperties) throws NotAProbabilityDistribution {
		// how many balls should I weigh to gain the most information?  (it always have to be an even number)
		// so that's what the bitfiddling is doing
		int guessBallsToWeigh = ( ballsWithUnknownProperties & (Integer.MAX_VALUE - 1) ) ;
		double maxEntropy = 0.0;
		int rightNumberOfBallsToWeigh = 0;
		while (guessBallsToWeigh > 1) {
			double guessEntropy = entropyOfWeighing(guessBallsToWeigh, ballsWithUnknownProperties);
			if (guessEntropy > maxEntropy) {
				maxEntropy = guessEntropy;
				rightNumberOfBallsToWeigh = guessBallsToWeigh;
			}
			guessBallsToWeigh -= 2;
		}
		return rightNumberOfBallsToWeigh;
	}

}
