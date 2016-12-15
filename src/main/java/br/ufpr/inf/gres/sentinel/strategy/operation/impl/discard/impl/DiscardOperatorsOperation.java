package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.AbstractDiscardOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;

import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class DiscardOperatorsOperation extends AbstractDiscardOperation<Operator> {

	public DiscardOperatorsOperation() {
		super(TerminalRuleType.DISCARD_OPERATORS);
	}

	public DiscardOperatorsOperation(SelectionOperation<Operator> selection) {
		super(TerminalRuleType.DISCARD_OPERATORS, selection);
	}

	@Override
	protected List<Operator> obtainList(Solution solution) {
		return solution.getOperators();
	}

}
