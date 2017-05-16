package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.VariableLengthIntegerProblem;
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
    public DefaultVariableLengthIntegerSolution(VariableLengthIntegerProblem problem) {
        super(problem);

        int numberOfVariables = this.randomGenerator.nextInt(problem.getMinLength(), problem.getMaxLength());
        for (int i = 0; i < numberOfVariables; i++) {
            this.addVariable(this.randomGenerator.nextInt(problem.getLowerVariableBound(), problem.getUpperVariableBound()));
        }
    }

    /**
     *
     * @param solution
     */
    public DefaultVariableLengthIntegerSolution(DefaultVariableLengthIntegerSolution solution) {
        super(solution);
    }

    /**
     *
     * @return
     */
    @Override
    public DefaultVariableLengthIntegerSolution copy() {
        return new DefaultVariableLengthIntegerSolution(this);
    }
}
