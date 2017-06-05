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
    }

    /**
     *
     * @param facade
     * @param outputDirectory
     */
    public CachedFacade(IntegrationFacade facade, String outputDirectory) {
        super(facade.getInputDirectory());
        this.facade = facade;
        this.cache = new FacadeCache(null, outputDirectory);
    }

    /**
     *
     * @param inputDirectory
     */
    public CachedFacade(String inputDirectory) {
        super(inputDirectory);
        this.cache = new FacadeCache(inputDirectory, null);
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

                this.cache.recordOperatorCPUTime(program, operator, (long) operatorCpuTime);
                this.cache.recordOperatorExecutionTime(program, operator, (long) operatorExecutionTime);

                cpuTimeSum += operatorCpuTime;
                timeSum += operatorExecutionTime;
            }

            for (Mutant mutant : allMutants) {
                double mutantCpuTime = mutant.getCpuTime();
                double mutantExecutionTime = mutant.getExecutionTime();

                this.cache.recordMutantCPUTime(program, mutant, (long) mutantCpuTime);
                this.cache.recordMutantExecutionTime(program, mutant, (long) mutantExecutionTime);

                cpuTimeSum += mutantCpuTime;
                timeSum += mutantExecutionTime;
            }

            this.conventionalExecutionCPUTimes.put(program, cpuTimeSum);
            this.conventionalExecutionTimes.put(program, timeSum);
        }
        this.conventionalMutants.putAll(program, allMutants);
        cache.setCached(program);
        cache.writeCache();
    }

    @Override
    public void executeMutants(List<Mutant> mutantsToExecute, Program program) {
        if (facade != null && !cache.isCached(program)) {
            for (Mutant mutant : mutantsToExecute) {
                this.executeMutant(mutant, program);
            }
        }
    }

    @Override
    public void executeMutant(Mutant mutantToExecute, Program program) {
        if (facade != null && !cache.isCached(program)) {
            Stopwatch stopWatch = Stopwatch.createStarted();
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            long cpuTime = threadBean.getCurrentThreadCpuTime();

            this.facade.executeMutant(mutantToExecute, program);

            cpuTime = threadBean.getCurrentThreadCpuTime() - cpuTime;
            stopWatch.stop();

            mutantToExecute.setCpuTime(cpuTime);
            mutantToExecute.setExecutionTime(stopWatch.elapsed(TimeUnit.NANOSECONDS));
        }
    }

    @Override
    public List<Mutant> executeOperator(Operator operator, Program program) {
        if (facade != null && !cache.isCached(program)) {
            Stopwatch stopWatch = Stopwatch.createStarted();
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            long cpuTime = threadBean.getCurrentThreadCpuTime();

            List<Mutant> generatedMutants = this.facade.executeOperator(operator, program);

            cpuTime = threadBean.getCurrentThreadCpuTime() - cpuTime;
            stopWatch.stop();

            operator.setCpuTime(cpuTime);
            operator.setExecutionTime(stopWatch.elapsed(TimeUnit.NANOSECONDS));
            return generatedMutants;
        } else {
            return null;
        }
    }

    @Override
    public List<Mutant> executeOperators(List<Operator> operators, Program program) {
        if (facade != null && !cache.isCached(program)) {
            List<Mutant> allMutants = new ArrayList<>();
            for (Operator operator : operators) {
                allMutants.addAll(this.executeOperator(operator, program));
            }
            return allMutants;
        } else {
            return null;
        }
    }

    @Override
    public List<Operator> getAllOperators() {
        if (facade != null) {
            return this.facade.getAllOperators();
        } else {
            return null;
        }
    }

}
