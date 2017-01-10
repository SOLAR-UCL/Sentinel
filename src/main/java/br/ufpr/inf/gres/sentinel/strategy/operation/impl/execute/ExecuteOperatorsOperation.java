package br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.OperatorExecutionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Giovani Guizzo
 */
public class ExecuteOperatorsOperation extends Operation<Solution, List<Mutant>> {

	private SelectionOperation<Operator> selection;
	private OperatorExecutionType executionType;

	public ExecuteOperatorsOperation() {
		super(TerminalRuleType.EXECUTE_OPERATORS);
	}

	public ExecuteOperatorsOperation(SelectionOperation<Operator> selection, OperatorExecutionType executionType) {
		super(TerminalRuleType.EXECUTE_OPERATORS);
		this.selection = selection;
		this.executionType = executionType;
	}

	public SelectionOperation<Operator> getSelection() {
		return selection;
	}

	public void setSelection(SelectionOperation<Operator> selection) {
		this.selection = selection;
	}

	public OperatorExecutionType getExecutionType() {
		return executionType;
	}

	public void setExecutionType(OperatorExecutionType executionType) {
		this.executionType = executionType;
	}

	@Override
	public List<Mutant> doOperation(Solution solution) {
		checkNotNull(selection, "No selection operation!");
		checkNotNull(executionType, "No execution operation!");
		List<Operator> selectedOperators = selection.doOperation(solution.getOperators());
		List<Mutant> generatedMutants = executionType.doOperation(selectedOperators);
		solution.getMutants().addAll(generatedMutants);
		return next(solution);
	}

	@Override
	public boolean isSpecific() {
		boolean isSpecific = false;
		if (selection != null) {
			isSpecific = selection.isSpecific();
		}
		if (executionType != null) {
			isSpecific = isSpecific || executionType.isSpecific();
		}
		return isSpecific;
	}

}
