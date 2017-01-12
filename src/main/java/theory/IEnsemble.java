package theory;

import java.util.List;

public interface IEnsemble<T extends Comparable<T>> {

	/**
	 * The probability of occurrence of a symbol in the ensemble
	 * @param symbol type
	 * @return the probability of the occurrence of symbol
	 */
	double probabilityOfOccurence(T symbol);

	/**
	 * The probability of occurrence of a symbol in the ensemble
	 * @param the actual symbol (just a bit of sugar)
	 * @return the probability of the occurrence of symbol
	 */
	double probabilityOfOccurence(Symbol<T> symbol);

	/**
	 * The probability of occurrence of a symbol in the ensemble
	 * @param symbol
	 * @return the probability of the occurrence of symbol
	 */
	double informationOfOccurence(Symbol<T> symbol);

	/**
	 *  returns the Shannon information content in bits, of an isolated occurrence of a member of the alphabet
	 * @param symbol
	 * @return Shannon information content for symbol in bits
	 */
	double informationOfOccurence(T symbol);

	/**
	 * Shannon entropy is the average information content for the Ensemble
	 * @return the Shannon entropy of the Ensemble in bits
	 */
	double entropy();

	List<Symbol<T>> getAlphabet();

	int alphabetLength();

	Symbol<T> getSymbol(T key);

}