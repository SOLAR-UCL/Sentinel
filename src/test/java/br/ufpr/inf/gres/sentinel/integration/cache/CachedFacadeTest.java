package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.impl.CachedObjectiveFunctionObserver;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class CachedFacadeTest {

    private static final Logger LOGGER = LogManager.getLogger(CachedFacadeTest.class);

    private static CachedFacade facade;
    private static Program programUnderTest;

    public CachedFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        LOGGER.debug("Initializing CachedFacadeTest.");
        String directory = System.getProperty("user.dir");
        facade = new CachedFacade(new PITFacade(directory), "src" + File.separator + "test" + File.separator + "resources", "src" + File.separator + "test" + File.separator + "resources");
        programUnderTest = facade.instantiateProgram("Triangle;src/test/resources;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;;src/test/resources");
        LOGGER.debug("Initializing program.");
        facade.initializeConventionalStrategy(programUnderTest, 5);
    }

    @Test
    public void testGetAllOperators() {
        LOGGER.debug("Testing method: testGetAllOperators");
        Collection<Operator> allOperators = facade.getAllOperators();
        assertNotNull(allOperators);
        assertEquals(7, allOperators.size());
    }

    @Test
    public void testExecuteOperator() {
        LOGGER.debug("Testing method: testExecuteOperator");
        IntegrationFacade.setIntegrationFacade(facade);

        Collection<Operator> allOperators = facade.getAllOperators();

        Operator operator1 = allOperators.iterator().next();
        Collection<Mutant> mutants = facade.executeOperator(operator1, programUnderTest);
        assertNotNull(mutants);
        assertEquals(13, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
        assertTrue(mutants.stream().allMatch((t) -> t.getOperator().equals(operator1)));
        assertTrue(operator1.getGeneratedMutants().containsAll(mutants));
        assertTrue(operator1.getCpuTime() > 0);
        assertTrue(operator1.getExecutionTime() > 0);

        facade.initializeConventionalStrategy(programUnderTest, 1);

        Collection<Operator> allOperators2 = facade.getAllOperators();

        Operator operator2 = allOperators2.iterator().next();
        Collection<Mutant> mutants2 = facade.executeOperator(operator2, programUnderTest);
        assertNotNull(mutants2);
        assertEquals(13, mutants2.size());
        assertTrue(mutants2.stream().allMatch(Mutant::isAlive));
        assertTrue(mutants2.stream().allMatch((t) -> t.getOperator().equals(operator2)));
        assertTrue(operator2.getGeneratedMutants().containsAll(mutants2));
        assertTrue(operator2.getCpuTime() > 0);
        assertTrue(operator2.getExecutionTime() > 0);

        assertEquals(operator1.getCpuTime(), operator2.getCpuTime(), 0.0001);
        assertEquals(operator1.getExecutionTime(), operator2.getExecutionTime(), 0.0001);
        assertArrayEquals(operator1.getGeneratedMutants().toArray(), operator2.getGeneratedMutants().toArray());
    }

    @Test
    public void testExecuteOperator2() {
        LOGGER.debug("Testing method: testExecuteOperator2");
        IntegrationFacade.setIntegrationFacade(facade);

        Collection<Operator> allOperators = facade.getAllOperators();

        Operator operator1 = allOperators.iterator().next();
        Collection<Mutant> mutants = facade.executeOperator(operator1, programUnderTest);
        mutants = facade.executeOperator(operator1, programUnderTest);
        assertNotNull(mutants);
        assertEquals(13, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
        assertTrue(mutants.stream().allMatch((t) -> t.getOperator().equals(operator1)));
        assertTrue(operator1.getGeneratedMutants().containsAll(mutants));
        assertTrue(operator1.getCpuTime() > 0);
        assertTrue(operator1.getExecutionTime() > 0);

        facade.executeMutant(mutants.iterator().next(), programUnderTest);
        assertTrue(mutants.iterator().next().isDead());
        assertEquals(1, mutants.iterator().next().getKillingTestCases().size());
    }

    @Test
    public void testExecuteOperators() {
        LOGGER.debug("Testing method: testExecuteOperators");

        List<Operator> allOperators = new ArrayList<>(facade.getAllOperators());

        Collection<Mutant> mutants = facade.executeOperators(allOperators, programUnderTest);
        assertNotNull(mutants);
        assertEquals(50, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));

        List<Operator> allOperators2 = new ArrayList<>(facade.getAllOperators());

        Collection<Mutant> mutants2 = facade.executeOperators(allOperators2, programUnderTest);
        assertNotNull(mutants2);
        assertEquals(50, mutants2.size());
        assertTrue(mutants2.stream().allMatch(Mutant::isAlive));

        for (int i = 0; i < allOperators.size(); i++) {
            Operator operator1 = allOperators.get(i);
            Operator operator2 = allOperators2.get(i);

            assertEquals(operator1.getCpuTime(), operator2.getCpuTime(), 0.0001);
            assertEquals(operator1.getExecutionTime(), operator2.getExecutionTime(), 0.0001);
            assertArrayEquals(operator1.getGeneratedMutants().toArray(), operator2.getGeneratedMutants().toArray());
        }
    }

    @Test
    public void testExecuteOperators2() {
        LOGGER.debug("Testing method: testExecuteOperators2");
        IntegrationFacade.setIntegrationFacade(facade);

        Collection<Mutant> mutants = facade.executeOperators(new ArrayList<>(), programUnderTest);
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test(expected = Exception.class)
    public void testExecuteOperators4() {
        LOGGER.debug("Testing method: testExecuteOperators4");
        IntegrationFacade.setIntegrationFacade(facade);
        Program programUnderTest = new Program("unknown.Program", new File("unknown" + File.separator + "Program.java"));

        Collection<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testExecuteMutant() {
        LOGGER.debug("Testing method: testExecuteMutant");
        IntegrationFacade.setIntegrationFacade(facade);

        List<Operator> allOperators = new ArrayList<>(facade.getAllOperators());

        List<Mutant> mutants = new ArrayList<>(facade.executeOperators(allOperators, programUnderTest));
        assertNotNull(mutants);
        assertEquals(50, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));

        List<Operator> allOperators2 = new ArrayList<>(facade.getAllOperators());

        List<Mutant> mutants2 = new ArrayList<>(facade.executeOperators(allOperators2, programUnderTest));
        assertNotNull(mutants2);
        assertEquals(50, mutants2.size());
        assertTrue(mutants2.stream().allMatch(Mutant::isAlive));

        facade.executeMutant(mutants.get(0), programUnderTest);
        assertTrue(mutants.get(0).isDead());

        facade.executeMutant(mutants2.get(0), programUnderTest);
        assertTrue(mutants2.get(0).isDead());

        assertEquals(mutants.get(0).getCpuTime(), mutants.get(0).getCpuTime(), 0.001);
        assertEquals(mutants.get(0).getExecutionTime(), mutants.get(0).getExecutionTime(), 0.001);
        assertArrayEquals(mutants.get(0).getKillingTestCases().toArray(), mutants2.get(0).getKillingTestCases().toArray());

        facade.executeMutant(null, programUnderTest);
    }

    @Test
    public void testExecuteMutants() {
        LOGGER.debug("Testing method: testExecuteMutants");
        IntegrationFacade.setIntegrationFacade(facade);

        List<Operator> allOperators = new ArrayList<>(facade.getAllOperators());

        List<Mutant> mutants = new ArrayList<>(facade.executeOperators(allOperators, programUnderTest));
        assertNotNull(mutants);
        assertEquals(50, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));

        List<Operator> allOperators2 = new ArrayList<>(facade.getAllOperators());

        List<Mutant> mutants2 = new ArrayList<>(facade.executeOperators(allOperators2, programUnderTest));
        assertNotNull(mutants2);
        assertEquals(50, mutants2.size());
        assertTrue(mutants2.stream().allMatch(Mutant::isAlive));

        facade.executeMutants(mutants, programUnderTest);
        assertEquals(50, mutants.stream().filter(Mutant::isDead).count());
        assertEquals(0, mutants.stream().filter(Mutant::isAlive).count());

        facade.executeMutants(mutants2, programUnderTest);
        assertEquals(50, mutants2.stream().filter(Mutant::isDead).count());
        assertEquals(0, mutants2.stream().filter(Mutant::isAlive).count());

        for (int i = 0; i < mutants.size(); i++) {
            Mutant mutant1 = mutants.get(i);
            Mutant mutant2 = mutants2.get(i);

            assertEquals(mutant1.getCpuTime(), mutant2.getCpuTime(), 0.0001);
            assertEquals(mutant1.getExecutionTime(), mutant2.getExecutionTime(), 0.0001);
            assertArrayEquals(mutant1.getKillingTestCases().toArray(), mutant2.getKillingTestCases().toArray());
        }
    }

    @Test
    public void testInstantiateProgram() {
        LOGGER.debug("Testing method: testInstantiateProgram");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;;br");
        assertNotNull(program);
        assertEquals("Triangle", program.getName());
        assertEquals(System.getProperty("user.dir"), program.getSourceFile().getAbsolutePath());
        assertEquals("br.ufpr.inf.gres.TriTyp*", program.getAttribute("targetClassesGlob"));
        assertEquals("br.ufpr.inf.gres.TriTypTest*", program.getAttribute("targetTestsGlob"));
        assertEquals("", program.getAttribute("excludedClassesGlob"));
        assertArrayEquals(new Object[]{System.getProperty("user.dir") + File.separator + "br"}, ((List) program.getAttribute("classPath")).toArray());
    }

    @Test
    public void testInstantiateProgram2() {
        LOGGER.debug("Testing method: testInstantiateProgram2");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;excluded.classes.*");
        assertNotNull(program);
        assertEquals("Triangle", program.getName());
        assertEquals(System.getProperty("user.dir"), program.getSourceFile().getAbsolutePath());
        assertEquals("br.ufpr.inf.gres.TriTyp*", program.getAttribute("targetClassesGlob"));
        assertEquals("br.ufpr.inf.gres.TriTypTest*", program.getAttribute("targetTestsGlob"));
        assertEquals("excluded.classes.*", program.getAttribute("excludedClassesGlob"));
        assertTrue(((List) program.getAttribute("classPath")).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateProgram3() {
        LOGGER.debug("Testing method: testInstantiateProgram3");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateProgram4() {
        LOGGER.debug("Testing method: testInstantiateProgram4");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;test.test");
    }

    @Test
    public void testRead() throws IOException {
        LOGGER.debug("Testing method: testRead");
        String directory = System.getProperty("user.dir") + File.separator + "training";
        CachedFacade facade = new CachedFacade(new PITFacade(directory), "src" + File.separator + "test" + File.separator + "resources", null);
        IntegrationFacade.setIntegrationFacade(facade);

        CachedObjectiveFunctionObserver observer = new CachedObjectiveFunctionObserver();
        facade.attachObserver(observer);

        MutationStrategyGenerationProblem problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                10,
                15,
                1,
                10,
                10,
                5,
                1,
                Lists.newArrayList(new Program("Triangle", "")),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
        problem.dettachAllObservers();
        problem.attachObserver(observer);

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 2, 3));

        problem.evaluate(solution);

        assertEquals(0.608, solution.getObjective(0), 0.0001);
        assertEquals(-0.76, solution.getObjective(1), 0.0001);

        problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                10,
                15,
                1,
                10,
                0,
                20,
                20,
                Lists.newArrayList(new Program("Triangle", "")),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
        problem.dettachAllObservers();
        problem.attachObserver(observer);

        problem.evaluate(solution);

        assertEquals(0.608, solution.getObjective(0), 0.0001);
        assertEquals(-0.76, solution.getObjective(1), 0.0001);

        problem.evaluate(solution);

        assertEquals(0.608, solution.getObjective(0), 0.0001);
        assertEquals(-0.76, solution.getObjective(1), 0.0001);

        solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0));

        problem.evaluate(solution);

        assertEquals(Double.MAX_VALUE, solution.getObjective(0), 0.001);
        assertEquals(Double.MAX_VALUE, solution.getObjective(1), 0.001);

        solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(3));

        problem.evaluate(solution);

        assertEquals(Double.MAX_VALUE, solution.getObjective(0), 0.001);
        assertEquals(Double.MAX_VALUE, solution.getObjective(1), 0.001);

        facade.dettachObserver(observer);
    }

    @Test
    public void testRead2() throws IOException {
        LOGGER.debug("Testing method: testRead2");
        String directory = System.getProperty("user.dir") + File.separator + "training";
        CachedFacade facade = new CachedFacade(new PITFacade(directory), "src" + File.separator + "test" + File.separator + "resources", null);
        IntegrationFacade.setIntegrationFacade(facade);

        CachedObjectiveFunctionObserver observer = new CachedObjectiveFunctionObserver();
        facade.attachObserver(observer);

        MutationStrategyGenerationProblem problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                10,
                15,
                1,
                10,
                0,
                5,
                1,
                Lists.newArrayList(new Program("Triangle", "")),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
        problem.dettachAllObservers();
        problem.attachObserver(observer);

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(124, 20, 75, 14, 126, 47, 163, 96, 95, 6, 119, 165, 74, 123, 54, 70, 173, 116, 22, 121, 57, 109, 34, 89, 153, 95, 96, 107, 26, 50, 102, 160, 171, 9, 96, 95, 6, 119, 165, 74, 123, 54, 70, 173, 116, 22, 121, 57, 109, 34, 89, 153, 95, 96));

        problem.evaluate(solution);

        assertEquals(1.04, solution.getObjective(0), 0.0001);
        assertEquals(-1.0, solution.getObjective(1), 0.0001);

        problem.evaluate(solution);

        assertEquals(1.04, solution.getObjective(0), 0.0001);
        assertEquals(-1.0, solution.getObjective(1), 0.0001);

        facade.dettachObserver(observer);
    }

    @Test
    public void testRead3() throws IOException {
        LOGGER.debug("Testing method: testRead3");
        String directory = System.getProperty("user.dir") + File.separator + "training";
        CachedFacade facade = new CachedFacade(new PITFacade(directory), "src" + File.separator + "test" + File.separator + "resources", null);
        IntegrationFacade.setIntegrationFacade(facade);

        CachedObjectiveFunctionObserver observer = new CachedObjectiveFunctionObserver();
        facade.attachObserver(observer);

        MutationStrategyGenerationProblem problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                10,
                15,
                1,
                10,
                0,
                5,
                1,
                Lists.newArrayList(new Program("Triangle", "")),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
        problem.dettachAllObservers();
        problem.attachObserver(observer);

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 3));

        problem.evaluate(solution);

        assertEquals(1.0, solution.getObjective(0), 0.0001);
        assertEquals(-1.0, solution.getObjective(1), 0.0001);

        problem.evaluate(solution);

        assertEquals(1.0, solution.getObjective(0), 0.0001);
        assertEquals(-1.0, solution.getObjective(1), 0.0001);

        facade.dettachObserver(observer);
    }

}
