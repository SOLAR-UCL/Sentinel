package br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.AbstractHOMGeneration;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class SingleHOMGeneration extends AbstractHOMGeneration {

	public SingleHOMGeneration() {
		super(TerminalRuleType.SINGLE_HOM + " Generation");
	}

	@Override
	public List<Mutant> doOperation(List<Mutant> input) {
		if (input.size() >= 2) {
			Mutant mutant = IntegrationFacade.getIntegrationFacade().combineMutants(input);
			for (Mutant tempMutant : input) {
				mutant.getConstituentMutants().add(tempMutant);
				mutant.getOperators().addAll(tempMutant.getOperators());
				for (Operator operator : tempMutant.getOperators()) {
					operator.getGeneratedMutants().add(mutant);
				}
			}
			return Lists.newArrayList(mutant);
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public boolean isSpecific() {
		return false;
	}
}
