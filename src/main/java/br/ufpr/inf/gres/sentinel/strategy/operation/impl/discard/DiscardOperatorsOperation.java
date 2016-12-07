package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.impl.SelectionOperation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class DiscardOperatorsOperation extends Operation<Solution, List<Operator>> {

    private SelectionOperation<Operator> selection;

    public DiscardOperatorsOperation(SelectionOperation<Operator> selection) {
        super(TerminalRuleType.DISCARD_OPERATORS);
        this.selection = selection;
    }

    public DiscardOperatorsOperation() {
        super(TerminalRuleType.DISCARD_OPERATORS);
    }

    public SelectionOperation<Operator> getSelection() {
        return selection;
    }

    public void setSelection(SelectionOperation<Operator> selection) {
        this.selection = selection;
    }

    @Override
    public boolean isSpecific() {
        return selection != null ? selection.isSpecific() : false;
    }

    @Override
    public List<Operator> doOperation(Solution solution) {
        solution.getOperators().removeAll(selection.doOperation(solution.getOperators()));
        return next(solution);
    }

}
