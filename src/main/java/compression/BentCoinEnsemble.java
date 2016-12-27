package compression;

import java.util.Random;

import theory.Ensemble;
import theory.NotAProbabilityDistribution;

public class BentCoinEnsemble extends Ensemble<String> {

	public BentCoinEnsemble(double[] probabilityDistribution) throws NotAProbabilityDistribution {
		 super(new Random(), new String[]{"0","1"},probabilityDistribution);
	}	
	
}
