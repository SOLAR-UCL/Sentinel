package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class SelectOperatorsOperation extends Operation<Solution, List<Mutant>> {

    private SelectionOperation<Operator> selection;

    public SelectOperatorsOperation(SelectionOperation<Operator> selection) {
        super(TerminalRuleType.SELECT_OPERATORS, selection.isSpecific());
        this.selection = selection;
    }

    public SelectOperatorsOperation() {
        super(TerminalRuleType.SELECT_OPERATORS, false);
    }

    public SelectionOperation<Operator> getSelection() {
        return selection;
    }

    public void setSelection(SelectionOperation<Operator> selection) {
        this.selection = selection;
    }

    @Override
    public boolean isSpecific() {
        return selection != null ? selection.isSpecific() : specific;
    }

    @Override
    public List<Mutant> doOperation(Solution solution) {
        solution.getOperators().retainAll(selection.doOperation(solution.getOperators()));
        return next(solution);
    }

}
