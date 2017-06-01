package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.VariableLengthIntegerProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.MutationStrategyGenerationObserver;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.iterator.CountingIterator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Giovani Guizzo
 */
public class MutationStrategyGenerationProblem implements VariableLengthIntegerProblem {

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
            List<ObjectiveFunction> objectiveFunctions) throws IOException {
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
        this.objectiveFunctions = Collections.unmodifiableList(new ArrayList<>(objectiveFunctions));
        this.observers = new HashSet<>();
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
        ++this.evaluationCount;
        notifyObservers(observer -> observer.notifyEvaluationStart(solution, evaluationCount, objectiveFunctions));
        try {
            notifyObservers(observer -> observer.notifyStrategyCreationStart(solution));
            Strategy strategy = this.createStrategy(solution);
            notifyObservers(observer -> observer.notifyStrategyCreationEnd(solution, strategy));
            IntegrationFacade integrationFacade = IntegrationFacade.getIntegrationFacade();
            boolean isInvalid;
            evaluationFor:
            for (Program testProgram : this.testPrograms) {
                notifyObservers(observer -> observer.notifyProgramChange(solution, testProgram));
                IntegrationFacade.setProgramUnderTest(testProgram);
                notifyObservers(observer -> observer.notifyConventionalStrategyInitializationStart(solution, testProgram, numberOfConventionalRuns));
                integrationFacade.initializeConventionalStrategy(testProgram, this.numberOfConventionalRuns);
                notifyObservers(observer -> observer.notifyConventionalStrategyInitializationEnd(solution, testProgram, numberOfConventionalRuns));
                for (int i = 0; i < this.numberOfStrategyRuns; i++) {
                    final int runNumber = i;
                    notifyObservers(observer -> observer.notifyRunStart(solution, runNumber, numberOfStrategyRuns));
                    notifyObservers(observer -> observer.notifyStrategyExecutionStart(solution, strategy));
                    List<Mutant> mutants = strategy.run();
                    notifyObservers(observer -> observer.notifyStrategyExecutionEnd(solution, strategy));
                    notifyObservers(observer -> observer.notifyMutantsExecutionStart(solution, mutants));
                    integrationFacade.executeMutants(mutants);
                    notifyObservers(observer -> observer.notifyMutantsExecutionEnd(solution, mutants));
                    notifyObservers(observer -> observer.notifyRunEnd(solution, runNumber, numberOfStrategyRuns));

                    isInvalid = observers.stream().anyMatch(observer -> observer.shouldStopEvaluation());
                    if (isInvalid) {
                        break evaluationFor;
                    }
                }
            }
            notifyObservers(observer -> observer.notifyObjectiveComputationStart(solution, objectiveFunctions));
            notifyObservers(observer -> observer.notifyComputeObjectives(solution, objectiveFunctions));
            notifyObservers(observer -> observer.notifyObjectiveComputationEnd(solution, objectiveFunctions));
        } catch (Exception ex) {
            // Invalid strategy. Probably discarded due to maximum wraps.
            notifyObservers(observer -> observer.notifyException(solution, ex));
        } finally {
            notifyObservers(observer -> observer.notifyEvaluationEnd(solution, evaluationCount));
        }
    }

    private Strategy createStrategy(VariableLengthSolution<Integer> solution) {
        List<Integer> variables = solution.getVariablesCopy();
        CountingIterator<Integer> variablesIterator = new CountingIterator<>(Iterables.limit(Iterables.cycle(variables), variables.size() * (this.maxWraps + 1)).iterator());
        Strategy strategy = this.strategyMapper.interpret(variablesIterator);
        notifyObservers(observer -> observer.notifyConsumedItems(solution, variablesIterator.getCount()));
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

    public void attachAllObservers(List<MutationStrategyGenerationObserver> observers) {
        this.observers.addAll(observers);
    }

    public void dettachObserver(MutationStrategyGenerationObserver observer) {
        this.observers.remove(observer);
    }

    private void notifyObservers(Consumer<MutationStrategyGenerationObserver> notificationConsumer) {
        this.observers.stream().forEach(notificationConsumer);
    }

}
