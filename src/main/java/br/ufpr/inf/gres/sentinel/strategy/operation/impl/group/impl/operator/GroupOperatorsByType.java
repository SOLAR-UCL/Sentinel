package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.OperatorTypeComparator;

import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class GroupOperatorsByType extends AbstractGroupingOperation<Operator> {

	private OperatorTypeComparator delegate;

	public GroupOperatorsByType() {
		super("Group Operators by " + TerminalRuleType.TYPE);
		delegate = new OperatorTypeComparator();
	}

	@Override
	public Function<Operator, String> createGroupingFunction() {
		return delegate.createSortingFunction();
	}

	@Override
	public boolean isSpecific() {
		return delegate.isSpecific();
	}

}
