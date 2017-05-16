package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.duplicate.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.duplicate.DuplicateOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.List;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public class SimpleDuplicateOperator<T> implements DuplicateOperator<VariableLengthSolution<T>> {

    private int maxLength;
    private double probability;
    private JMetalRandom randomGenerator;

    /**
     *
     * @param probability
     * @param maxLength
     */
    public SimpleDuplicateOperator(double probability, int maxLength) {
        this.probability = probability;
        this.maxLength = maxLength;
        this.randomGenerator = JMetalRandom.getInstance();
    }

    @Override
    public VariableLengthSolution<T> execute(VariableLengthSolution<T> variableLengthSolution) {
        if (this.randomGenerator.nextDouble() < this.probability) {
            List<T> variables = variableLengthSolution.getVariablesCopy();
            if (variables.size() < this.maxLength) {

                int spaceAvailable = this.maxLength - variables.size();
                if (spaceAvailable > 0) {
                    int startIndex = this.randomGenerator.nextInt(0, variables.size() - 1);
                    int endIndex = this.randomGenerator.nextInt(startIndex + 1, variables.size());
                    int sizeToDuplicate = endIndex - startIndex;
                    if (sizeToDuplicate + variables.size() > this.maxLength) {
                        endIndex = endIndex - (sizeToDuplicate - spaceAvailable);
                    }
                    if (startIndex != endIndex) {
                        variableLengthSolution.addAllVariables(variables.subList(startIndex, endIndex));
                    }
                }
            }
        }
        return variableLengthSolution;
    }

}
