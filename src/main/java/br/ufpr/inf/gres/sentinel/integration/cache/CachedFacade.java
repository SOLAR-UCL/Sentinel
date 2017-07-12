package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.cache.observer.CacheFacadeObserver;
import com.google.common.base.Stopwatch;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
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
     */
    public CachedFacade(IntegrationFacade facade) {
        super(facade.getInputDirectory());
        this.facade = facade;
        this.cache = new FacadeCache();
        this.observers = new HashSet<>();
    }

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
        LOGGER.debug("Initializing program " + program.getName() + " for " + repetitions + " repetitions.");
        if (!this.cache.isCached(program)) {
            LOGGER.debug("Program is not cached. Starting execution.");
            LOGGER.trace("Starting first execution. It's gonna be discarded.");
            LinkedHashSet<Mutant> allMutants = this.facade.executeOperators(this.facade.getAllOperators(), program);
            this.facade.executeMutants(allMutants, program);
            this.conventionalExecutionCPUTimes.removeAll(program);
            this.conventionalExecutionTimes.removeAll(program);
            this.conventionalMutants.removeAll(program);
            this.cache.clearCache(program);

            LOGGER.trace("Starting actual execution.");
            this.runConventionalStrategy(program, repetitions);
            this.cache.setCached(program);
            LOGGER.debug("Program executed and successfully cached.");
            try {
                LOGGER.debug("Preparing to write cache file.");
                boolean written = this.cache.writeCache();
                if (written) {
                    LOGGER.debug("Cache file written successfully.");
                } else {
                    LOGGER.debug("No cache file was written.");
                }
            } catch (IOException ex) {
                LOGGER.error("Could not write cache file. The exception is: " + ex.getMessage(), ex);
            }
            return true;
        } else {
            LOGGER.debug("Program is cached. Returning cached values.");
            if (!this.conventionalMutants.containsKey(program)) {
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
            return false;
        }
    }

    @Override
    protected void runConventionalStrategy(Program program, int repetitions) {
        LinkedHashSet<Mutant> allMutants = new LinkedHashSet<>();
        for (int i = 0; i < repetitions; i++) {
            LOGGER.trace("Executing repetition " + i + ".");
            long cpuTimeSum = 0;
            long timeSum = 0;

            Collection<Operator> operators = this.getAllOperators();
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
            LOGGER.trace("Repetition ended. " + (((double) i + 1) / (double) repetitions * 100) + "% complete.");
        }
        LOGGER.trace(allMutants.size() + " mutants generated.");
        this.conventionalMutants.putAll(program, allMutants);
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
                ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
                long cpuTime = threadBean.getCurrentThreadCpuTime();

                LinkedHashSet<Mutant> generatedMutants = this.facade.executeOperator(operator, program);

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

    @Override
    public String getJavaExecutablePath() {
        return facade.getJavaExecutablePath();
    }

    @Override
    public void setJavaExecutablePath(String javaExecutablePath) {
        facade.setJavaExecutablePath(javaExecutablePath);
    }

}
