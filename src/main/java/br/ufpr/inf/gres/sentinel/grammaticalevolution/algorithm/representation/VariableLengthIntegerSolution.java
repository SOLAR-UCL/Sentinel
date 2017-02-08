package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation;

import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.AbstractGenericSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class VariableLengthIntegerSolution extends AbstractGenericSolution<Integer, IntegerProblem> implements IntegerSolution {

	private int maxLength;
	private int minLength;
	private List<Integer> variables;

	/**
	 * Constructor
	 *
	 * @param problem
	 */
	public VariableLengthIntegerSolution(IntegerProblem problem, int minLength, int maxLength) {
		super(problem);
		this.maxLength = maxLength;
		this.minLength = minLength;
		this.variables = new ArrayList<>();

		int numberOfVariables = randomGenerator.nextInt(minLength, maxLength);
		for (int i = 0; i < numberOfVariables; i++) {
			addVariable(randomGenerator.nextInt(getLowerBound(i), getUpperBound(i)));
		}
	}

	public VariableLengthIntegerSolution(VariableLengthIntegerSolution solution) {
		super(solution.problem);
		this.maxLength = solution.maxLength;
		this.minLength = solution.minLength;
		this.variables = new ArrayList<>();

		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			addVariable(solution.getVariableValue(i));
		}

		for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
			setObjective(i, solution.getObjective(i));
		}

		attributes = new HashMap<>(solution.attributes);
	}

	@Override
	public Solution<Integer> copy() {
		return new VariableLengthIntegerSolution(this);
	}

	@Override
	public String getVariableValueString(int index) {
		return variables.get(index).toString();
	}

	@Override
	public Integer getLowerBound(int index) {
		return problem.getLowerBound(index);
	}

	@Override
	public Integer getUpperBound(int index) {
		return problem.getUpperBound(index);
	}

	@Override
	public Integer getVariableValue(int index) {
		return variables.get(index);
	}

	@Override
	public void setVariableValue(int index, Integer value) {
		variables.set(index, value);
	}

	public void addVariable(Integer value) {
		variables.add(value);
	}

	public void addAllVariables(List<Integer> values) {
		variables.addAll(values);
	}

	@Override
	public int getNumberOfVariables() {
		return variables.size();
	}

	public void pruneVariables() {
		this.variables = this.variables.subList(0, minLength);
	}

	public void duplicateVariables() {
		int spaceAvailable = maxLength - variables.size();
		if (spaceAvailable > 0) {
			int startIndex = randomGenerator.nextInt(0, variables.size() - 1);
			int endIndex = randomGenerator.nextInt(startIndex + 1, variables.size());
			int sizeToDuplicate = endIndex - startIndex;
			if (sizeToDuplicate + variables.size() > maxLength) {
				endIndex = endIndex - (sizeToDuplicate - spaceAvailable);
			}
			if (startIndex != endIndex) {
				addAllVariables(this.variables.subList(startIndex, endIndex));
			}
		}
	}
}
