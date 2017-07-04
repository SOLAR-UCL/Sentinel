package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl.AverageCPUTime;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl.AverageQuantity;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl.AverageScore;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.impl.UnconstrainedObjectiveFunctionObserver;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest;
import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;
import com.google.common.collect.Lists;
import java.io.File;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Giovani Guizzo
 */
public class MutationStrategyGenerationProblemTest {

    private static MutationStrategyGenerationProblem problem;

    @Before
    public void setUp() throws Exception {
        problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                10,
                15,
                1,
                10,
                0,
                5,
                1,
                Lists.newArrayList(new Program("Test1", ""),
                        new Program("Test2", "")),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
    }

    @Test
    public void createSolution() throws Exception {
        VariableLengthSolution<Integer> solution = problem.createSolution();
        assertNotNull(solution);
    }

    @Test
    public void evaluate() throws Exception {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeTest.IntegrationFacadeStub());

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 3));
        problem.evaluate(solution);

        assertNotNull(solution.getAttribute("Strategy"));
        Object strategy = solution.getAttribute("Strategy");
        assertTrue(solution.getObjective(0) >= 0);
        assertEquals(-1, solution.getObjective(1), 0.000001);

        VariableLengthSolution<Integer> copy = solution.copy();
        copy.clearVariables();
        copy.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 0, 2, 1, 0, 0, 1, 9, 3));
        problem.evaluate(copy);

        assertNotNull(copy.getAttribute("Strategy"));
        Object strategy2 = copy.getAttribute("Strategy");
        assertTrue(copy.getObjective(0) >= 0);
        assertEquals(-1, copy.getObjective(1), 0.000001);

        assertNotEquals(strategy2, strategy);
    }

    @Test
    public void evaluate2() throws Exception {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeTest.IntegrationFacadeStub());
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9));
        problem.evaluate(solution);
        assertNull(solution.getAttribute("Strategy"));
        assertEquals(Double.MAX_VALUE, solution.getObjective(0), 0.0001);
        assertEquals(Double.MAX_VALUE, solution.getObjective(1), 0.000001);
    }

    @Test
    @Ignore
    public void evaluate3() throws Exception {
        PITFacade facade = new PITFacade(System.getProperty("user.dir") + File.separator + "training");

        Program programUnderTest = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;");
        problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                15,
                100,
                0,
                179,
                0,
                5,
                1,
                Lists.newArrayList(programUnderTest),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));

        IntegrationFacade.setIntegrationFacade(facade);

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 3));
        problem.evaluate(solution);

        Double quantity = new AverageQuantity<Integer>().computeFitness(solution);
        System.out.println(solution.getAttribute("Strategy"));
        System.out.println("Objective 1\t(time)\texp > 0:\t" + solution.getObjective(0));
        System.out.println("Objective 2\t(score)\texp = 1:\t" + solution.getObjective(1));
        System.out.println("Objective 3\t(quantity)\texp = 1:\t" + quantity);

        assertNotNull(solution.getAttribute("Strategy"));
        assertTrue(solution.getObjective(0) > 0);
        assertEquals(-1, solution.getObjective(1), 0.000001);
        assertEquals(1, quantity, 0.000001);
    }

    @Test
    public void evaluate4() throws Exception {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeTest.IntegrationFacadeStub());
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(3));
        problem.evaluate(solution);
        assertNotNull(solution.getAttribute("Strategy"));
        assertEquals(Double.MAX_VALUE, solution.getObjective(0), 0.0001);
        assertEquals(Double.MAX_VALUE, solution.getObjective(1), 0.000001);
    }

    @Test
    @Ignore
    public void evaluate5() throws Exception {
        IntegrationFacade facade = IntegrationFacadeFactory.createIntegrationFacade("PIT", System.getProperty("user.dir") + File.separator + "training");
        Program programUnderTest = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;");
        IntegrationFacade.setIntegrationFacade(facade);
        problem = new MutationStrategyGenerationProblem(GrammarFiles.getGrammarPath(GrammarFiles.DEFAULT_GRAMMAR),
                10,
                100,
                1,
                179,
                10,
                10,
                10,
                Lists.newArrayList(programUnderTest),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
        problem.dettachAllObservers();
        problem.attachObserver(new UnconstrainedObjectiveFunctionObserver());

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(132, 155, 153, 128, 107, 119, 116, 80, 115, 130, 16, 173, 175, 61, 52, 162, 129, 141, 87, 53, 55, 67, 83, 51, 143, 166, 132, 155, 153, 128, 107, 119, 127, 42, 74, 129, 111, 103, 12, 154, 127, 42, 27, 83, 51, 97, 89, 109, 44, 179, 176, 56, 86, 171, 70, 110, 139, 157, 99, 19));
        String runs = "";
        for (int i = 0; i < 1; i++) {
            problem.evaluate(solution);
            runs += solution.getObjective(0) + " " + solution.getObjective(1) + "\n";
        }
        System.out.println("Runs:\n" + runs);
    }

    @Test
    public void getMaxAndMinLength() throws Exception {
        assertEquals(10, problem.getMinLength());
        assertEquals(15, problem.getMaxLength());
    }

    @Test
    public void getMaxWraps() throws Exception {
        assertEquals(0, problem.getMaxWraps());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Mutation Strategy Generation Problem", problem.getName());
    }

    @Test
    public void getNumberOfConstraints() throws Exception {
        assertEquals(0, problem.getNumberOfConstraints());
    }

    @Test
    public void getNumberOfObjectives() throws Exception {
        assertEquals(2, problem.getNumberOfObjectives());
    }

    @Test
    public void getNumberOfVariables() throws Exception {
        assertEquals(10, problem.getNumberOfVariables());
    }

    @Test
    public void getObjectives() throws Exception {
        assertArrayEquals(new Object[]{new AverageCPUTime<>(), new AverageScore<>()}, problem.getObjectiveFunctions().toArray());
    }

    @Test
    public void getUpperAndLowerVariableBound() throws Exception {
        assertEquals(1, problem.getLowerVariableBound());
        assertEquals(10, problem.getUpperVariableBound());
    }

}
