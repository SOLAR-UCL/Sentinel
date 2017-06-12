package br.ufpr.inf.gres.sentinel.integration.pit;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import com.google.common.base.Stopwatch;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Giovani Guizzo
 */
public class PITFacadeTest {

    private static PITFacade facade;
    private static Program programUnderTest;

    @BeforeClass
    public static void setUpClass() {
        facade = new PITFacade(System.getProperty("user.dir") + File.separator + "training");
        programUnderTest = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;");
    }

    @Test
    @Ignore
    public void testJodaExecution() {
        PITFacade facade = new PITFacade("training");
        Program programUnderTest = facade.instantiateProgram("Joda-Time;joda/sources;org.joda.time.*;**TestAllPackages;joda/joda-time-2.9.9-jar-with-dependencies.jar;joda/classes;joda/test-classes");
        IntegrationFacade.setIntegrationFacade(facade);

        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        facade.executeMutants(mutants, programUnderTest);
        stopwatch.stop();

        System.out.println("Time: " + stopwatch.elapsed(TimeUnit.MINUTES) + "m" + (stopwatch.elapsed(TimeUnit.SECONDS) % 60) + "s");
        System.out.println("Size: " + mutants.size());
        System.out.println("Dead: " + mutants.stream().filter(Mutant::isDead).count());
        System.out.println("Alive: " + mutants.stream().filter(Mutant::isAlive).count());
    }

    @Test
    public void testExecuteMutant() {
        IntegrationFacade.setIntegrationFacade(facade);

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);

        facade.executeMutant(mutants.get(0), programUnderTest);
        assertTrue(mutants.get(0).isDead());

        facade.executeMutant(mutants.get(1), programUnderTest);
        assertTrue(mutants.get(1).isDead());

        facade.executeMutant(mutants.get(2), programUnderTest);
        assertTrue(mutants.get(2).isDead());

        facade.executeMutant(null, programUnderTest);
    }

    @Test
    public void testExecuteMutants() {
        IntegrationFacade.setIntegrationFacade(facade);

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);

        facade.executeMutants(mutants, programUnderTest);
        assertEquals(119, mutants.stream().filter(Mutant::isDead).count());
        assertEquals(3, mutants.stream().filter(Mutant::isAlive).count());
    }

    @Test
    public void testExecuteOperator() {
        IntegrationFacade.setIntegrationFacade(facade);
        List<Operator> allOperators = facade.getAllOperators();

        List<Mutant> mutants = facade.executeOperator(allOperators.get(0), programUnderTest);
        assertNotNull(mutants);
        assertEquals(13, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
        assertTrue(mutants.stream().allMatch((t) -> t.getOperator().equals(allOperators.get(0))));
        assertTrue(allOperators.get(0).getGeneratedMutants().containsAll(mutants));
    }

    @Test
    public void testExecuteOperator2() {
        IntegrationFacade.setIntegrationFacade(facade);

        List<Mutant> mutants = facade.executeOperator(facade.getAllOperators().get(15), programUnderTest);
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testExecuteOperators() {
        IntegrationFacade.setIntegrationFacade(facade);

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);
        assertEquals(122, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
    }

    @Test
    public void testExecuteOperators2() {
        IntegrationFacade.setIntegrationFacade(facade);

        List<Mutant> mutants = facade.executeOperators(new ArrayList<>(), programUnderTest);
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testExecuteOperators3() {
        IntegrationFacade.setIntegrationFacade(facade);

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);
        assertEquals(122, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
    }

    @Test(expected = Exception.class)
    public void testExecuteOperators4() {
        IntegrationFacade.setIntegrationFacade(facade);
        Program programUnderTest = new Program("unknown.Program", new File("unknown" + File.separator + "Program.java"));

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testGetAllOperators() {
        List<Operator> allOperators = facade.getAllOperators();
        assertNotNull(allOperators);
        assertEquals(17, allOperators.size());
    }

    @Test
    public void testInstantiateProgram() {
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;br");
        assertNotNull(program);
        assertEquals("Triangle", program.getName());
        assertEquals(System.getProperty("user.dir") + File.separator + "training", program.getSourceFile().getAbsolutePath());
        assertEquals("br.ufpr.inf.gres.TriTyp*", program.getAttribute("targetClassesGlob"));
        assertEquals("br.ufpr.inf.gres.TriTypTest*", program.getAttribute("targetTestsGlob"));
        assertArrayEquals(new Object[]{System.getProperty("user.dir") + File.separator + "training" + File.separator + "br"}, ((List) program.getAttribute("classPath")).toArray());
    }

    @Test
    public void testInstantiateProgram2() {
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*");
        assertNotNull(program);
        assertEquals("Triangle", program.getName());
        assertEquals(System.getProperty("user.dir") + File.separator + "training", program.getSourceFile().getAbsolutePath());
        assertEquals("br.ufpr.inf.gres.TriTyp*", program.getAttribute("targetClassesGlob"));
        assertEquals("br.ufpr.inf.gres.TriTypTest*", program.getAttribute("targetTestsGlob"));
        assertTrue(((List) program.getAttribute("classPath")).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateProgram3() {
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*");
    }

}
