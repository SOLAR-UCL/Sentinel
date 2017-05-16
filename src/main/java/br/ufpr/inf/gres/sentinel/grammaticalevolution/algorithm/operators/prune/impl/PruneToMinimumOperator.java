package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.PruneOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.List;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public class PruneToMinimumOperator<T> implements PruneOperator<VariableLengthSolution<T>> {

    private int minLength;
    private double probability;
    private JMetalRandom randomGenerator;

    /**
     *
     * @param probability
     * @param minLength
     */
    public PruneToMinimumOperator(double probability, int minLength) {
        this.probability = probability;
        this.minLength = minLength;
        this.randomGenerator = JMetalRandom.getInstance();
    }

    @Override
    public VariableLengthSolution<T> execute(VariableLengthSolution<T> variableLengthSolution) {
        if (this.randomGenerator.nextDouble() < this.probability) {
            List<T> variables = variableLengthSolution.getVariablesCopy();
            if (variables.size() > this.minLength) {
                variables = variables.subList(0, this.minLength);
                variableLengthSolution.clearVariables();
                variableLengthSolution.addAllVariables(variables);
            }
        }
        return variableLengthSolution;
    }
}
