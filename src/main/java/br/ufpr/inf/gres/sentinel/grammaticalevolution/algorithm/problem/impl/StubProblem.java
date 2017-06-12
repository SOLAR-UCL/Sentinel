package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.VariableLengthIntegerProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;

/**
 *
 * @author Giovani Guizzo
 */
public class StubProblem implements VariableLengthIntegerProblem {

    @Override
    public VariableLengthSolution<Integer> createSolution() {
        return new DefaultVariableLengthIntegerSolution(this);
    }

    @Override
    public void evaluate(VariableLengthSolution<Integer> solution) {
    }

    @Override
    public int getLowerVariableBound() {
        return 0;
    }

    @Override
    public int getMaxLength() {
        return 0;
    }

    @Override
    public int getMinLength() {
        return 0;
    }

    @Override
    public String getName() {
        return "Stub Problem";
    }

    @Override
    public int getNumberOfConstraints() {
        return 0;
    }

    @Override
    public int getNumberOfObjectives() {
        return 2;
    }

    @Override
    public int getNumberOfVariables() {
        return 0;
    }

    @Override
    public int getUpperVariableBound() {
        return 0;
    }

}
