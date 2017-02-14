package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import com.google.common.collect.Lists;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class SimplePruneOperatorTest {

	private static JMetalRandom randomGenerator;
	private static MutationStrategyGenerationProblem problem;

	@BeforeClass
	public static void setUpClass() throws IOException {
		randomGenerator = JMetalRandom.getInstance();
		problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
														10,
														15,
														1,
														10,
														2,
														1,
														null);
	}

	@AfterClass
	public static void tearDownClass() {
		randomGenerator.setRandomGenerator(new JavaRandomGenerator());
	}

	@Test
	public void execute() throws Exception {
		SimplePruneOperator<Integer> operator = new SimplePruneOperator<>(1.0, problem.getMinLength());

		VariableLengthSolution<Integer> solution = problem.createSolution();
		solution.addAllVariables(Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17));

		solution = operator.execute(solution);
		assertEquals(10, solution.getNumberOfVariables());
	}

}