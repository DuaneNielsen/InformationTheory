package theory;

import org.nd4j.linalg.api.ndarray.INDArray;

public interface IJointEnsemble<ROW extends Comparable<ROW>, COLUMN extends Comparable<COLUMN>> {

	IEnsemble<ROW> marginalRow();

	IEnsemble<COLUMN> marginalColumn();

	Symbol<ROW> getSymbolX(ROW row) throws SymbolNotFound;

	Symbol<COLUMN> getSymbolY(COLUMN column) throws SymbolNotFound;

	double getProbability(ROW row, COLUMN column);

	double entropy();

	INDArray shannonInformation();

}