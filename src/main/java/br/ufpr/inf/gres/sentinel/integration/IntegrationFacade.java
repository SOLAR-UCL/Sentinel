package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Giovani Guizzo
 */
public abstract class IntegrationFacade {

    private static IntegrationFacade FACADE_INSTANCE;
    private static Program PROGRAM_UNDER_TEST;
    private final HashMap<Program, Long> conventionalExecutionTimes = new HashMap<>();
    private final HashMap<Program, Integer> conventionalQuantities = new HashMap<>();
    private final HashMap<Program, Double> conventionalScores = new HashMap<>();
    private final HashMap<Program, List<Mutant>> conventionalMutants = new HashMap<>();

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

    public long getConventionalMutationTime(Program program, TimeUnit timeUnit) {
        if (!conventionalExecutionTimes.containsKey(program)) {
            runConventionalStrategy(program, 10);
        }
        return timeUnit.convert(conventionalExecutionTimes.get(program), TimeUnit.NANOSECONDS);
    }

    public int getConventionalQuantityOfMutants(Program program) {
        if (!conventionalExecutionTimes.containsKey(program)) {
            runConventionalStrategy(program, 10);
        }
        return conventionalQuantities.get(program);
    }

    public double getConventionalMutationScore(Program program) {
        if (!conventionalExecutionTimes.containsKey(program)) {
            runConventionalStrategy(program, 10);
        }
        return conventionalScores.get(program);
    }

    public double getRelativeMutationScore(Program program, List<TestCase> testCases) {
        if (!conventionalMutants.containsKey(program)) {
            runConventionalStrategy(program, 10);
        }
        List<Mutant> mutants = new ArrayList<>(conventionalMutants.get(program));
        int originalSize = mutants.size();
        mutants
                = mutants.stream()
                        .filter(mutant -> mutant.getKillingTestCases().stream().anyMatch(testCases::contains))
                        .collect(Collectors.toList());
        return (double) mutants.size() / originalSize;
    }

    protected void runConventionalStrategy(Program program, int repetitions) {
        Program tempProgram = IntegrationFacade.getProgramUnderTest();
        IntegrationFacade.setProgramUnderTest(program);
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        List<Mutant> allMutants = new ArrayList<>();
        long currentThreadCpuTime = threadBean.getCurrentThreadCpuTime();
        for (int i = 0; i < repetitions; i++) {
            List<Operator> operators = getAllOperators();
            allMutants = executeOperators(operators);
            executeMutants(allMutants);
        }
        currentThreadCpuTime = threadBean.getCurrentThreadCpuTime() - currentThreadCpuTime;
        conventionalExecutionTimes.put(program, currentThreadCpuTime / repetitions);
        conventionalQuantities.put(program, allMutants.size());
        long numberOfDeadMutants = allMutants.stream().filter(Mutant::isDead).count();
        conventionalScores.put(program, (double) numberOfDeadMutants / (double) allMutants.size());
        conventionalMutants.put(program, allMutants.stream().filter(Mutant::isDead).collect(Collectors.toList()));
        IntegrationFacade.setProgramUnderTest(tempProgram);
    }

    public List<Program> instantiatePrograms(List<String> programNames) {
        List<Program> programs = new ArrayList<>();
        for (String programName : programNames) {
            programs.add(instantiateProgram(programName));
        }
        return programs;
    }

    public void initializeForProgram(Program program, int repetitions) {
        if (!conventionalExecutionTimes.containsKey(program)) {
            runConventionalStrategy(program, 1);
            conventionalExecutionTimes.remove(program);
            conventionalQuantities.remove(program);
            conventionalScores.remove(program);
            conventionalMutants.remove(program);
            runConventionalStrategy(program, repetitions);
        }
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
