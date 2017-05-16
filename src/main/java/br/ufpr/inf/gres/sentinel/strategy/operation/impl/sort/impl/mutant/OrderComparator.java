package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class OrderComparator extends AbstractSorterOperation<Mutant> {

    /**
     *
     */
    public OrderComparator() {
        super("Sort Mutants by " + TerminalRuleType.ORDER);
    }

    /**
     *
     * @return
     */
    @Override
    public Function<Mutant, Integer> createSortingFunction() {
        return mutant -> mutant.getConstituentMutants().size() <= 1 ? 1 : mutant.getConstituentMutants().size();
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
