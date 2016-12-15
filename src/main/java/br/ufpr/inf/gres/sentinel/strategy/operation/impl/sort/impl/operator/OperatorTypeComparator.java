package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;

import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class OperatorTypeComparator extends AbstractSorterOperation<Operator> {

	public OperatorTypeComparator() {
		super("Sort Operators by " + TerminalRuleType.TYPE);
	}

	@Override
	public boolean isSpecific() {
		return false;
	}

	@Override
	protected Function<Operator, String> createSortingFunction() {
		return Operator::getType;
	}
}
