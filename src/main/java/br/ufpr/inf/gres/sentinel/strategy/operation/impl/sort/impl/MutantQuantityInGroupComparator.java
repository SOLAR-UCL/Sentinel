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
public class MutantQuantityInGroupComparator extends AbstractSorterOperation<List<Operator>> {

    public MutantQuantityInGroupComparator() {
        super(TerminalRuleType.MUTANT_QUANTITY, false);
    }

    @Override
    public int compare(List<Operator> o1, List<Operator> o2) {
        int quantityO1 = o1.stream().mapToInt(operator -> operator.getGeneratedMutants().size()).sum();
        int quantityO2 = o2.stream().mapToInt(operator -> operator.getGeneratedMutants().size()).sum();
        return Ints.compare(quantityO1, quantityO2);
    }

}
