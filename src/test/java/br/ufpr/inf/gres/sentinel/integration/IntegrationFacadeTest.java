package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class IntegrationFacadeTest {

    public IntegrationFacadeTest() {
    }

    @Test
    public void getConventionalStats() throws Exception {
        IntegrationFacadeStub facade = new IntegrationFacadeStub();
        Program program1 = new Program("Test", "");
        Program program2 = new Program("Test2", "");
        facade.initializeConventionalStrategy(program1, 5);
        List<Long> times = facade.getConventionalExecutionCPUTimes().get(program1);
        assertNotNull(times);
        assertFalse(times.isEmpty());
        facade.initializeConventionalStrategy(program2, 5);
        times = facade.getConventionalExecutionCPUTimes().get(program2);
        assertNotNull(times);
        assertTrue(!times.isEmpty());
        facade = new IntegrationFacadeStub();
        facade.initializeConventionalStrategy(program1, 5);
        facade.initializeConventionalStrategy(program2, 5);
        assertEquals(16, facade.getConventionalMutants().get(program1).size());
        assertEquals(16, facade.getConventionalMutants().get(program2).size());
        facade = new IntegrationFacadeStub();
        facade.initializeConventionalStrategy(program1, 5);
        facade.initializeConventionalStrategy(program2, 5);
        assertEquals(8, facade.getConventionalMutants().get(program1).stream().filter(mutant -> mutant.isDead()).count());
        assertEquals(8, facade.getConventionalMutants().get(program2).stream().filter(mutant -> mutant.isDead()).count());
    }

    @Test
    public void test() {
        IntegrationFacade facade = new PITFacade("");
        IntegrationFacade.setIntegrationFacade(facade);
        assertNotNull(IntegrationFacade.getIntegrationFacade());
        assertTrue(IntegrationFacade.getIntegrationFacade() instanceof PITFacade);
    }

    public static class IntegrationFacadeStub extends IntegrationFacade {

        public IntegrationFacadeStub() {
            super("/stub/path");
        }

        @Override
        public void executeMutant(Mutant mutantToExecute, Program program) {
            this.executeMutants(Lists.newArrayList(mutantToExecute), program);
        }

        @Override
        public void executeMutants(List<Mutant> mutantsToExecute, Program program) {
            int size = mutantsToExecute.size();
            for (int i = 0; i < size / 2; i++) {
                Mutant mutant = mutantsToExecute.get(i);
                TestCase testCase = new TestCase(i % 2 == 0 ? "Test1" : "Test2");
                mutant.getKillingTestCases().add(testCase);
            }
        }

        @Override
        public List<Mutant> executeOperator(Operator operator, Program program) {
            ArrayList<Mutant> mutants = Lists.newArrayList(new Mutant(operator + "_1", new File(operator + "_1"), program),
                    new Mutant(operator + "_2", new File(operator + "_2"), program),
                    new Mutant(operator + "_3", new File(operator + "_3"), program),
                    new Mutant(operator + "_4", new File(operator + "_4"), program));
            for (Mutant mutant : mutants) {
                mutant.setOperator(operator);
                operator.getGeneratedMutants().add(mutant);
            }
            return mutants;
        }

        @Override
        public List<Mutant> executeOperators(List<Operator> operators, Program program) {
            List<Mutant> allMutants = new ArrayList<>();
            for (Operator operator : operators) {
                allMutants.addAll(this.executeOperator(operator, program));
            }
            return allMutants;
        }

        @Override
        public List<Operator> getAllOperators() {
            return Lists.newArrayList(new Operator("Operator1", "Type1"),
                    new Operator("Operator2", "Type1"),
                    new Operator("Operator3", "Type2"),
                    new Operator("Operator4", "Type3"));
        }

        @Override
        public Program instantiateProgram(String programName) {
            return null;
        }

        @Override
        public List<Program> instantiatePrograms(List<String> programNames) {
            return null;
        }

    }

}
