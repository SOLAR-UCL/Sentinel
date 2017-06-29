package br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.OperatorExecutionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Giovani Guizzo
 */
public class ExecuteOperatorsOperation extends Operation<Solution, Collection<Mutant>> {

    private OperatorExecutionType executionType;
    private SelectionOperation<Operator> selection;

    /**
     *
     */
    public ExecuteOperatorsOperation() {
        super(TerminalRuleType.EXECUTE_OPERATORS);
    }

    /**
     *
     * @param selection
     * @param executionType
     */
    public ExecuteOperatorsOperation(SelectionOperation<Operator> selection, OperatorExecutionType executionType) {
        super(TerminalRuleType.EXECUTE_OPERATORS);
        this.selection = selection;
        this.executionType = executionType;
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public Collection<Mutant> doOperation(Solution solution, Program program) {
        checkNotNull(this.selection, "No selection operation!");
        checkNotNull(this.executionType, "No execution operation!");
        Collection<Operator> selectedOperators = this.selection.doOperation(solution.getOperators(), null);
        Collection<Mutant> generatedMutants = this.executionType.doOperation(selectedOperators, program);
        solution.getMutants().addAll(generatedMutants);
        return this.next(solution, program);
    }

    /**
     *
     * @return
     */
    public OperatorExecutionType getExecutionType() {
        return this.executionType;
    }

    /**
     *
     * @param executionType
     */
    public void setExecutionType(OperatorExecutionType executionType) {
        this.executionType = executionType;
    }

    /**
     *
     * @return
     */
    public SelectionOperation<Operator> getSelection() {
        return this.selection;
    }

    /**
     *
     * @param selection
     */
    public void setSelection(SelectionOperation<Operator> selection) {
        this.selection = selection;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        boolean isSpecific = false;
        if (this.selection != null) {
            isSpecific = this.selection.isSpecific();
        }
        if (this.executionType != null) {
            isSpecific = isSpecific || this.executionType.isSpecific();
        }
        return isSpecific;
    }

}
