package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class MutantsOperatorComparator extends AbstractSorterOperation<Mutant> {

    /**
     *
     */
    public MutantsOperatorComparator() {
        super("Sort Mutants by " + TerminalRuleType.OPERATOR);
    }

    /**
     *
     * @return
     */
    @Override
    public Function<Mutant, String> createSortingFunction() {
        return mutant -> {
            return mutant.getOperator().getName();
        };
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
