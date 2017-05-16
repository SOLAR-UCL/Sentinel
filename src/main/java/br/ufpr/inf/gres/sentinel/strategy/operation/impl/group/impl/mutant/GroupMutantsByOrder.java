package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant.OrderComparator;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class GroupMutantsByOrder extends AbstractGroupingOperation<Mutant> {

    private OrderComparator delegate;

    /**
     *
     */
    public GroupMutantsByOrder() {
        super("Group Mutants by " + TerminalRuleType.ORDER);
        this.delegate = new OrderComparator();
    }

    /**
     *
     * @return
     */
    @Override
    public Function<Mutant, Integer> createGroupingFunction() {
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
