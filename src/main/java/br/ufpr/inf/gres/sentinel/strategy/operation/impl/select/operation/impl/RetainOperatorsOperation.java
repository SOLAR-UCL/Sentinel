package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.AbstractSelectOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.Collection;

/**
 * @author Giovani Guizzo
 */
public class RetainOperatorsOperation extends AbstractSelectOperation<Operator> {

    /**
     *
     */
    public RetainOperatorsOperation() {
        super(TerminalRuleType.RETAIN_OPERATORS);
    }

    /**
     *
     * @param selection
     */
    public RetainOperatorsOperation(SelectionOperation<Operator> selection) {
        super(TerminalRuleType.RETAIN_OPERATORS, selection);
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public Collection<Operator> obtainList(Solution solution) {
        return solution.getOperators();
    }

}
