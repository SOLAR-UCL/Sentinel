package br.ufpr.inf.gres.sentinel.integration.hg4hom;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Giovani Guizzo
 */
public class HG4HOMFacadeTest {

    public HG4HOMFacadeTest() {
    }

    @Test
    public void instantiateProgram() throws Exception {
        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");
        Program program = facade.instantiateProgram("test.test.Program");
        assertNotNull(program);
        assertEquals(System.getProperty("user.dir")
                + File.separator
                + "training"
                + File.separator
                + "src"
                + File.separator
                + "test"
                + File.separator
                + "test"
                + File.separator
                + "Program.java", program.getSourceFile().getAbsolutePath());
    }

    @Test
    public void instantiateProgram2() throws Exception {
        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");
        Program program = facade.instantiateProgram("test/test/Program.java");
        assertNotNull(program);
        assertEquals(System.getProperty("user.dir")
                + File.separator
                + "training"
                + File.separator
                + "src"
                + File.separator
                + "test"
                + File.separator
                + "test"
                + File.separator
                + "Program.java", program.getSourceFile().getAbsolutePath());
    }

    @Test
    public void instantiatePrograms() throws Exception {
        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");
        List<Program> program = facade.instantiatePrograms(Lists.newArrayList("test.test.Program", "Program2"));
        assertNotNull(program);
        assertFalse(program.isEmpty());
        assertEquals(System.getProperty("user.dir")
                + File.separator
                + "training"
                + File.separator
                + "src"
                + File.separator
                + "test"
                + File.separator
                + "test"
                + File.separator
                + "Program.java", program.get(0).getSourceFile().getAbsolutePath());
        assertEquals(System.getProperty("user.dir")
                + File.separator
                + "training"
                + File.separator
                + "src"
                + File.separator
                + "Program2.java", program.get(1).getSourceFile().getAbsolutePath());
    }

    @Test
    public void getAllOperators() throws Exception {
        IntegrationFacade muJavaFacade = new HG4HOMFacade("");
        List<Operator> allOperators = muJavaFacade.getAllOperators();
        assertNotNull(allOperators);
        assertEquals(47, allOperators.size());
    }

    @Test
    public void executeOperator() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("LOI", "Traditional_L");

        List<Mutant> mutants = facade.executeOperator(operator);
        assertEquals(57, mutants.size());
        assertEquals(57, operator.getGeneratedMutants().size());
        for (Mutant mutant : mutants) {
            assertTrue(mutant.getOperators().contains(operator));
            assertTrue(operator.getGeneratedMutants().contains(mutant));
        }
        facade.executeMutants(mutants);
        assertFalse(mutants.get(0).isAlive());
    }

    @Test
    public void executeOperator2() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("AMC", "Class_A");

        List<Mutant> mutants = facade.executeOperator(operator);
        assertEquals(39, mutants.size());
        assertEquals(39, operator.getGeneratedMutants().size());
        for (Mutant mutant : mutants) {
            assertTrue(mutant.getOperators().contains(operator));
            assertTrue(operator.getGeneratedMutants().contains(mutant));
        }
        facade.executeMutants(mutants);
        assertFalse(mutants.get(8).isAlive());
    }

    @Test(expected = Exception.class)
    public void executeOperator3() throws Exception {
        Program programUnderTest = new Program("test.Unknown", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("AMC", "Class_A");

        List<Mutant> mutants = facade.executeOperator(operator);
    }

    @Test(expected = Exception.class)
    public void executeOperator4() throws Exception {
        Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("training/wrongPath"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("AMC", "Class_A");

        List<Mutant> mutants = facade.executeOperator(operator);
    }

    @Test(expected = Exception.class)
    public void executeOperator5() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training/wrongPath");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("AMC", "Class_A");

        List<Mutant> mutants = facade.executeOperator(operator);
    }

    @Test
    @Ignore
    public void executeOperator6() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Mutant> aliveMutants = new ArrayList<>();
        List<Mutant> deadMutants = new ArrayList<>();
        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators());
        facade.executeMutants(mutants);
        aliveMutants.addAll(mutants.stream().filter(Mutant::isAlive).collect(Collectors.toList()));
        deadMutants.addAll(mutants.stream().filter(Mutant::isDead).collect(Collectors.toList()));
        stopwatch.stop();

        System.out.println("Alive: " + aliveMutants.size());
        System.out.println("Dead: " + deadMutants.size());
        System.out.println("Time s: " + stopwatch.elapsed(TimeUnit.SECONDS) + "s");
        System.out.println("Time m: " + stopwatch.elapsed(TimeUnit.MINUTES) + "m");
        assertEquals(79, aliveMutants.size());
        assertEquals(526, deadMutants.size());
    }

    @Test
    public void executeOperators() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("LOI", "Traditional_L");
        Operator operator2 = new Operator("AMC", "Class_A");

        List<Mutant> mutants = facade.executeOperators(Lists.newArrayList(operator, operator2));
        assertEquals(96, mutants.size());
        assertEquals(57, operator.getGeneratedMutants().size());
        assertEquals(39, operator2.getGeneratedMutants().size());
    }

    @Test
    public void combineMutants() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("AMC", "Class_A");

        List<Mutant> mutants = facade.executeOperator(operator);

        mutants
                = Lists.newArrayList(mutants.get(0),
                        mutants.stream()
                                .filter(mutant -> mutant.getFullName().equals("AMC_4"))
                                .findAny()
                                .get());
        Mutant hom = facade.combineMutants(mutants);
        assertNotNull(hom);

        facade.executeMutant(hom);
        assertEquals("AMC_1_and_AMC_4", hom.getFullName());
        assertEquals(1, hom.getOperators().size());
        assertEquals(2, hom.getOrder());
        assertArrayEquals(mutants.toArray(), hom.getConstituentMutants().toArray());
        assertTrue(hom.isAlive());
        assertEquals(programUnderTest, hom.getOriginalProgram());
    }

    @Test(expected = Exception.class)
    public void combineMutants2() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("AMC", "Class_A");

        List<Mutant> mutants = facade.executeOperator(operator);
        mutants = Lists.newArrayList(mutants.get(0), mutants.get(11));

        Mutant hom = facade.combineMutants(mutants);
        assertNull(hom);

        hom = facade.combineMutants(Lists.newArrayList(hom, mutants.get(0)));
        assertNull(hom);

        facade.setMuJavaHome("Unknown.invalid");
        facade.combineMutants(mutants);
    }

    @Test
    public void combineMutants3() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("AMC", "Class_A");

        List<Mutant> mutants = facade.executeOperator(operator);

        mutants
                = Lists.newArrayList(mutants.get(0),
                        mutants.stream()
                                .filter(mutant -> mutant.getFullName().equals("AMC_17"))
                                .findAny()
                                .get());
        Mutant hom = facade.combineMutants(mutants);
        assertNotNull(hom);

        facade.executeMutant(hom);
        assertEquals("AMC_1_and_AMC_17", hom.getFullName());
        assertEquals(1, hom.getOperators().size());
        assertEquals(2, hom.getOrder());
        assertArrayEquals(mutants.toArray(), hom.getConstituentMutants().toArray());
        assertFalse(hom.isAlive());
        assertEquals(programUnderTest, hom.getOriginalProgram());
    }

    @Test
    public void combineMutants4() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("AMC", "Class_A");
        List<Mutant> mutants = facade.executeOperator(operator);

        operator = new Operator("LOI", "Traditional_L");
        mutants.addAll(facade.executeOperator(operator));

        mutants
                = Lists.newArrayList(mutants.get(0),
                        mutants.stream()
                                .filter(mutant -> mutant.getFullName().equals("LOI_1"))
                                .findAny()
                                .get());
        Mutant hom = facade.combineMutants(mutants);
        assertNotNull(hom);

        facade.executeMutant(hom);
        assertEquals("AMC_1_and_LOI_1", hom.getFullName());
        assertEquals(2, hom.getOperators().size());
        assertEquals(2, hom.getOrder());
        assertArrayEquals(mutants.toArray(), hom.getConstituentMutants().toArray());
        assertFalse(hom.isAlive());
        assertEquals(programUnderTest, hom.getOriginalProgram());
    }

    @Test
    public void getAndSetMuJavaHome() throws Exception {
        HG4HOMFacade facade = new HG4HOMFacade("Test");
        facade.setMuJavaHome("Test2");
        assertEquals("Test2", facade.getMuJavaHome());
    }

    @Test
    public void combineMutants5() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp", new File("training/src/br/ufpr/inf/gres/TriTyp.java"));

        HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "training");

        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(programUnderTest);

        Operator operator = new Operator("AMC", "Class_A");

        List<Mutant> mutants = facade.executeOperator(operator);

        mutants
                = Lists.newArrayList(mutants.get(0),
                        mutants.stream()
                                .filter(mutant -> mutant.getFullName().equals("AMC_4"))
                                .findAny()
                                .get(),
                        mutants.get(5));
        Mutant hom = facade.combineMutants(mutants);
        assertNotNull(hom);

        facade.executeMutant(hom);
        assertEquals("AMC_1_and_AMC_4", hom.getFullName());
        assertEquals(1, hom.getOperators().size());
        assertEquals(2, hom.getOrder());
        assertEquals(40, operator.getGeneratedMutants().size());
        assertArrayEquals(mutants.subList(0, 2).toArray(), hom.getConstituentMutants().toArray());
        assertTrue(hom.isAlive());
        assertEquals(programUnderTest, hom.getOriginalProgram());
    }

}
