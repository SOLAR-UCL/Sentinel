package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.PseudoRandomGenerator;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class VariableLengthSolutionTest {

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

	@Before
	public void setUp() {
		randomGenerator.setRandomGenerator(new PseudoRandomGeneratorStub(Iterables.cycle(10,
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
																						 10).iterator()));
	}

	@Test
	public void addSetAndGetVariableValue() throws Exception {
		VariableLengthSolution<Integer> solution = problem.createSolution();
		assertEquals((int) 1, (int) solution.getVariableValue(0));
		solution.addVariable(1);
		assertEquals((int) 1, (int) solution.getVariableValue(10));
		solution.setVariableValue(0, 2);
		assertEquals((int) 2, (int) solution.getVariableValue(0));
		assertEquals("2", solution.getVariableValueString(0));
		assertEquals(11, solution.getNumberOfVariables());
		solution.addAllVariables(Lists.newArrayList(3, 4));
		assertEquals(13, solution.getNumberOfVariables());
		List<Integer> list = solution.getVariablesCopy();
		assertNotNull(list);
		assertEquals(13, list.size());
		solution.clearVariables();
		assertEquals(0, solution.getNumberOfVariables());
		assertEquals(13, list.size());
	}

	@Test
	public void copy() throws Exception {
		VariableLengthSolution<Integer> solution = problem.createSolution();
		solution.setVariableValue(0, 200);
		solution.setObjective(0, 1000);
		solution.setObjective(1, 2000);
		solution.setAttribute("Test", "Test2");

		Solution<Integer> copy = solution.copy();

		assertEquals((int) 200, (int) copy.getVariableValue(0));
		assertEquals((int) 1000, (int) copy.getObjective(0));
		assertEquals((int) 2000, (int) copy.getObjective(1));
		assertEquals("Test2", copy.getAttribute("Test"));

		solution.setVariableValue(0, -1);
		solution.setObjective(0, -1);
		solution.setObjective(1, -1);
		solution.setAttribute("Test", "Test3");

		assertNotEquals((int) solution.getVariableValue(0), (int) copy.getVariableValue(0));
		assertNotEquals((int) solution.getObjective(0), (int) copy.getObjective(0));
		assertNotEquals((int) solution.getObjective(1), (int) copy.getObjective(1));
		assertNotEquals(solution.getAttribute("Test"), copy.getAttribute("Test"));
	}

	public static class PseudoRandomGeneratorStub implements PseudoRandomGenerator {

		private Iterator<Integer> integerValues;

		public PseudoRandomGeneratorStub(Iterator<Integer> integerValues) {
			this.integerValues = integerValues;
		}

		@Override
		public int nextInt(int lowerBound, int upperBound) {
			return integerValues.next();
		}

		@Override
		public double nextDouble(double lowerBound, double upperBound) {
			return 0;
		}

		@Override
		public double nextDouble() {
			return 0;
		}

		@Override
		public long getSeed() {
			return 0;
		}

		@Override
		public void setSeed(long seed) {

		}

		@Override
		public String getName() {
			return null;
		}
	}
}