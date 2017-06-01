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

    public void notifyChange();

    public void notifyEvaluationStart(VariableLengthSolution<Integer> solution, int evaluationCount, Collection<ObjectiveFunction> objectiveFunctions);

    public void notifyStrategyCreationStart(VariableLengthSolution<Integer> solution);

    public void notifyStrategyCreationEnd(VariableLengthSolution<Integer> solution, Strategy strategy);

    public void notifyProgramChange(VariableLengthSolution<Integer> solution, Program testProgram);

    public void notifyRunStart(VariableLengthSolution<Integer> solution, int runNumber, int numberOfStrategyRuns);

    public void notifyStrategyExecutionStart(VariableLengthSolution<Integer> solution, Strategy strategy);

    public void notifyStrategyExecutionEnd(VariableLengthSolution<Integer> solution, Strategy strategy);

    public void notifyMutantsExecutionStart(VariableLengthSolution<Integer> solution, Collection<Mutant> mutants);

    public void notifyMutantsExecutionEnd(VariableLengthSolution<Integer> solution, Collection<Mutant> mutants);

    public void notifyRunEnd(VariableLengthSolution<Integer> solution, int runNumber, int numberOfStrategyRuns);

    public boolean shouldStopEvaluation();

    public void notifyObjectiveComputationStart(VariableLengthSolution<Integer> solution, Collection<ObjectiveFunction> objectiveFunctions);

    public void notifyComputeObjectives(VariableLengthSolution<Integer> solution, Collection<ObjectiveFunction> objectiveFunctions);

    public void notifyObjectiveComputationEnd(VariableLengthSolution<Integer> solution, Collection<ObjectiveFunction> objectiveFunctions);

    public void notifyException(VariableLengthSolution<Integer> solution, Exception ex);

    public void notifyEvaluationEnd(VariableLengthSolution<Integer> solution, int evaluationCount);

    public void notifyConsumedItems(VariableLengthSolution<Integer> solution, int count);

    public void notifyConventionalStrategyInitializationStart(VariableLengthSolution<Integer> solution, Program testProgram, int numberOfConventionalRuns);

    public void notifyConventionalStrategyInitializationEnd(VariableLengthSolution<Integer> solution, Program testProgram, int numberOfConventionalRuns);

}
