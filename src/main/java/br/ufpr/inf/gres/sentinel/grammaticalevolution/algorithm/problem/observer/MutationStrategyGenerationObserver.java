package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import java.util.Collection;

/**
 *
 * @author Giovani
 */
public interface MutationStrategyGenerationObserver {

    public default void notifyEvaluationStart(VariableLengthSolution<Integer> solution, int evaluationCount, Collection<ObjectiveFunction> objectiveFunctions) {
    }

    public default void notifyStrategyCreationStart(VariableLengthSolution<Integer> solution) {
    }

    public default void notifyStrategyCreationEnd(VariableLengthSolution<Integer> solution, Strategy strategy) {
    }

    public default void notifyProgramChange(VariableLengthSolution<Integer> solution, Program testProgram) {
    }

    public default void notifyRunStart(VariableLengthSolution<Integer> solution, int runNumber, int numberOfStrategyRuns) {
    }

    public default void notifyStrategyExecutionStart(VariableLengthSolution<Integer> solution, Strategy strategy) {
    }

    public default void notifyStrategyExecutionEnd(VariableLengthSolution<Integer> solution, Strategy strategy) {
    }

    public default void notifyMutantsExecutionStart(VariableLengthSolution<Integer> solution, Collection<Mutant> mutants) {
    }

    public default void notifyMutantsExecutionEnd(VariableLengthSolution<Integer> solution, Collection<Mutant> mutants) {
    }

    public default void notifyRunEnd(VariableLengthSolution<Integer> solution, int runNumber, int numberOfStrategyRuns) {
    }

    public default boolean shouldStopEvaluation(VariableLengthSolution<Integer> solution) {
        return false;
    }

    public default void notifyObjectiveComputationStart(VariableLengthSolution<Integer> solution, Collection<ObjectiveFunction> objectiveFunctions) {
    }

    public default void notifyComputeObjectives(VariableLengthSolution<Integer> solution, Collection<ObjectiveFunction> objectiveFunctions) {
    }

    public default void notifyObjectiveComputationEnd(VariableLengthSolution<Integer> solution, Collection<ObjectiveFunction> objectiveFunctions) {
    }

    public default void notifyException(VariableLengthSolution<Integer> solution, Exception ex) {
    }

    public default void notifyEvaluationEnd(VariableLengthSolution<Integer> solution, int evaluationCount) {
    }

    public default void notifyConsumedItems(VariableLengthSolution<Integer> solution, int count) {
    }

    public default void notifyConventionalStrategyInitializationStart(VariableLengthSolution<Integer> solution, Program testProgram, int numberOfConventionalRuns) {
    }

    public default void notifyConventionalStrategyInitializationEnd(VariableLengthSolution<Integer> solution, Program testProgram, int numberOfConventionalRuns) {
    }

}
