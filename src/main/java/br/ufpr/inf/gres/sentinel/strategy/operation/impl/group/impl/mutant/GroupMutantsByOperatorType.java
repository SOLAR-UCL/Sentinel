package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant.MutantsOperatorTypeComparator;

import java.util.function.Function;

public class GroupMutantsByOperatorType extends AbstractGroupingOperation<Mutant> {

	private MutantsOperatorTypeComparator delegate;

	public GroupMutantsByOperatorType() {
		super("Group Mutants by " + TerminalRuleType.OPERATOR_TYPE);
		delegate = new MutantsOperatorTypeComparator();
	}

	@Override
	public Function<Mutant, String> createGroupingFunction() {
		return delegate.createSortingFunction();
	}

	@Override
	public boolean isSpecific() {
		return delegate.isSpecific();
	}

}
