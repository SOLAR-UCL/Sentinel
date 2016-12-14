package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class MutantQuantityInGroupComparator extends AbstractSorterOperation<List<Operator>> {

    public MutantQuantityInGroupComparator() {
        super("Sort Operator Groups by " + TerminalRuleType.MUTANT_QUANTITY_IN_GROUP);
    }

    @Override
    public int compare(List<Operator> o1, List<Operator> o2) {
        int quantityO1 = o1.stream().mapToInt(operator -> operator.getGeneratedMutants().size()).sum();
        int quantityO2 = o2.stream().mapToInt(operator -> operator.getGeneratedMutants().size()).sum();
        return Integer.compare(quantityO1, quantityO2);
    }

    @Override
    public boolean isSpecific() {
        return false;
    }

}
