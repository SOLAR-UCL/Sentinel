package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;

import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class GroupOperatorsByMutantQuantity extends AbstractGroupingOperation<Operator> {

	public GroupOperatorsByMutantQuantity() {
		super("Group Operators by " + TerminalRuleType.MUTANT_QUANTITY);
	}

	@Override
	protected Function<Operator, Integer> createGroupingFunction() {
		return (Operator operator) -> operator.getGeneratedMutants().size();
	}

	@Override
	public boolean isSpecific() {
		return false;
	}

}
