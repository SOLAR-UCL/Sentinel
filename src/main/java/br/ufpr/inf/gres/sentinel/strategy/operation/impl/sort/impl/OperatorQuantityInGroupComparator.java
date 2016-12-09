package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class OperatorQuantityInGroupComparator extends AbstractSorterOperation<List<Operator>> {

    public OperatorQuantityInGroupComparator() {
        super("Sort Operator Groups by " + TerminalRuleType.MUTANT_QUANTITY);
    }

    @Override
    public int compare(List<Operator> o1, List<Operator> o2) {
        return Integer.compare(o1.size(), o2.size());
    }

    @Override
    public boolean isSpecific() {
        return false;
    }
}
