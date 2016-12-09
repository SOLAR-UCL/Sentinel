package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingFunction;
import java.util.function.Function;

/**
 *
 * @author Giovani Guizzo
 */
public class GroupOperatorsByType extends AbstractGroupingFunction<Operator> {

    public GroupOperatorsByType() {
        super("Group Operators by " + TerminalRuleType.TYPE);
    }

    @Override
    protected Function<Operator, String> createGrouperFunction() {
        return Operator::getType;
    }

    @Override
    public boolean isSpecific() {
        return false;
    }

}
