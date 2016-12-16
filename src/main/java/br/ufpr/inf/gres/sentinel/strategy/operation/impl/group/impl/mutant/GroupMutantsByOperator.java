package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant.MutantsOperatorComparator;

import java.util.function.Function;

public class GroupMutantsByOperator extends AbstractGroupingOperation<Mutant> {

	private MutantsOperatorComparator delegate;

	public GroupMutantsByOperator() {
		super("Group Mutants by " + TerminalRuleType.OPERATOR);
		delegate = new MutantsOperatorComparator();
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
