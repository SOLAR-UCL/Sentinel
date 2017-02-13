package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.duplicate.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolutionTest;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import com.google.common.collect.Iterables;
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
public class SimpleDuplicateOperatorTest {

	private static JMetalRandom randomGenerator;
	private static MutationStrategyGenerationProblem problem;

	@BeforeClass
	public static void setUpClass() throws IOException {
		randomGenerator = JMetalRandom.getInstance();
		problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(), 10, 15, 1, 10, 2);
	}

	@AfterClass
	public static void tearDownClass() {
		randomGenerator.setRandomGenerator(new JavaRandomGenerator());
	}

	@Test
	public void duplicateVariables() throws Exception {
		randomGenerator.setRandomGenerator(new VariableLengthSolutionTest.PseudoRandomGeneratorStub(Iterables.cycle(10,
																													1,
																													2,
																													3,
																													4,
																													5,
																													6,
																													7,
																													8,
																													9,
																													10,
																													0,
																													10)
																											 .iterator()));
		VariableLengthSolution solution = problem.createSolution();
		SimpleDuplicateOperator<Integer> duplicateOperator = new SimpleDuplicateOperator<>(1.0, problem.getMaxLength());
		solution = duplicateOperator.execute(solution);
		assertEquals(15, solution.getNumberOfVariables());
		assertEquals((int) 1, (int) solution.getVariableValue(10));
		assertEquals((int) 2, (int) solution.getVariableValue(11));
		assertEquals((int) 3, (int) solution.getVariableValue(12));
		assertEquals((int) 4, (int) solution.getVariableValue(13));
		assertEquals((int) 5, (int) solution.getVariableValue(14));
	}

	@Test
	public void duplicateVariables2() throws Exception {
		randomGenerator.setRandomGenerator(new VariableLengthSolutionTest.PseudoRandomGeneratorStub(Iterables.cycle(10,
																													1,
																													2,
																													3,
																													4,
																													5,
																													6,
																													7,
																													8,
																													9,
																													10,
																													7,
																													8)
																											 .iterator()));
		VariableLengthSolution solution = problem.createSolution();
		SimpleDuplicateOperator<Integer> duplicateOperator = new SimpleDuplicateOperator<>(1.0, problem.getMaxLength());
		solution = duplicateOperator.execute(solution);
		assertEquals(11, solution.getNumberOfVariables());
		assertEquals((int) 8, (int) solution.getVariableValue(10));
	}

	@Test
	public void duplicateVariables3() throws Exception {
		randomGenerator.setRandomGenerator(new VariableLengthSolutionTest.PseudoRandomGeneratorStub(Iterables.cycle(10,
																													1,
																													2,
																													3,
																													4,
																													5,
																													6,
																													7,
																													8,
																													9,
																													10,
																													0,
																													10)
																											 .iterator()));
		VariableLengthSolution<Integer> solution = new DefaultVariableLengthIntegerSolution(new MutationStrategyGenerationProblem(
				GrammarFiles.getDefaultGrammarPath(),
				10,
				10,
				1,
				10,
				2));
		SimpleDuplicateOperator<Integer> duplicateOperator = new SimpleDuplicateOperator<>(1.0, 10);
		solution = duplicateOperator.execute(solution);
		assertEquals(10, solution.getNumberOfVariables());
	}

}