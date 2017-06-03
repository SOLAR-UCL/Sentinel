package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Giovani Guizzo
 */
public abstract class IntegrationFacade {

    private static IntegrationFacade FACADE_INSTANCE;
    private static Program PROGRAM_UNDER_TEST;

    /**
     *
     * @return
     */
    public static IntegrationFacade getIntegrationFacade() {
        return FACADE_INSTANCE;
    }

    /**
     *
     * @param facade
     */
    public static void setIntegrationFacade(IntegrationFacade facade) {
        FACADE_INSTANCE = facade;
    }

    /**
     *
     * @return
     */
    public static Program getProgramUnderTest() {
        return PROGRAM_UNDER_TEST;
    }

    /**
     *
     * @param programUnderTest
     */
    public static void setProgramUnderTest(Program programUnderTest) {
        IntegrationFacade.PROGRAM_UNDER_TEST = programUnderTest;
    }
    private final ArrayListMultimap<Program, Long> conventionalExecutionCPUTimes = ArrayListMultimap.create();
    private final ArrayListMultimap<Program, Long> conventionalExecutionTimes = ArrayListMultimap.create();
    private final ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();

    /**
     *
     * @param mutantsToCombine
     * @return
     */
    public abstract Mutant combineMutants(List<Mutant> mutantsToCombine);

    /**
     *
     * @param mutantToExecute
     */
    public abstract void executeMutant(Mutant mutantToExecute);

    /**
     *
     * @param mutantsToExecute
     */
    public abstract void executeMutants(List<Mutant> mutantsToExecute);

    /**
     *
     * @param mutantsToExecute
     */
    public abstract void executeMutantsAgainstAllTestCases(List<Mutant> mutantsToExecute);

    /**
     *
     * @param operator
     * @return
     */
    public abstract List<Mutant> executeOperator(Operator operator);

    /**
     *
     * @param operators
     * @return
     */
    public abstract List<Mutant> executeOperators(List<Operator> operators);

    /**
     *
     * @return
     */
    public abstract List<Operator> getAllOperators();

    /**
     *
     * @return
     */
    public ListMultimap<Program, Long> getConventionalExecutionCPUTimes() {
        return Multimaps.unmodifiableListMultimap(this.conventionalExecutionCPUTimes);
    }

    /**
     *
     * @return
     */
    public ListMultimap<Program, Long> getConventionalExecutionTimes() {
        return Multimaps.unmodifiableListMultimap(this.conventionalExecutionTimes);
    }

    /**
     *
     * @return
     */
    public ListMultimap<Program, Mutant> getConventionalMutants() {
        return Multimaps.unmodifiableListMultimap(this.conventionalMutants);
    }

    /**
     *
     * @param program
     * @param repetitions
     */
    public void initializeConventionalStrategy(Program program, int repetitions) {
        if (!this.conventionalExecutionCPUTimes.containsKey(program)) {
            this.runConventionalStrategy(program, 1);
            this.conventionalExecutionCPUTimes.removeAll(program);
            this.conventionalExecutionTimes.removeAll(program);
            this.conventionalMutants.removeAll(program);
            this.runConventionalStrategy(program, repetitions);
            System.out.println("Number of generated mutants for " + program.getName() + ": " + this.conventionalMutants.get(program).size());
        }
    }

    /**
     *
     * @param programString
     * @return
     */
    public abstract Program instantiateProgram(String programString);

    /**
     *
     * @param programsStrings
     * @return
     */
    public List<Program> instantiatePrograms(List<String> programsStrings) {
        List<Program> programs = new ArrayList<>();
        for (String programName : programsStrings) {
            programs.add(this.instantiateProgram(programName));
        }
        return programs;
    }

    /**
     *
     * @param program
     * @param repetitions
     */
    protected void runConventionalStrategy(Program program, int repetitions) {
        IntegrationFacade.setProgramUnderTest(program);
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        List<Mutant> allMutants = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            long currentThreadCpuTime = threadBean.getCurrentThreadCpuTime();
            List<Operator> operators = this.getAllOperators();
            allMutants = this.executeOperators(operators);
            this.executeMutants(allMutants);
            currentThreadCpuTime = threadBean.getCurrentThreadCpuTime() - currentThreadCpuTime;
            stopwatch.stop();
            this.conventionalExecutionCPUTimes.put(program, currentThreadCpuTime);
            this.conventionalExecutionTimes.put(program, stopwatch.elapsed(TimeUnit.NANOSECONDS));
        }
        this.conventionalMutants.putAll(program, allMutants);
    }

    /**
     *
     */
    public abstract void tearDown();

}
