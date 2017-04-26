package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.PruneOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.List;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * @author Giovani Guizzo
 */
public class PruneToMinimumOperator<T> implements PruneOperator<VariableLengthSolution<T>> {

    private double probability;
    private int minLength;
    private JMetalRandom randomGenerator;

    public PruneToMinimumOperator(double probability, int minLength) {
        this.probability = probability;
        this.minLength = minLength;
        this.randomGenerator = JMetalRandom.getInstance();
    }

    @Override
    public VariableLengthSolution<T> execute(VariableLengthSolution<T> variableLengthSolution) {
        if (randomGenerator.nextDouble() < probability) {
            List<T> variables = variableLengthSolution.getVariablesCopy();
            if (variables.size() > minLength) {
                variables = variables.subList(0, minLength);
                variableLengthSolution.clearVariables();
                variableLengthSolution.addAllVariables(variables);
            }
        }
        return variableLengthSolution;
    }
}
