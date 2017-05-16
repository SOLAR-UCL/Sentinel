package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.mutation.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * @author Giovani Guizzo
 */
public class SimpleRandomVariableMutation implements MutationOperator<VariableLengthSolution<Integer>> {

    private final int maxValue;
    private final int minValue;
    private double mutationProbability;
    private JMetalRandom randomGenerator;

    /**
     * Constructor
     *
     * @param mutationProbability
     * @param minValue
     * @param maxValue
     */
    public SimpleRandomVariableMutation(double mutationProbability, int minValue, int maxValue) {
        this.mutationProbability = mutationProbability;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.randomGenerator = JMetalRandom.getInstance();
    }

    /**
     * Perform the mutation operation
     *
     * @param probability Mutation setProbability
     * @param solution The solution to mutate
     */
    public void doMutation(double probability, VariableLengthSolution<Integer> solution) {
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            if (this.randomGenerator.nextDouble() <= probability) {
                solution.setVariableValue(i, this.randomGenerator.nextInt(this.minValue, this.maxValue));
            }
        }
    }

    /**
     * Execute() method
     *
     * @param solution
     * @return
     */
    @Override
    public VariableLengthSolution<Integer> execute(VariableLengthSolution<Integer> solution) {
        this.doMutation(this.mutationProbability, solution);
        return solution;
    }
}
