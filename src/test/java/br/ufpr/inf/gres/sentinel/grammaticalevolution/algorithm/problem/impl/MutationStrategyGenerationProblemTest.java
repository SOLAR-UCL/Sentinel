package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class MutationStrategyGenerationProblemTest {

	private static MutationStrategyGenerationProblem problem;

	@BeforeClass
	public static void setUp() throws Exception {
		problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
														10,
														15,
														1,
														10,
														2,
														1,
														null);
	}

	@Test
	public void getUpperAndLowerVariableBound() throws Exception {
		assertEquals(1, problem.getLowerVariableBound());
		assertEquals(10, problem.getUpperVariableBound());
	}

	@Test
	public void getMaxAndMinLength() throws Exception {
		assertEquals(10, problem.getMinLength());
		assertEquals(15, problem.getMaxLength());
	}

	@Test
	public void getNumberOfVariables() throws Exception {
		assertEquals(10, problem.getNumberOfVariables());
	}

	@Test
	public void getNumberOfObjectives() throws Exception {
		assertEquals(3, problem.getNumberOfObjectives());
	}

	@Test
	public void getNumberOfConstraints() throws Exception {
		assertEquals(0, problem.getNumberOfConstraints());
	}

	@Test
	public void getName() throws Exception {
		assertEquals("Mutation Strategy Generation Problem", problem.getName());
	}

	@Test
	public void evaluate() throws Exception {
		//TODO implement it
		problem.evaluate(null);
	}

	@Test
	public void createSolution() throws Exception {
		VariableLengthSolution<Integer> solution = problem.createSolution();
	}

}