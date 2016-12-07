package br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.OperatorExecutionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.impl.SelectionOperation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class ExecuteOperatorsOperation extends Operation<Solution, List<Operator>> {

    private final SelectionOperation<Operator> selection;
    private final OperatorExecutionType executionType;

    public ExecuteOperatorsOperation(SelectionOperation<Operator> selection, OperatorExecutionType executionType) {
        super(TerminalRuleType.DISCARD_OPERATORS);
        this.selection = selection;
        this.executionType = executionType;
    }

    @Override
    public List<Operator> doOperation(Solution solution) {
        List<Operator> selectedOperators = selection.doOperation(solution.getOperators());
        executionType.doOperation(selectedOperators);
        selectedOperators.forEach((operator) -> solution.getMutants().addAll(operator.getGeneratedMutants()));
        return next(solution);
    }

    @Override
    public boolean isSpecific() {
        boolean isSpecific = false;
        if (selection != null) {
            isSpecific = isSpecific || selection.isSpecific();
        }
        if (executionType != null) {
            isSpecific = isSpecific || executionType.isSpecific();
        }
        return isSpecific;
    }

}
