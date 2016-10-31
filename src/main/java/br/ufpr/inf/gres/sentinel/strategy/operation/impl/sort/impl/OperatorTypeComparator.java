package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;

/**
 *
 * @author Giovani Guizzo
 */
public class OperatorTypeComparator extends AbstractSorterOperation<Operator> {

    public OperatorTypeComparator() {
        super(TerminalRuleType.TYPE, false);
    }

    @Override
    public int compare(Operator o1, Operator o2) {
        return o1.getType().compareToIgnoreCase(o2.getType());
    }

}
