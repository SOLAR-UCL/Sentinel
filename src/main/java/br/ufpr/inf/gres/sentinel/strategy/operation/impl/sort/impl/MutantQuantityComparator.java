package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import com.google.common.primitives.Ints;

/**
 *
 * @author Giovani Guizzo
 */
public class MutantQuantityComparator extends AbstractSorterOperation<Operator> {

    public MutantQuantityComparator() {
        super(TerminalRuleType.MUTANT_QUANTITY, false);
    }

    @Override
    public int compare(Operator o1, Operator o2) {
        return Ints.compare(o1.getGeneratedMutants().size(), o2.getGeneratedMutants().size());
    }

}
