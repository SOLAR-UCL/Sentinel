package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Giovani
 */
public interface MutationStrategyGenerationObserver {

    public default void notifyConventionalStrategyInitializationStart(int numberOfConventionalRuns) {
    }

    public default void notifyEvaluationStart(VariableLengthSolution<Integer> solution, int evaluationCount, List<ObjectiveFunction> objectiveFunctions, List<Program> testPrograms, int numberOfStrategyRuns) {
    }

    public default void notifyInvalidSolution() {
    }

    public default void notifyStrategyCreated(Strategy strategy) {
    }

    public default void notifyProgramChange(Program testProgram) {
    }

    public default void notifyRunStart(int runNumber) {
    }

    public default void notifyStrategyExecutionStart() {
    }

    public default void notifyStrategyExecutionEnd() {
    }

    public default void notifyMutantsExecutionStart(Collection<Mutant> mutants) {
    }

    public default void notifyMutantsExecutionEnd() {
    }

    public default void notifyRunEnd() {
    }

    public default void prepareForFitnessEvaluation() {
    }

    public default boolean shouldStopEvaluation() {
        return false;
    }

    public default void notifyComputeObjectives() {
    }

    public default void notifyException(Exception ex) {
    }

    public default void notifyEvaluationEnd() {
    }

    public default void notifyConsumedItems(int count) {
    }

    public default void notifyConventionalStrategyInitializationEnd(boolean wasInitialized) {
    }

}
