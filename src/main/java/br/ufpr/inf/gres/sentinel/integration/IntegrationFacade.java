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
            runConventionalStrategy(program);
        }
        return timeUnit.convert(conventionalExecutionTimes.get(program), TimeUnit.NANOSECONDS);
    }

    public int getConventionalQuantityOfMutants(Program program) {
        if (!conventionalExecutionTimes.containsKey(program)) {
            runConventionalStrategy(program);
        }
        return conventionalQuantities.get(program);
    }

    public double getConventionalMutationScore(Program program) {
        if (!conventionalExecutionTimes.containsKey(program)) {
            runConventionalStrategy(program);
        }
        return conventionalScores.get(program);
    }

    public double getRelativeMutationScore(Program program, List<TestCase> testCases) {
        if (!conventionalMutants.containsKey(program)) {
            runConventionalStrategy(program);
        }
        List<Mutant> mutants = new ArrayList<>(conventionalMutants.get(program));
        int originalSize = mutants.size();
        mutants
                = mutants.stream()
                        .filter(mutant -> mutant.getKillingTestCases().stream().anyMatch(testCases::contains))
                        .collect(Collectors.toList());
        return (double) mutants.size() / originalSize;
    }

    protected void runConventionalStrategy(Program program) {
        Program tempProgram = IntegrationFacade.getProgramUnderTest();
        IntegrationFacade.setProgramUnderTest(program);
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        long currentThreadCpuTime = threadBean.getCurrentThreadCpuTime();
        List<Operator> operators = getAllOperators();
        List<Mutant> allMutants = new ArrayList<>();
        for (Operator operator : operators) {
            allMutants.addAll(executeOperator(operator));
        }
        executeMutants(allMutants);
        currentThreadCpuTime = threadBean.getCurrentThreadCpuTime() - currentThreadCpuTime;
        conventionalExecutionTimes.put(program, currentThreadCpuTime);
        conventionalQuantities.put(program, allMutants.size());
        long numberOfDeadMutants = allMutants.stream().filter(Mutant::isDead).count();
        conventionalScores.put(program, (double) numberOfDeadMutants / (double) allMutants.size());
        conventionalMutants.put(program, allMutants.stream().filter(Mutant::isDead).collect(Collectors.toList()));
        IntegrationFacade.setProgramUnderTest(tempProgram);
    }

    public abstract List<Program> instantiatePrograms(List<String> programNames);

    public abstract Program instantiateProgram(String programName);

    public abstract List<Operator> getAllOperators();

    public abstract List<Mutant> executeOperator(Operator operator);

    public abstract Mutant combineMutants(List<Mutant> mutantsToCombine);

    public abstract void executeMutant(Mutant mutantToExecute);

    public abstract void executeMutants(List<Mutant> mutantsToExecute);

}
