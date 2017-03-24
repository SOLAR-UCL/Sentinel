package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import br.ufpr.inf.gres.sentinel.integration.hg4hom.HG4HOMFacade;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Giovani Guizzo
 */
public class IntegrationFacadeTest {

    public IntegrationFacadeTest() {
    }

    @Test
    public void test() {
        IntegrationFacade muJava = new HG4HOMFacade("");
        IntegrationFacade.setIntegrationFacade(muJava);
        assertNotNull(IntegrationFacade.getIntegrationFacade());
        assertTrue(IntegrationFacade.getIntegrationFacade() instanceof HG4HOMFacade);
    }

    @Test
    public void test1() {
        IntegrationFacade.setProgramUnderTest(new Program("Test", null));
        assertNotNull(IntegrationFacade.getProgramUnderTest());
        assertEquals(IntegrationFacade.getProgramUnderTest(), new Program("Test", null));
    }

    @Test
    public void getConventionalStats() throws Exception {
        IntegrationFacadeStub facade = new IntegrationFacadeStub();
        Program program1 = new Program("Test", null);
        Program program2 = new Program("Test2", null);
        long time = facade.getConventionalMutationTime(program1, TimeUnit.NANOSECONDS);
        assertTrue(time >= 0);
        assertEquals(time, facade.getConventionalMutationTime(program1, TimeUnit.NANOSECONDS));
        time = facade.getConventionalMutationTime(program2, TimeUnit.NANOSECONDS);
        assertTrue(time >= 0);
        facade = new IntegrationFacadeStub();
        assertEquals(16, facade.getConventionalQuantityOfMutants(program1));
        assertEquals(16, facade.getConventionalQuantityOfMutants(program2));
        facade = new IntegrationFacadeStub();
        assertEquals(0.5, facade.getConventionalMutationScore(program1), 0.001);
        assertEquals(0.5, facade.getConventionalMutationScore(program2), 0.001);

        facade = new IntegrationFacadeStub();
        TestCase testCase1 = new TestCase("Test1");
        TestCase testCase2 = new TestCase("Test2");
        TestCase testCase3 = new TestCase("Test3");

        ArrayList<TestCase> testCases = Lists.newArrayList(testCase1, testCase2, testCase1);
        assertEquals(1.0, facade.getRelativeMutationScore(program1, testCases), 0.000001);
        assertEquals(1.0, facade.getRelativeMutationScore(program2, testCases), 0.000001);

        testCases = Lists.newArrayList(testCase1);
        assertEquals(0.5, facade.getRelativeMutationScore(program1, testCases), 0.000001);
        assertEquals(0.5, facade.getRelativeMutationScore(program2, testCases), 0.000001);

        testCases = Lists.newArrayList(testCase2);
        assertEquals(0.5, facade.getRelativeMutationScore(program1, testCases), 0.000001);
        assertEquals(0.5, facade.getRelativeMutationScore(program2, testCases), 0.000001);

        testCases = Lists.newArrayList(testCase3);
        assertEquals(0.0, facade.getRelativeMutationScore(program1, testCases), 0.000001);
        assertEquals(0.0, facade.getRelativeMutationScore(program2, testCases), 0.000001);
    }

    public static class IntegrationFacadeStub extends IntegrationFacade {

        public IntegrationFacadeStub() {
        }

        @Override
        public List<Program> instantiatePrograms(List<String> programNames) {
            return null;
        }

        @Override
        public Program instantiateProgram(String programName) {
            return null;
        }

        @Override
        public List<Operator> getAllOperators() {
            return Lists.newArrayList(new Operator("Operator1", "Type1"),
                    new Operator("Operator2", "Type1"),
                    new Operator("Operator3", "Type2"),
                    new Operator("Operator4", "Type3"));
        }

        @Override
        public List<Mutant> executeOperator(Operator operator) {
            Program programToBeMutated = IntegrationFacade.getProgramUnderTest();
            ArrayList<Mutant> mutants = Lists.newArrayList(new Mutant(operator + "_1", new File(operator + "_1"), programToBeMutated),
                    new Mutant(operator + "_2", new File(operator + "_2"), programToBeMutated),
                    new Mutant(operator + "_3", new File(operator + "_3"), programToBeMutated),
                    new Mutant(operator + "_4", new File(operator + "_4"), programToBeMutated));
            for (Mutant mutant : mutants) {
                mutant.getOperators().add(operator);
                operator.getGeneratedMutants().add(mutant);
            }
            return mutants;
        }

        @Override
        public Mutant combineMutants(List<Mutant> mutantsToCombine) {
            Mutant generatedMutant = new Mutant("", null, IntegrationFacade.getProgramUnderTest());
            generatedMutant.setFullName(Joiner.on("_").join(mutantsToCombine));
            return generatedMutant;
        }

        @Override
        public void executeMutant(Mutant mutantToExecute) {
            executeMutants(Lists.newArrayList(mutantToExecute));
        }

        @Override
        public void executeMutants(List<Mutant> mutantsToExecute) {
            int size = mutantsToExecute.size();
            for (int i = 0; i < size / 2; i++) {
                Mutant mutant = mutantsToExecute.get(i);
                TestCase testCase = new TestCase(i % 2 == 0 ? "Test1" : "Test2");
                mutant.getKillingTestCases().add(testCase);
                testCase.getKillingMutants().add(mutant);
            }
        }

        @Override
        public List<Mutant> executeOperators(List<Operator> operators) {
            List<Mutant> allMutants = new ArrayList<>();
            for (Operator operator : operators) {
                allMutants.addAll(executeOperator(operator));
            }
            return allMutants;
        }

    }

}
