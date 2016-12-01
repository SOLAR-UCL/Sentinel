package br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.OperatorExecutionType;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class ConventionalExecution extends OperatorExecutionType {

    public ConventionalExecution() {
        super(TerminalRuleType.CONVENTIONAL_EXECUTION, false);
    }

    @Override
    public List<Operator> doOperation(List<Operator> input) {
        input.forEach((operator) -> operator.execute());
        return input;
    }

}
