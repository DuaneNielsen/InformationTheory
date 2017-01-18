package theory;

import org.nd4j.linalg.api.ndarray.INDArray;

public interface IJointEnsemble<ROW extends Comparable<ROW>, COLUMN extends Comparable<COLUMN>> {

	IEnsemble<ROW> marginalRow();

	IEnsemble<COLUMN> marginalColumn();

	Symbol<ROW> getSymbolRow(ROW row) throws SymbolNotFound;

	Symbol<COLUMN> getSymbolColumn(COLUMN column) throws SymbolNotFound;

	double getProbability(ROW row, COLUMN column);

	double entropy();

	INDArray shannonInformation();
	
	public IEnsemble<COLUMN> conditionalOnRow(ROW symbol);
	
	public IEnsemble<ROW> conditionalOnColumn(COLUMN symbol);
	
	public int rowLength();
	public int columnLength();
	
}