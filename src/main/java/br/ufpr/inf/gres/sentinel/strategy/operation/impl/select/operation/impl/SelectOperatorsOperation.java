package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.AbstractSelectOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class SelectOperatorsOperation extends AbstractSelectOperation<Operator> {

    public SelectOperatorsOperation() {
        super(TerminalRuleType.SELECT_OPERATORS);
    }

    public SelectOperatorsOperation(SelectionOperation<Operator> selection) {
        super(TerminalRuleType.SELECT_OPERATORS, selection);
    }

    @Override
    public List<Operator> obtainList(Solution solution) {
        return solution.getOperators();
    }

}
