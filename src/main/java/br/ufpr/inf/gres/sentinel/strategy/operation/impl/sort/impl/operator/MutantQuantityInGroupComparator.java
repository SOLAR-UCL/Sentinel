package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;

import java.util.List;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class MutantQuantityInGroupComparator extends AbstractSorterOperation<List<Operator>> {

	public MutantQuantityInGroupComparator() {
		super("Sort Operator Groups by " + TerminalRuleType.MUTANT_QUANTITY_IN_GROUP);
	}

	@Override
	public boolean isSpecific() {
		return false;
	}

	@Override
	public Function<List<Operator>, Integer> createSortingFunction() {
		return operators -> operators.stream().mapToInt(operator -> operator.getGeneratedMutants().size()).sum();
	}
}
