package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.duplicate.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.duplicate.DuplicateOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class SimpleDuplicateOperator<T> implements DuplicateOperator<VariableLengthSolution<T>> {

	private double probability;
	private int maxLength;
	private JMetalRandom randomGenerator;

	public SimpleDuplicateOperator(double probability, int maxLength) {
		this.probability = probability;
		this.maxLength = maxLength;
		randomGenerator = JMetalRandom.getInstance();
	}

	@Override
	public VariableLengthSolution<T> execute(VariableLengthSolution<T> variableLengthSolution) {
		if (randomGenerator.nextDouble() < probability) {
			List<T> variables = variableLengthSolution.getVariablesCopy();
			if (variables.size() < maxLength) {

				int spaceAvailable = maxLength - variables.size();
				if (spaceAvailable > 0) {
					int startIndex = randomGenerator.nextInt(0, variables.size() - 1);
					int endIndex = randomGenerator.nextInt(startIndex + 1, variables.size());
					int sizeToDuplicate = endIndex - startIndex;
					if (sizeToDuplicate + variables.size() > maxLength) {
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
