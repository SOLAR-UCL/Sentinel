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

    /**
     *
     */
    public GroupOperatorsByType() {
        super("Group Operators by " + TerminalRuleType.TYPE);
        this.delegate = new OperatorTypeComparator();
    }

    /**
     *
     * @return
     */
    @Override
    public Function<Operator, String> createGroupingFunction() {
        return this.delegate.createSortingFunction();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        return this.delegate.isSpecific();
    }

}
