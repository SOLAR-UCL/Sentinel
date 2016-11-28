package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.SelectionOperation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class DiscardOperatorsOperation extends Operation<Solution, List<Operator>> {

    private final SelectionOperation<Operator> selection;

    public DiscardOperatorsOperation(SelectionOperation<Operator> selection) {
        super(TerminalRuleType.DISCARD_OPERATORS, false);
        this.selection = selection;
    }

    @Override
    public List<Operator> doOperation(Solution solution) {
        solution.getOperators().removeAll(selection.doOperation(solution.getOperators()));
        return next(solution);
    }

}
