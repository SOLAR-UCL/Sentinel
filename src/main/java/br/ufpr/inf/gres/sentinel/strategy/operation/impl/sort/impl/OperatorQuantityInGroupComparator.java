package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import com.google.common.primitives.Ints;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class OperatorQuantityInGroupComparator extends AbstractSorterOperation<List<Operator>> {

    public OperatorQuantityInGroupComparator() {
        super(TerminalRuleType.MUTANT_QUANTITY, false);
    }

    @Override
    public int compare(List<Operator> o1, List<Operator> o2) {
        return Ints.compare(o1.size(), o2.size());
    }

}
