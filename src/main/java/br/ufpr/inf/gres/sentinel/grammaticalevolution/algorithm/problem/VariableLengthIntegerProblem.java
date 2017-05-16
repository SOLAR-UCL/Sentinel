package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem;

/**
 * @author Giovani Guizzo
 */
public interface VariableLengthIntegerProblem extends VariableLengthProblem<Integer> {

    /**
     *
     * @return
     */
    int getUpperVariableBound();

    /**
     *
     * @return
     */
    int getLowerVariableBound();

}
