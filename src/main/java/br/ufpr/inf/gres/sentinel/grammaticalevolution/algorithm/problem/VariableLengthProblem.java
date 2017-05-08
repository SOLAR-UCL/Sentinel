package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import org.uma.jmetal.problem.Problem;

/**
 * @author Giovani Guizzo
 */
public interface VariableLengthProblem<T> extends Problem<VariableLengthSolution<T>> {

    int getMaxLength();

    int getMinLength();
}
