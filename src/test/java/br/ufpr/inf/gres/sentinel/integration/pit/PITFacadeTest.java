package br.ufpr.inf.gres.sentinel.integration.pit;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
        programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));
    }

    @AfterClass
    public static void tearDownClass() {
        facade.tearDown();
    }

    @Test(expected = Exception.class)
    public void testCombineMutants() {
        facade.combineMutants(new ArrayList<>());
    }

    @Test
    public void testExecuteMutant() {
        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators());
        assertNotNull(mutants);

        facade.executeMutant(mutants.get(0));
        assertTrue(mutants.get(0).isDead());

        facade.executeMutant(mutants.get(1));
        assertTrue(mutants.get(1).isDead());

        facade.executeMutant(mutants.get(2));
        assertTrue(mutants.get(2).isDead());

        facade.executeMutant(null);
    }

    @Test
    public void testExecuteMutants() {
        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators());
        assertNotNull(mutants);

        facade.executeMutants(mutants);
        assertEquals(119, mutants.stream().filter(Mutant::isDead).count());
        assertEquals(3, mutants.stream().filter(Mutant::isAlive).count());
    }

    @Test
    public void testExecuteOperator() {
        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);
        List<Operator> allOperators = facade.getAllOperators();

        List<Mutant> mutants = facade.executeOperator(allOperators.get(0));
        assertNotNull(mutants);
        assertEquals(13, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
        assertTrue(mutants.stream().allMatch((t) -> t.getOperators().contains(allOperators.get(0))));
        assertTrue(allOperators.get(0).getGeneratedMutants().containsAll(mutants));
    }

    @Test
    public void testExecuteOperator2() {
        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        List<Mutant> mutants = facade.executeOperator(facade.getAllOperators().get(15));
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testExecuteOperators() {
        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators());
        assertNotNull(mutants);
        assertEquals(122, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
    }

    @Test
    public void testExecuteOperators2() {
        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        List<Mutant> mutants = facade.executeOperators(new ArrayList<>());
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testExecuteOperators3() {
        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators());
        mutants = facade.executeOperators(facade.getAllOperators());
        assertNotNull(mutants);
        assertEquals(122, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
    }

    @Test
    public void testExecuteOperators4() {
        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        IntegrationFacade.setProgramUnderTest(new Program("unknown.Program", new File("unknown" + File.separator + "Program.java")));
        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators());
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testExecuteOperators5() {
        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators());
        assertNotNull(mutants);
        assertFalse(mutants.isEmpty());
        IntegrationFacade.setProgramUnderTest(new Program("unknown.Program", new File("unknown" + File.separator + "Program.java")));
        facade.executeMutants(mutants);
    }

    @Test
    public void testGetAllOperators() {
        List<Operator> allOperators = facade.getAllOperators();
        assertNotNull(allOperators);
        assertEquals(17, allOperators.size());
    }

    @Test
    public void testInstantiateProgram() {
        Program program = facade.instantiateProgram("test.test.Program");
        assertNotNull(program);
        assertEquals(System.getProperty("user.dir")
                + File.separator
                + "training"
                + File.separator
                + "test"
                + File.separator
                + "test"
                + File.separator
                + "Program.java", program.getSourceFile().getAbsolutePath());
    }

    @Test
    public void testInstantiateProgram2() {
        Program program = facade.instantiateProgram("test.test.Program" + File.separator + "Program2.java");
        assertNotNull(program);
        assertEquals(System.getProperty("user.dir")
                + File.separator
                + "training"
                + File.separator
                + "test"
                + File.separator
                + "test"
                + File.separator
                + "Program" + File.separator + "Program2.java", program.getSourceFile().getAbsolutePath());
    }

}
