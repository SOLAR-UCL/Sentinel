package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.VariableLengthIntegerProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunctionFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.MutationStrategyGenerationObserver;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.impl.ConstrainedObjectiveFunctionObserver;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.iterator.CountingIterator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Giovani Guizzo
 */
public class MutationStrategyGenerationProblem implements VariableLengthIntegerProblem {

    private static final Logger LOGGER = LogManager.getLogger(MutationStrategyGenerationProblem.class);

    private int evaluationCount;
    private final int lowerVariableBound;
    private final int maxLength;
    private final int maxWraps;
    private final int minLength;
    private final int numberOfConstraints;
    private final int numberOfStrategyRuns;
    private final List<ObjectiveFunction> objectiveFunctions;
    private final StrategyMapper strategyMapper;
    private final List<Program> testPrograms;
    private final int upperVariableBound;
    private final int numberOfConventionalRuns;
    private final Set<MutationStrategyGenerationObserver> observers;

    private boolean haveProgramsBeenInitialized;

    /**
     *
     * @param grammarFile
     * @param minLength
     * @param maxLength
     * @param lowerVariableBound
     * @param upperVariableBound
     * @param maxWraps
     * @param numberOfStrategyRuns
     * @param numberOfConventionalRuns
     * @param testPrograms
     * @param objectiveFunctions
     * @throws IOException
     */
    public MutationStrategyGenerationProblem(String grammarFile,
            int minLength,
            int maxLength,
            int lowerVariableBound,
            int upperVariableBound,
            int maxWraps,
            int numberOfStrategyRuns,
            int numberOfConventionalRuns,
            List<Program> testPrograms,
            List<String> objectiveFunctions) throws IOException {
        this.strategyMapper = new StrategyMapper(grammarFile);
        this.lowerVariableBound = lowerVariableBound;
        this.upperVariableBound = upperVariableBound;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.maxWraps = maxWraps;
        this.numberOfStrategyRuns = numberOfStrategyRuns;
        this.numberOfConventionalRuns = numberOfConventionalRuns;
        this.testPrograms = testPrograms;
        this.evaluationCount = 0;
        this.numberOfConstraints = 0;
        this.objectiveFunctions = Collections.unmodifiableList(ObjectiveFunctionFactory.createObjectiveFunctions(objectiveFunctions));
        this.observers = new HashSet<>();
        this.observers.add(new ConstrainedObjectiveFunctionObserver());
        this.haveProgramsBeenInitialized = false;
        LOGGER.debug("Initializing problem.");
        LOGGER.trace("Grammar File: " + grammarFile);
        LOGGER.trace("Minimum Length: " + minLength);
        LOGGER.trace("Maximum Length: " + maxLength);
        LOGGER.trace("Lower Variable Bound: " + lowerVariableBound);
        LOGGER.trace("Upper Variable Bound: " + upperVariableBound);
        LOGGER.trace("Maximum Wraps: " + maxWraps);
        LOGGER.trace("Number of Strategy Runs: " + numberOfStrategyRuns);
        LOGGER.trace("Number of Conventional Runs: " + numberOfConventionalRuns);
        LOGGER.trace("Test Programs: " + testPrograms);
        LOGGER.trace("Objective Functions: " + objectiveFunctions);
    }

    /**
     *
     * @return
     */
    @Override
    public VariableLengthSolution<Integer> createSolution() {
        return new DefaultVariableLengthIntegerSolution(this);
    }

    /**
     *
     * @param solution
     */
    @Override
    public void evaluate(VariableLengthSolution<Integer> solution) {
        IntegrationFacade integrationFacade = IntegrationFacade.getIntegrationFacade();
        initializePrograms(integrationFacade);

        ++this.evaluationCount;
        LOGGER.info("Evaluation: " + this.evaluationCount);
        LOGGER.debug("Solution being evaluated: " + solution.getVariablesCopy());
        notifyObservers(observer -> observer.notifyEvaluationStart(solution, this.evaluationCount, this.objectiveFunctions, this.testPrograms, this.numberOfStrategyRuns));
        try {
            Strategy strategy = this.createStrategy(solution);
            notifyObservers(observer -> observer.notifyStrategyCreated(strategy));

            LOGGER.debug("Initializing evaluation runs for the solution.");
            boolean isInvalid;
            evaluationFor:
            for (Program testProgram : this.testPrograms) {
                LOGGER.debug("Evaluating the solution for program " + testProgram.getName());
                notifyObservers(observer -> observer.notifyProgramChange(testProgram));
                for (int i = 0; i < this.numberOfStrategyRuns; i++) {
                    final int runNumber = i;
                    LOGGER.debug("Evaluation run number " + i);
                    notifyObservers(observer -> observer.notifyRunStart(runNumber));

                    LOGGER.debug("Starting strategy execution.");
                    Stopwatch stopwatch = Stopwatch.createStarted();
                    notifyObservers(observer -> observer.notifyStrategyExecutionStart());
                    List<Mutant> mutants = strategy.run(testProgram);
                    notifyObservers(observer -> observer.notifyStrategyExecutionEnd());
                    stopwatch.stop();
                    LOGGER.debug("Strategy execution finished succesfully in " + DurationFormatUtils.formatDurationHMS(stopwatch.elapsed(TimeUnit.MILLISECONDS)));

                    LOGGER.debug("Starting mutants execution. " + mutants.size() + " will be executed.");
                    stopwatch.reset();
                    stopwatch.start();
                    notifyObservers(observer -> observer.notifyMutantsExecutionStart(mutants));
                    integrationFacade.executeMutants(mutants, testProgram);
                    notifyObservers(observer -> observer.notifyMutantsExecutionEnd());
                    stopwatch.stop();
                    LOGGER.debug("Mutants execution finished succesfully in " + DurationFormatUtils.formatDurationHMS(stopwatch.elapsed(TimeUnit.MILLISECONDS)));

                    notifyObservers(observer -> observer.notifyRunEnd());
                    LOGGER.trace("Checking validity of strategy.");
                    isInvalid = observers.stream().anyMatch(observer -> observer.shouldStopEvaluation());
                    if (isInvalid) {
                        LOGGER.trace("Strategy is invalid due to constraints. Stopping evaluation.");
                        notifyObservers(observer -> observer.notifyInvalidSolution());
                        break evaluationFor;
                    }
                    LOGGER.trace("Strategy is valid.");
                }
            }
            LOGGER.debug("Computing fitness.");
            notifyObservers(observer -> observer.prepareForFitnessEvaluation());
            notifyObservers(observer -> observer.notifyComputeObjectives());
        } catch (Exception ex) {
            // Invalid strategy. Probably discarded due to maximum wraps.
            LOGGER.debug("Exception! Solution is invalid. Not enough variables. Don't need to worry, though.");
            notifyObservers(observer -> observer.notifyException(ex));
        } finally {
            LOGGER.debug("Evaluation finished.");
            notifyObservers(observer -> observer.notifyEvaluationEnd());
        }
    }

    private void initializePrograms(IntegrationFacade integrationFacade) {
        if (!haveProgramsBeenInitialized) {
            LOGGER.info("Starting programs initialization.");
            Stopwatch stopwatch = Stopwatch.createStarted();
            for (Program testProgram : this.testPrograms) {
                Stopwatch stopwatchProgram = Stopwatch.createStarted();
                LOGGER.trace("Initializing program: " + testProgram.getName());
                notifyObservers(observer -> observer.notifyConventionalStrategyInitializationStart(this.numberOfConventionalRuns));
                boolean wasInitialized = integrationFacade.initializeConventionalStrategy(testProgram, this.numberOfConventionalRuns);
                notifyObservers(observer -> observer.notifyConventionalStrategyInitializationEnd(wasInitialized));
                stopwatchProgram.stop();
                LOGGER.trace("Program initialized successfully in " + DurationFormatUtils.formatDurationHMS(stopwatchProgram.elapsed(TimeUnit.MILLISECONDS)));
                LOGGER.trace("Generated mutants: " + integrationFacade.getConventionalMutants().get(testProgram).size());
                LOGGER.trace("Dead mutants: " + integrationFacade.getConventionalMutants().get(testProgram).stream().filter(Mutant::isDead).count());
                LOGGER.trace("Alive mutants: " + integrationFacade.getConventionalMutants().get(testProgram).stream().filter(Mutant::isAlive).count());
            }
            stopwatch.stop();
            LOGGER.info("Programs initialized successfully in " + DurationFormatUtils.formatDurationHMS(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
            this.haveProgramsBeenInitialized = true;
        }
    }

    private Strategy createStrategy(VariableLengthSolution<Integer> solution) {
        List<Integer> variables = solution.getVariablesCopy();
        CountingIterator<Integer> variablesIterator = new CountingIterator<>(Iterables.limit(Iterables.cycle(variables), variables.size() * (this.maxWraps + 1)).iterator());
        LOGGER.debug("Creating strategy.");
        Stopwatch stopwatch = Stopwatch.createStarted();
        Strategy strategy = this.strategyMapper.interpret(variablesIterator);
        stopwatch.stop();
        LOGGER.debug("Strategy created successfully in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        LOGGER.trace("Number of iterations for creating the strategy: " + variablesIterator.getCount());
        notifyObservers(observer -> observer.notifyConsumedItems(variablesIterator.getCount()));
        return strategy;
    }

    /**
     *
     * @return
     */
    @Override
    public int getLowerVariableBound() {
        return this.lowerVariableBound;
    }

    /**
     *
     * @return
     */
    @Override
    public int getMaxLength() {
        return this.maxLength;
    }

    /**
     *
     * @return
     */
    public int getMaxWraps() {
        return this.maxWraps;
    }

    /**
     *
     * @return
     */
    @Override
    public int getMinLength() {
        return this.minLength;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Mutation Strategy Generation Problem";
    }

    /**
     *
     * @return
     */
    @Override
    public int getNumberOfConstraints() {
        return this.numberOfConstraints;
    }

    /**
     *
     * @return
     */
    @Override
    public int getNumberOfObjectives() {
        return this.objectiveFunctions.size();
    }

    /**
     *
     * @return
     */
    @Override
    public int getNumberOfVariables() {
        return this.minLength;
    }

    /**
     *
     * @return
     */
    public List<ObjectiveFunction> getObjectiveFunctions() {
        return Collections.unmodifiableList(objectiveFunctions);
    }

    /**
     *
     * @return
     */
    @Override
    public int getUpperVariableBound() {
        return this.upperVariableBound;
    }

    public void attachObserver(MutationStrategyGenerationObserver observer) {
        this.observers.add(observer);
    }

    public void attachAllObservers(Collection<MutationStrategyGenerationObserver> observers) {
        this.observers.addAll(observers);
    }

    public void dettachObserver(MutationStrategyGenerationObserver observer) {
        this.observers.remove(observer);
    }

    public void dettachAllObservers(Collection<MutationStrategyGenerationObserver> observers) {
        this.observers.removeAll(observers);
    }

    public void dettachAllObservers() {
        this.observers.clear();
    }

    private void notifyObservers(Consumer<MutationStrategyGenerationObserver> notificationConsumer) {
        this.observers.stream().forEach(notificationConsumer);
    }

}
