package br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.OperatorExecutionType;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class ConventionalExecution extends OperatorExecutionType {

    /**
     *
     */
    public ConventionalExecution() {
        super(TerminalRuleType.CONVENTIONAL + " Execution");
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public List<Mutant> doOperation(List<Operator> input) {
        return IntegrationFacade.getIntegrationFacade().executeOperators(input);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        return false;
    }

}
