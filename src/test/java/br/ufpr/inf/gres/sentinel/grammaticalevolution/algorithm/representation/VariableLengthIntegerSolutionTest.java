package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.PseudoRandomGenerator;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class VariableLengthIntegerSolutionTest {

	private static JMetalRandom randomGenerator;
	private static IntegerProblem problem;

	@BeforeClass
	public static void setUpClass() {
		randomGenerator = JMetalRandom.getInstance();
		problem = new IntegerProblemStub();
	}

	@AfterClass
	public static void tearDownClass() {
		randomGenerator.setRandomGenerator(new JavaRandomGenerator());
	}

	@Before
	public void setUp() {
		randomGenerator.setRandomGenerator(new PseudoRandomGeneratorStub(Iterables.cycle(10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 10)
																				  .iterator()));
	}

	@Test
	public void addSetAndGetVariableValue() throws Exception {
		VariableLengthIntegerSolution solution = new VariableLengthIntegerSolution(problem, 10, 15);
		assertEquals((int) 1, (int) solution.getVariableValue(0));
		solution.addVariable(1);
		assertEquals((int) 1, (int) solution.getVariableValue(10));
		solution.setVariableValue(0, 2);
		assertEquals((int) 2, (int) solution.getVariableValue(0));
		assertEquals("2", solution.getVariableValueString(0));
		assertEquals(11, solution.getNumberOfVariables());
		solution.addAllVariables(Lists.newArrayList(3, 4));
		assertEquals(13, solution.getNumberOfVariables());
	}

	@Test
	public void getLowerBound() throws Exception {
		VariableLengthIntegerSolution solution = new VariableLengthIntegerSolution(problem, 10, 15);
		Integer lowerBound = solution.getLowerBound(0);
		Integer upperBound = solution.getUpperBound(0);
		assertEquals(0, (int) lowerBound);
		assertEquals(15, (int) upperBound);
	}

	@Test
	public void pruneVariables() throws Exception {
		VariableLengthIntegerSolution solution = new VariableLengthIntegerSolution(problem, 10, 15);
		solution.addAllVariables(Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17));
		solution.pruneVariables();
		assertEquals(10, solution.getNumberOfVariables());
	}

	@Test
	public void duplicateVariables() throws Exception {
		VariableLengthIntegerSolution solution = new VariableLengthIntegerSolution(problem, 10, 15);
		solution.duplicateVariables();
		assertEquals(15, solution.getNumberOfVariables());
		assertEquals((int) 1, (int) solution.getVariableValue(10));
		assertEquals((int) 2, (int) solution.getVariableValue(11));
		assertEquals((int) 3, (int) solution.getVariableValue(12));
		assertEquals((int) 4, (int) solution.getVariableValue(13));
		assertEquals((int) 5, (int) solution.getVariableValue(14));
	}

	@Test
	public void duplicateVariables2() throws Exception {
		randomGenerator.setRandomGenerator(new PseudoRandomGeneratorStub(Iterables.cycle(10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 7, 8)
																				  .iterator()));
		VariableLengthIntegerSolution solution = new VariableLengthIntegerSolution(problem, 10, 15);
		solution.duplicateVariables();
		assertEquals(11, solution.getNumberOfVariables());
		assertEquals((int) 8, (int) solution.getVariableValue(10));
	}

	@Test
	public void duplicateVariables3() throws Exception {
		randomGenerator.setRandomGenerator(new PseudoRandomGeneratorStub(Iterables.cycle(10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 10)
																				  .iterator()));
		VariableLengthIntegerSolution solution = new VariableLengthIntegerSolution(problem, 10, 10);
		solution.duplicateVariables();
		assertEquals(10, solution.getNumberOfVariables());
	}

	@Test
	public void copy() throws Exception {
		VariableLengthIntegerSolution solution = new VariableLengthIntegerSolution(problem, 10, 15);
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

	private static class PseudoRandomGeneratorStub implements PseudoRandomGenerator {

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

	private static class IntegerProblemStub implements IntegerProblem {

		@Override
		public Integer getLowerBound(int index) {
			return 0;
		}

		@Override
		public Integer getUpperBound(int index) {
			return 15;
		}

		@Override
		public int getNumberOfVariables() {
			return 10;
		}

		@Override
		public int getNumberOfObjectives() {
			return 2;
		}

		@Override
		public int getNumberOfConstraints() {
			return 0;
		}

		@Override
		public String getName() {
			return "Stub";
		}

		@Override
		public void evaluate(IntegerSolution solution) {
		}

		@Override
		public IntegerSolution createSolution() {
			return null;
		}
	}
}