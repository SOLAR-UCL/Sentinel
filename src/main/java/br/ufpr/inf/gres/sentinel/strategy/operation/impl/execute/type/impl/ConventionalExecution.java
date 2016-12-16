package br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.OperatorExecutionType;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class ConventionalExecution extends OperatorExecutionType {

	public ConventionalExecution() {
		super(TerminalRuleType.CONVENTIONAL + " Execution");
	}

	@Override
	public List<Mutant> doOperation(List<Operator> input) {
		List<Mutant> result = SetUniqueList.setUniqueList(new ArrayList<>());
		for (Operator operator : input) {
			List<Mutant> generatedMutants = IntegrationFacade.getIntegrationFacade()
															 .executeOperator(operator, IntegrationFacade.getProgramUnderTest());
			operator.getGeneratedMutants().addAll(generatedMutants);
			result.addAll(generatedMutants);
		}
		return result;
	}

	@Override
	public boolean isSpecific() {
		return false;
	}

}
