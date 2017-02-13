package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.PruneOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class SimplePruneOperator<T> implements PruneOperator<VariableLengthSolution<T>> {

	private double probability;
	private int minLength;
	private JMetalRandom randomGenerator;

	public SimplePruneOperator(double probability, int minLength) {
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
