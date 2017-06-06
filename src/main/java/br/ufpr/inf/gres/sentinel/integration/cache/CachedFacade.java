package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import com.google.common.base.Stopwatch;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Giovani
 */
public class CachedFacade extends IntegrationFacade {

    private IntegrationFacade facade;
    private FacadeCache cache;

    /**
     *
     * @param facade
     */
    public CachedFacade(IntegrationFacade facade) {
        super(facade.getInputDirectory());
        this.facade = facade;
        this.cache = new FacadeCache();
        this.cache.setAllOperators(facade.getAllOperators());
    }

    /**
     *
     * @param facade
     * @param inputDirectory
     * @param outputDirectory
     */
    public CachedFacade(IntegrationFacade facade, String inputDirectory, String outputDirectory) {
        super(inputDirectory);
        this.facade = facade;
        this.cache = new FacadeCache(inputDirectory, outputDirectory);
        this.cache.setAllOperators(facade.getAllOperators());
    }

    @Override
    public void initializeConventionalStrategy(Program program, int repetitions) {
        if (!this.cache.isCached(program)) {
            this.runConventionalStrategy(program, 1);
            this.conventionalExecutionCPUTimes.removeAll(program);
            this.conventionalExecutionTimes.removeAll(program);
            this.conventionalMutants.removeAll(program);
            this.cache.clearCache(program);

            this.runConventionalStrategy(program, repetitions);
            this.cache.setCached(program);
            this.cache.writeCache();
        }
    }

    @Override
    protected void runConventionalStrategy(Program program, int repetitions) {
        List<Mutant> allMutants = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            long cpuTimeSum = 0;
            long timeSum = 0;

            List<Operator> operators = this.getAllOperators();
            allMutants = this.executeOperators(operators, program);
            this.executeMutants(allMutants, program);

            for (Operator operator : operators) {
                double operatorCpuTime = operator.getCpuTime();
                double operatorExecutionTime = operator.getExecutionTime();

                cpuTimeSum += operatorCpuTime;
                timeSum += operatorExecutionTime;
            }

            for (Mutant mutant : allMutants) {
                double mutantCpuTime = mutant.getCpuTime();
                double mutantExecutionTime = mutant.getExecutionTime();

                cpuTimeSum += mutantCpuTime;
                timeSum += mutantExecutionTime;
            }

            this.conventionalExecutionCPUTimes.put(program, cpuTimeSum);
            this.conventionalExecutionTimes.put(program, timeSum);
        }
        this.conventionalMutants.putAll(program, allMutants);
    }

    @Override
    public List<Mutant> executeOperators(List<Operator> operators, Program program) {
        List<Mutant> allMutants = new ArrayList<>();
        for (Operator operator : operators) {
            List<Mutant> generatedMutants = this.executeOperator(operator, program);
            allMutants.addAll(generatedMutants);
        }
        return allMutants;
    }

    @Override
    public List<Mutant> executeOperator(Operator operator, Program program) {
        if (!cache.isCached(program)) {
            Stopwatch stopWatch = Stopwatch.createStarted();
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            long cpuTime = threadBean.getCurrentThreadCpuTime();

            List<Mutant> generatedMutants = this.facade.executeOperator(operator, program);

            cpuTime = threadBean.getCurrentThreadCpuTime() - cpuTime;
            stopWatch.stop();
            long executionTime = stopWatch.elapsed(TimeUnit.NANOSECONDS);

            operator.setCpuTime(cpuTime);
            operator.setExecutionTime(executionTime);

            this.cache.recordOperatorCPUTime(program, operator, (long) cpuTime);
            this.cache.recordOperatorExecutionTime(program, operator, (long) executionTime);
            this.cache.recordOperatorGeneratedMutants(program, operator, generatedMutants);

            return generatedMutants;
        } else {
            return this.cache.retrieveOperatorExecutionInformation(program, operator);
        }
    }

    @Override
    public void executeMutants(List<Mutant> mutantsToExecute, Program program) {
        for (Mutant mutant : mutantsToExecute) {
            this.executeMutant(mutant, program);
        }
    }

    @Override
    public void executeMutant(Mutant mutantToExecute, Program program) {
        if (!cache.isCached(program)) {
            Stopwatch stopWatch = Stopwatch.createStarted();
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            long cpuTime = threadBean.getCurrentThreadCpuTime();

            this.facade.executeMutant(mutantToExecute, program);

            cpuTime = threadBean.getCurrentThreadCpuTime() - cpuTime;
            stopWatch.stop();
            long executionTime = stopWatch.elapsed(TimeUnit.NANOSECONDS);

            mutantToExecute.setCpuTime(cpuTime);
            mutantToExecute.setExecutionTime(executionTime);

            this.cache.recordMutantCPUTime(program, mutantToExecute, (long) cpuTime);
            this.cache.recordMutantExecutionTime(program, mutantToExecute, (long) executionTime);
            this.cache.recordMutantKillingTestCases(program, mutantToExecute, mutantToExecute.getKillingTestCases());
        } else {
            this.cache.retrieveMutantExecutionInformation(program, mutantToExecute);
        }
    }

    @Override
    public List<Operator> getAllOperators() {
        return this.facade.getAllOperators();
    }

}
