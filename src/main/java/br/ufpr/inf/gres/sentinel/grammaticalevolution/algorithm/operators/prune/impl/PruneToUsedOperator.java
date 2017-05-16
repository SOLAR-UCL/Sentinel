package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.PruneOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.List;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 *
 * @author Giovani Guizzo
 * @param <T>
 */
public class PruneToUsedOperator<T> implements PruneOperator<VariableLengthSolution<T>> {

    private double probability;
    private JMetalRandom randomGenerator;

    /**
     *
     * @param probability
     */
    public PruneToUsedOperator(double probability) {
        this.probability = probability;
        this.randomGenerator = JMetalRandom.getInstance();
    }

    @Override
    public VariableLengthSolution<T> execute(VariableLengthSolution<T> variableLengthSolution) {
        if (this.randomGenerator.nextDouble() < this.probability) {
            List<T> variables = variableLengthSolution.getVariablesCopy();
            Object attribute = variableLengthSolution.getAttribute("Consumed Items Count");
            if (attribute != null) {
                int count = (int) attribute;
                if (variables.size() > count && count != 0) {
                    variables = variables.subList(0, count);
                    variableLengthSolution.clearVariables();
                    variableLengthSolution.addAllVariables(variables);
                }
            }
        }
        return variableLengthSolution;
    }

}
