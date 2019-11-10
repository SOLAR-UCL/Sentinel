package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.cache.observer.CacheFacadeObserver;
import com.google.common.base.Stopwatch;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Giovani
 */
public class CachedFacade extends IntegrationFacade {

    private static final Logger LOGGER = LogManager.getLogger(CachedFacade.class);

    private final IntegrationFacade facade;
    private final FacadeCache cache;
    private final Set<CacheFacadeObserver> observers;

    /**
     *
     * @param facade
     * @param cacheInputDirectory
     * @param cacheOutputDirectory
     */
    public CachedFacade(IntegrationFacade facade, String cacheInputDirectory, String cacheOutputDirectory) {
        super(facade.getInputDirectory());
        this.facade = facade;
        this.cache = new FacadeCache(cacheInputDirectory, cacheOutputDirectory);
        this.observers = new HashSet<>();
    }

    @Override
    public boolean initializeConventionalStrategy(Program program, int repetitions) {
        boolean wasCached = true;
        LOGGER.debug("Initializing program " + program.getName() + " for " + repetitions + " repetitions.");
        if (!this.cache.isCached(program)) {
            if (this.cache.getNumberOfRuns(program) < repetitions) {
                LOGGER.debug("Program is not cached. Starting execution.");
                LOGGER.trace("Starting first execution. It's gonna be discarded.");
                LinkedHashSet<Mutant> allMutants = this.facade.executeOperators(this.facade.getAllOperators(), program);
                this.facade.executeMutants(allMutants, program);
                this.conventionalExecutionCPUTimes.removeAll(program);
                this.conventionalExecutionTimes.removeAll(program);
                this.conventionalMutants.removeAll(program);

                LOGGER.trace("Starting actual execution.");
                this.runConventionalStrategy(program, repetitions);
                wasCached = false;
            }
            this.cache.setCached(program);
            this.cache.writeCache();
            LOGGER.debug("Program executed and successfully cached.");
        }
        if (!this.conventionalMutants.containsKey(program)) {
            LOGGER.debug("Program is cached. Returning cached values.");
            Collection<Mutant> allMutants = new LinkedHashSet<>();
            for (int i = 0; i < repetitions; i++) {
                long cpuTimeSum = 0;
                long timeSum = 0;

                Collection<Operator> operators = this.getAllOperators();

                allMutants = new LinkedHashSet<>();
                for (Operator operator : operators) {
                    allMutants.addAll(this.cache.retrieveOperatorExecutionInformation(program, operator));
                    cpuTimeSum += operator.getCpuTime();
                    timeSum += operator.getExecutionTime();
                }
                for (Mutant mutant : allMutants) {
                    this.cache.retrieveMutantExecutionInformation(program, mutant);
                    cpuTimeSum += mutant.getCpuTime();
                    timeSum += mutant.getExecutionTime();
                }
                this.conventionalExecutionCPUTimes.put(program, cpuTimeSum);
                this.conventionalExecutionTimes.put(program, timeSum);
            }
            this.conventionalMutants.putAll(program, allMutants);
        }
        return wasCached;
    }

    @Override
    protected void runConventionalStrategy(Program program, int repetitions) {
        LinkedHashSet<Mutant> allMutants = new LinkedHashSet<>();
        int numberOfRuns = this.cache.getNumberOfRuns(program);
        LOGGER.trace("Repetitions already finished: " + numberOfRuns + " / " + (((double) numberOfRuns) / (double) repetitions * 100) + "%");
        for (int i = numberOfRuns; i < repetitions; i++) {
            LOGGER.trace("Executing repetition " + i + ".");
            Collection<Operator> operators = this.getAllOperators();
            allMutants = this.executeOperators(operators, program);
            this.executeMutants(allMutants, program);

            LOGGER.trace("Repetition ended. " + (((double) i + 1) / (double) repetitions * 100) + "% complete.");
            this.cache.notifyRunEnded(program);
            this.cache.writeCache();
        }
        LOGGER.trace(allMutants.size() + " mutants generated.");
    }

    @Override
    public LinkedHashSet<Mutant> executeOperators(Collection<Operator> operators, Program program) {
        LinkedHashSet<Mutant> allMutants = new LinkedHashSet<>();
        if (operators != null) {
            for (Operator operator : operators) {
                LinkedHashSet<Mutant> generatedMutants = this.executeOperator(operator, program);
                allMutants.addAll(generatedMutants);
            }
        }
        return allMutants;
    }

    @Override
    public LinkedHashSet<Mutant> executeOperator(Operator operator, Program program) {
        if (operator != null) {
            if (!cache.isCached(program)) {
                Stopwatch stopWatch = Stopwatch.createStarted();
                OperatingSystemMXBean systemBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                long cpuTime = systemBean.getProcessCpuTime();

                LinkedHashSet<Mutant> generatedMutants = this.facade.executeOperator(operator, program);

                cpuTime = systemBean.getProcessCpuTime() - cpuTime;
                stopWatch.stop();
                long executionTime = stopWatch.elapsed(TimeUnit.NANOSECONDS);

                operator.setCpuTime(cpuTime);
                operator.setExecutionTime(executionTime);

                this.cache.recordOperatorCPUTime(program, operator, (long) cpuTime);
                this.cache.recordOperatorExecutionTime(program, operator, (long) executionTime);
                this.cache.recordOperatorGeneratedMutants(program, operator, generatedMutants);

                return generatedMutants;
            } else {
                LinkedHashSet<Mutant> mutants = this.cache.retrieveOperatorExecutionInformation(program, operator);
                notifyObservers(observer -> observer.notifyOperatorExecutionInformationRetrieved(operator));
                return mutants;
            }
        }
        return new LinkedHashSet<>();
    }

    @Override
    public void executeMutants(Collection<Mutant> mutantsToExecute, Program program) {
        if (mutantsToExecute != null) {
            for (Mutant mutant : mutantsToExecute) {
                this.executeMutant(mutant, program);
            }
        }
    }

    @Override
    public void executeMutant(Mutant mutantToExecute, Program program) {
        if (mutantToExecute != null) {
            if (!cache.isCached(program)) {
                Stopwatch stopWatch = Stopwatch.createStarted();
                OperatingSystemMXBean systembean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                long cpuTime = systembean.getProcessCpuTime();

                this.facade.executeMutant(mutantToExecute, program);

                cpuTime = systembean.getProcessCpuTime() - cpuTime;
                stopWatch.stop();
                long executionTime = stopWatch.elapsed(TimeUnit.NANOSECONDS);

                mutantToExecute.setCpuTime(cpuTime);
                mutantToExecute.setExecutionTime(executionTime);

                this.cache.recordMutantCPUTime(program, mutantToExecute, (long) cpuTime);
                this.cache.recordMutantExecutionTime(program, mutantToExecute, (long) executionTime);
                this.cache.recordMutantKillingTestCases(program, mutantToExecute, mutantToExecute.getKillingTestCases());
            } else {
                this.cache.retrieveMutantExecutionInformation(program, mutantToExecute);
                notifyObservers(observer -> observer.notifyMutantExecutionInformationRetrieved(mutantToExecute));
            }
        }
    }

    @Override
    public Collection<Operator> getAllOperators() {
        return this.facade.getAllOperators();
    }

    public void attachObserver(CacheFacadeObserver observer) {
        this.observers.add(observer);
    }

    public void attachAllObservers(Collection<CacheFacadeObserver> observers) {
        this.observers.addAll(observers);
    }

    public void dettachObserver(CacheFacadeObserver observer) {
        this.observers.remove(observer);
    }

    public void dettachAllObservers(Collection<CacheFacadeObserver> observers) {
        this.observers.removeAll(observers);
    }

    private void notifyObservers(Consumer<CacheFacadeObserver> notificationConsumer) {
        this.observers.stream().forEach(notificationConsumer);
    }

    private void clearCache() {
        this.cache.clearCache();
    }

    public boolean isCached(Program program) {
        return this.cache.isCached(program);
    }

}
