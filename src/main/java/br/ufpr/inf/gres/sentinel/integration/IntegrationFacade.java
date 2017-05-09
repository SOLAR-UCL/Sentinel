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
    private final ArrayListMultimap<Program, Long> conventionalExecutionCPUTimes = ArrayListMultimap.create();
    private final ArrayListMultimap<Program, Long> conventionalExecutionTimes = ArrayListMultimap.create();
    private final ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();

    public static IntegrationFacade getIntegrationFacade() {
        return FACADE_INSTANCE;
    }

    public static void setIntegrationFacade(IntegrationFacade facade) {
        FACADE_INSTANCE = facade;
    }

    public static Program getProgramUnderTest() {
        return PROGRAM_UNDER_TEST;
    }

    public static void setProgramUnderTest(Program programUnderTest) {
        IntegrationFacade.PROGRAM_UNDER_TEST = programUnderTest;
    }

    public void initializeConventionalStrategy(Program program, int repetitions) {
        if (!conventionalExecutionCPUTimes.containsKey(program)) {
            runConventionalStrategy(program, 1);
            conventionalExecutionCPUTimes.removeAll(program);
            conventionalExecutionTimes.removeAll(program);
            conventionalMutants.removeAll(program);
            runConventionalStrategy(program, repetitions);
        }
    }

    protected void runConventionalStrategy(Program program, int repetitions) {
        IntegrationFacade.setProgramUnderTest(program);

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        List<Mutant> allMutants = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            long currentThreadCpuTime = threadBean.getCurrentThreadCpuTime();
            List<Operator> operators = getAllOperators();
            allMutants = executeOperators(operators);
            executeMutants(allMutants);
            currentThreadCpuTime = threadBean.getCurrentThreadCpuTime() - currentThreadCpuTime;
            stopwatch.stop();
            conventionalExecutionCPUTimes.put(program, currentThreadCpuTime);
            conventionalExecutionTimes.put(program, stopwatch.elapsed(TimeUnit.NANOSECONDS));
        }
        conventionalMutants.putAll(program, allMutants);
    }

    public ListMultimap<Program, Long> getConventionalExecutionCPUTimes() {
        return Multimaps.unmodifiableListMultimap(conventionalExecutionCPUTimes);
    }

    public ListMultimap<Program, Long> getConventionalExecutionTimes() {
        return Multimaps.unmodifiableListMultimap(conventionalExecutionTimes);
    }

    public ListMultimap<Program, Mutant> getConventionalMutants() {
        return Multimaps.unmodifiableListMultimap(conventionalMutants);
    }

    public List<Program> instantiatePrograms(List<String> programNames) {
        List<Program> programs = new ArrayList<>();
        for (String programName : programNames) {
            programs.add(instantiateProgram(programName));
        }
        return programs;
    }

    public abstract Program instantiateProgram(String programName);

    public abstract List<Operator> getAllOperators();

    public abstract List<Mutant> executeOperator(Operator operator);

    public abstract List<Mutant> executeOperators(List<Operator> operators);

    public abstract Mutant combineMutants(List<Mutant> mutantsToCombine);

    public abstract void executeMutant(Mutant mutantToExecute);

    public abstract void executeMutants(List<Mutant> mutantsToExecute);

    public abstract void executeMutantsAgainstAllTestCases(List<Mutant> mutantsToExecute);

    public abstract void tearDown();

}
