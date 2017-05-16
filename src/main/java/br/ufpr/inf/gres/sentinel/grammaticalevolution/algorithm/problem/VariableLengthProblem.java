package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import org.uma.jmetal.problem.Problem;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public interface VariableLengthProblem<T> extends Problem<VariableLengthSolution<T>> {

    /**
     *
     * @return
     */
    int getMaxLength();

    /**
     *
     * @return
     */
    int getMinLength();
}
