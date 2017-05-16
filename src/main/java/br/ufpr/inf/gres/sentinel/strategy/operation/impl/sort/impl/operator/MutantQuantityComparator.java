package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class MutantQuantityComparator extends AbstractSorterOperation<Operator> {

    /**
     *
     */
    public MutantQuantityComparator() {
        super("Sort Operators by " + TerminalRuleType.MUTANT_QUANTITY);
    }

    /**
     *
     * @return
     */
    @Override
    public Function<Operator, Integer> createSortingFunction() {
        return operator -> operator.getGeneratedMutants().size();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        return false;
    }
}
