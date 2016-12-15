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

	public ConventionalExecution() {
		super(TerminalRuleType.CONVENTIONAL_EXECUTION);
	}

	@Override
	public List<Operator> doOperation(List<Operator> input) {
		for (Operator operator : input) {
			List<Mutant> result = IntegrationFacade.getFacade()
												   .executeOperator(operator, IntegrationFacade.getProgramUnderTest());
			operator.getGeneratedMutants().addAll(result);
		}
		return input;
	}

	@Override
	public boolean isSpecific() {
		return false;
	}

}
