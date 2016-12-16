package br.ufpr.inf.gres.sentinel.integration.mujava;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;

import java.util.Collections;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class MuJavaFacade extends IntegrationFacade {

	@Override
	public List<Operator> getAllOperators() {
		//TODO implement it
		return Collections.emptyList();
	}

	@Override
	public List<Mutant> executeOperator(Operator operator, Program programToBeMutated) {
		//TODO implement it
		return Collections.emptyList();
	}

	@Override
	public Mutant combineMutants(List<Mutant> mutantsToCombine) {
		//TODO implement it
		return null;
	}

}
