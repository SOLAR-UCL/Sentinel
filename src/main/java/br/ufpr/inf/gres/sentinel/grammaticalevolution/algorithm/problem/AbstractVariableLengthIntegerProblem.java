package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem;

/**
 * @author Giovani Guizzo
 */
public interface AbstractVariableLengthIntegerProblem extends AbstractVariableLengthProblem<Integer> {

	int getUpperVariableBound();

	int getLowerVariableBound();

}
