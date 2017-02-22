package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.AbstractVariableLengthIntegerProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.list.SetUniqueList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Giovani Guizzo
 */
public class MutationStrategyGenerationProblem implements AbstractVariableLengthIntegerProblem {

	private final StrategyMapper strategyMapper;
	private final int numberOfStrategyRuns;
	private final List<Program> testPrograms;
	private int lowerVariableBound;
	private int upperVariableBound;
	private int minLength;
	private int maxLength;
	private int maxWraps;
	private int evaluationCount;

	public MutationStrategyGenerationProblem(String grammarFile,
											 int minLength,
											 int maxLength,
											 int lowerVariableBound,
											 int upperVariableBound,
											 int maxWraps,
											 int numberOfStrategyRuns,
											 List<Program> testPrograms) throws IOException {
		this.strategyMapper = new StrategyMapper(grammarFile);
		this.lowerVariableBound = lowerVariableBound;
		this.upperVariableBound = upperVariableBound;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.maxWraps = maxWraps;
		this.numberOfStrategyRuns = numberOfStrategyRuns;
		this.testPrograms = testPrograms;
		this.evaluationCount = 0;
	}

	@Override
	public int getUpperVariableBound() {
		return upperVariableBound;
	}

	@Override
	public int getLowerVariableBound() {
		return lowerVariableBound;
	}

	@Override
	public int getMaxLength() {
		return maxLength;
	}

	@Override
	public int getMinLength() {
		return minLength;
	}

	@Override
	public int getNumberOfVariables() {
		return minLength;
	}

	@Override
	public int getNumberOfObjectives() {
		return 3;
	}

	@Override
	public int getNumberOfConstraints() {
		return 0;
	}

	@Override
	public String getName() {
		return "Mutation Strategy Generation Problem";
	}

	@Override
	public void evaluate(VariableLengthSolution<Integer> solution) {
		System.out.println("Evaluation: " + (++evaluationCount));
		try {
			Strategy strategy = getOrCreateStrategy(solution);

			Program tempProgram = IntegrationFacade.getProgramUnderTest();
			double numberOfMutants = 0;
			double score = 0;
			double elapsedMs = 0;
			for (Program testProgram : testPrograms) {
				IntegrationFacade.setProgramUnderTest(testProgram);
				for (int i = 0; i < numberOfStrategyRuns; i++) {
					Stopwatch stopwatch = Stopwatch.createStarted();
					List<Mutant> mutants = strategy.run();
					IntegrationFacade integrationFacade = IntegrationFacade.getIntegrationFacade();
					integrationFacade.executeMutants(mutants);
					stopwatch.stop();
					// Summing the elapsed time
					elapsedMs +=
							(double) stopwatch.elapsed(TimeUnit.NANOSECONDS) /
							integrationFacade.getConventionalMutationTime(testProgram, TimeUnit.NANOSECONDS);
					// Summing the number of mutants
					numberOfMutants +=
							(double) mutants.size() / integrationFacade.getConventionalQuantityOfMutants(testProgram);
					// Summing the score
					score +=
							integrationFacade.getRelativeMutationScore(testProgram,
																	   mutants.stream()
																			  .map(Mutant::getKillingTestCases)
																			  .reduce((testCases, testCases2) -> SetUniqueList
																					  .setUniqueList(Lists.newArrayList(
																							  Iterables.concat(testCases,
																											   testCases2))))
																			  .orElse(SetUniqueList.setUniqueList(new ArrayList<>())));
				}
			}
			IntegrationFacade.setProgramUnderTest(tempProgram);

			if (numberOfMutants > 0) {
				// Normalizing
				int normalizationFactor = testPrograms.size() * numberOfStrategyRuns;
				elapsedMs /= normalizationFactor;
				numberOfMutants /= normalizationFactor;
				score /= normalizationFactor;

				solution.setObjective(0, elapsedMs);
				solution.setObjective(1, numberOfMutants);
				solution.setObjective(2, score * -1);
			} else {
				setWorst(solution);
			}
		} catch (Exception ex) {
			System.out.println("Exception! Solution: " + solution.getVariablesCopy());
			System.out.println(ex.getMessage());
			// Invalid strategy. Probably discarded due to maximum wraps.
			setWorst(solution);
		}
	}

	private void setWorst(VariableLengthSolution<Integer> solution) {
		solution.setObjective(0, Double.MAX_VALUE);
		solution.setObjective(1, Double.MAX_VALUE);
		solution.setObjective(2, Double.MAX_VALUE);
	}

	private Strategy getOrCreateStrategy(VariableLengthSolution<Integer> solution) {
		Object tempStrategy = solution.getAttribute("Strategy");
		if (tempStrategy == null) {
			List<Integer> variables = solution.getVariablesCopy();
			Iterable<Integer>
					variablesIterator =
					Iterables.limit(Iterables.cycle(variables), variables.size() * (maxWraps + 1));
			tempStrategy = strategyMapper.interpret(variablesIterator);
			solution.setAttribute("Strategy", tempStrategy);
		}
		return (Strategy) tempStrategy;
	}

	@Override
	public VariableLengthSolution<Integer> createSolution() {
		return new DefaultVariableLengthIntegerSolution(this);
	}

}
