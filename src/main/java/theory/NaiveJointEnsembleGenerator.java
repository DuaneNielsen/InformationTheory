package theory;

import java.util.*;

public class NaiveJointEnsembleGenerator<ROW extends Comparable<ROW>, COLUMN extends Comparable<COLUMN>> {

	private Random randomColumn;
	private IJointEnsemble<ROW,COLUMN> jointEnsemble;
	private NaiveEnsembleGenerator<ROW> rowGenerator; 
	
	public NaiveJointEnsembleGenerator(Random randomRow, Random randomColumn,
			IJointEnsemble<ROW, COLUMN> jointEnsemble) {
		super();
		this.randomColumn = randomColumn;
		this.jointEnsemble = jointEnsemble;
		this.rowGenerator = new NaiveEnsembleGenerator<ROW>(randomRow, jointEnsemble.marginalRow());
	}
	
	public SymbolPair<ROW,COLUMN> generateRandom() {
		Symbol<ROW> row = this.rowGenerator.generateRandom();
		IEnsemble<COLUMN> marginalColumn = jointEnsemble.conditionalOnRow(row.getSymbol());
		NaiveEnsembleGenerator<COLUMN> genC = new NaiveEnsembleGenerator<COLUMN>(randomColumn, marginalColumn);
		return new SymbolPair<ROW,COLUMN>(row,genC.generateRandom());
	}
	
}
