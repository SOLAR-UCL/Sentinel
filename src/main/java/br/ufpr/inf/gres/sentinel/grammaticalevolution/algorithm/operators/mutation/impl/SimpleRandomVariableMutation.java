package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.mutation.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * @author Giovani Guizzo
 */
public class SimpleRandomVariableMutation implements MutationOperator<VariableLengthSolution<Integer>> {

	private final int minValue;
	private final int maxValue;
	private double mutationProbability;
	private JMetalRandom randomGenerator;

	/**
	 * Constructor
	 */
	public SimpleRandomVariableMutation(double mutationProbability, int minValue, int maxValue) {
		this.mutationProbability = mutationProbability;
		this.minValue = minValue;
		this.maxValue = maxValue;
		randomGenerator = JMetalRandom.getInstance();
	}

	/**
	 * Execute() method
	 */
	@Override
	public VariableLengthSolution<Integer> execute(VariableLengthSolution<Integer> solution) {
		doMutation(mutationProbability, solution);
		return solution;
	}

	/**
	 * Perform the mutation operation
	 *
	 * @param probability Mutation setProbability
	 * @param solution    The solution to mutate
	 */
	public void doMutation(double probability, VariableLengthSolution<Integer> solution) {
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			if (randomGenerator.nextDouble() <= probability) {
				solution.setVariableValue(i, randomGenerator.nextInt(minValue, maxValue));
			}
		}
	}
}