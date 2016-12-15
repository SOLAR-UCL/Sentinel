package br.ufpr.inf.gres.sentinel.strategy;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giovani Guizzo on 24/10/2016.
 */
public class Strategy {

	private Operation<Solution, List<Mutant>> firstOperation;

	public Strategy() {
	}

	public Strategy(Operation<Solution, List<Mutant>> firstOperation) {
		this.firstOperation = firstOperation;
	}

	public Operation<Solution, List<Mutant>> getFirstOperation() {
		return firstOperation;
	}

	public void setFirstOperation(Operation<Solution, List<Mutant>> firstOperation) {
		this.firstOperation = firstOperation;
	}

	public List<Mutant> run() {
		if (firstOperation != null) {
			return firstOperation.doOperation(new Solution());
		}
		return SetUniqueList.setUniqueList(new ArrayList<>());
	}

	@Override
	public String toString() {
		if (firstOperation == null) {
			return "Empty Strategy";
		}
		return firstOperation.toStringComplete();
	}

}
