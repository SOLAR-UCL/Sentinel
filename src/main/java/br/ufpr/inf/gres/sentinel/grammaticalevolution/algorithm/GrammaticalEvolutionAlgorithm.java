package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthIntegerSolution;
import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.problem.Problem;

import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class GrammaticalEvolutionAlgorithm extends AbstractGeneticAlgorithm<VariableLengthIntegerSolution, List<VariableLengthIntegerSolution>> {

	/**
	 * Constructor
	 *
	 * @param problem The problem to solve
	 */
	public GrammaticalEvolutionAlgorithm(Problem<VariableLengthIntegerSolution> problem) {
		super(problem);
	}

	@Override
	protected void initProgress() {

	}

	@Override
	protected void updateProgress() {

	}

	@Override
	protected boolean isStoppingConditionReached() {
		return false;
	}

	@Override
	protected List<VariableLengthIntegerSolution> evaluatePopulation(List<VariableLengthIntegerSolution> population) {
		return null;
	}

	@Override
	protected List<VariableLengthIntegerSolution> replacement(List<VariableLengthIntegerSolution> population, List<VariableLengthIntegerSolution> offspringPopulation) {
		return null;
	}

	@Override
	public List<VariableLengthIntegerSolution> getResult() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}
}
