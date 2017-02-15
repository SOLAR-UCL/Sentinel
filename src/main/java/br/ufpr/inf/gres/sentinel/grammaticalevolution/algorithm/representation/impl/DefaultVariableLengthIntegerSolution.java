package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.AbstractVariableLengthIntegerProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;

/**
 * @author Giovani Guizzo
 */
public class DefaultVariableLengthIntegerSolution extends VariableLengthSolution<Integer> {

	/**
	 * Constructor
	 *
	 * @param problem
	 */
	public DefaultVariableLengthIntegerSolution(AbstractVariableLengthIntegerProblem problem) {
		super(problem);

		int numberOfVariables = randomGenerator.nextInt(problem.getMinLength(), problem.getMaxLength());
		for (int i = 0; i < numberOfVariables; i++) {
			addVariable(randomGenerator.nextInt(problem.getLowerVariableBound(), problem.getUpperVariableBound()));
		}
	}

	public DefaultVariableLengthIntegerSolution(DefaultVariableLengthIntegerSolution solution) {
		super(solution);
	}

	@Override
	public DefaultVariableLengthIntegerSolution copy() {
		return new DefaultVariableLengthIntegerSolution(this);
	}
}
