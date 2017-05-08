package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem;

/**
 * @author Giovani Guizzo
 */
public interface VariableLengthIntegerProblem extends VariableLengthProblem<Integer> {

    int getUpperVariableBound();

    int getLowerVariableBound();

}
