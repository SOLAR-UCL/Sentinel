package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest;
import br.ufpr.inf.gres.sentinel.integration.hg4hom.HG4HOMFacade;
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
                Lists.newArrayList(new Program("Test1", null),
                        new Program("Test2", null)));
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
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeTest.IntegrationFacadeStub());
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 3));
        problem.evaluate(solution);
        assertNotNull(solution.getAttribute("Strategy"));
        assertTrue(solution.getObjective(0) >= 0);
        assertEquals(1, solution.getObjective(1), 0.000001);
        assertEquals(-1, solution.getObjective(2), 0.000001);
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
        assertEquals(Double.MAX_VALUE, solution.getObjective(2), 0.000001);
    }

    @Test
    @Ignore
    public void evaluate3() throws Exception {
        Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));
        problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                15,
                100,
                0,
                179,
                0,
                1,
                Lists.newArrayList(programUnderTest));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 3));
        problem.evaluate(solution);

        System.out.println(solution.getAttribute("Strategy"));
        System.out.println("Objective 1\t(time)\texp > 0:\t" + solution.getObjective(0));
        System.out.println("Objective 2\t(quantity)\texp = 1:\t" + solution.getObjective(1));
        System.out.println("Objective 3\t(score):\texp = 1:\t" + solution.getObjective(2));

        assertNotNull(solution.getAttribute("Strategy"));
        assertTrue(solution.getObjective(0) > 0);
        assertEquals(1, solution.getObjective(1), 0.000001);
        assertEquals(-1, solution.getObjective(2), 0.000001);
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
        assertEquals(Double.MAX_VALUE, solution.getObjective(2), 0.000001);
    }

    @Test
    public void createSolution() throws Exception {
        VariableLengthSolution<Integer> solution = problem.createSolution();
    }

}
