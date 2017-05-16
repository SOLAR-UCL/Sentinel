package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.MutantQuantityComparator;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class GroupOperatorsByMutantQuantity extends AbstractGroupingOperation<Operator> {

    private MutantQuantityComparator delegate;

    /**
     *
     */
    public GroupOperatorsByMutantQuantity() {
        super("Group Operators by " + TerminalRuleType.MUTANT_QUANTITY);
        this.delegate = new MutantQuantityComparator();
    }

    /**
     *
     * @return
     */
    @Override
    public Function<Operator, Integer> createGroupingFunction() {
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
