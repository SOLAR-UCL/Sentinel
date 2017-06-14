package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.MutationStrategyGenerationObserver;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleFunction;

/**
 *
 * @author Giovani Guizzo
 */
public class ConstrainedObjectiveFunctionObserver implements MutationStrategyGenerationObserver {

    protected ThreadMXBean threadBean;

    protected VariableLengthSolution<Integer> solution;
    protected ArrayListMultimap<Program, Collection<Mutant>> allMutants;
    protected ArrayListMultimap<Program, Long> nanoCPUTimes;
    protected ArrayListMultimap<Program, Long> nanoTimes;
    protected Collection<ObjectiveFunction> objectiveFunctions;
    protected boolean isInvalid;

    protected Program program;

    protected long currentThreadCpuTime;
    protected Stopwatch stopwatch;
    protected Collection<Mutant> mutants;

    public ConstrainedObjectiveFunctionObserver() {
        this.threadBean = ManagementFactory.getThreadMXBean();
    }

    @Override
    public void notifyEvaluationStart(VariableLengthSolution<Integer> solution, int evaluationCount, List<ObjectiveFunction> objectiveFunctions, List<Program> testPrograms, int numberOfStrategyRuns) {
        this.solution = solution;
        this.solution.setAttribute("Evaluation Found", evaluationCount);
        this.nanoTimes = ArrayListMultimap.create();
        this.nanoCPUTimes = ArrayListMultimap.create();
        this.allMutants = ArrayListMultimap.create();
        this.isInvalid = false;
        this.objectiveFunctions = objectiveFunctions;
    }

    @Override
    public void notifyConsumedItems(int count) {
        this.solution.setAttribute("Consumed Items Count", count);
    }

    @Override
    public void notifyStrategyCreated(Strategy strategy) {
        this.solution.setAttribute("Strategy", strategy);
    }

    @Override
    public void notifyProgramChange(Program testProgram) {
        this.program = testProgram;
    }

    @Override
    public void notifyRunStart(int runNumber) {
        this.stopwatch = Stopwatch.createStarted();
        this.currentThreadCpuTime = this.threadBean.getCurrentThreadCpuTime();
    }

    @Override
    public void notifyMutantsExecutionStart(Collection<Mutant> mutants) {
        this.mutants = mutants;
    }

    @Override
    public void notifyRunEnd() {
        this.stopwatch.stop();
        this.currentThreadCpuTime = this.threadBean.getCurrentThreadCpuTime() - this.currentThreadCpuTime;

        this.nanoTimes.put(program, this.stopwatch.elapsed(TimeUnit.NANOSECONDS));
        this.nanoCPUTimes.put(program, this.currentThreadCpuTime);
        this.allMutants.put(program, mutants);
    }

    @Override
    public boolean shouldStopEvaluation() {
        return mutants == null || mutants.isEmpty();
    }

    @Override
    public void notifyInvalidSolution() {
        this.isInvalid = true;
    }

    @Override
    public void prepareForFitnessEvaluation() {
        IntegrationFacade integrationFacade = IntegrationFacade.getIntegrationFacade();
        this.solution.setAttribute("CPUTimes", nanoCPUTimes);
        this.solution.setAttribute("ConventionalCPUTimes", integrationFacade.getConventionalExecutionCPUTimes());
        this.solution.setAttribute("Times", nanoTimes);
        this.solution.setAttribute("ConventionalTimes", integrationFacade.getConventionalExecutionTimes());
        this.solution.setAttribute("Mutants", allMutants);
        this.solution.setAttribute("ConventionalMutants", integrationFacade.getConventionalMutants());
    }

    @Override
    public void notifyComputeObjectives() {
        ToDoubleFunction<ObjectiveFunction> compute = isInvalid
                ? objective -> objective.getWorstValue()
                : objective -> objective.computeFitness(solution);
        double[] objectiveValues = objectiveFunctions
                .stream()
                .mapToDouble(compute)
                .toArray();
        for (int i = 0; i < objectiveValues.length; i++) {
            double objectiveValue = objectiveValues[i];
            solution.setObjective(i, objectiveValue);
        }
    }

    @Override
    public void notifyException(Exception ex) {
        this.isInvalid = true;
        this.notifyComputeObjectives();
    }

    @Override
    public void notifyEvaluationEnd() {
        this.solution.setAttribute("CPUTimes", null);
        this.solution.setAttribute("ConventionalCPUTimes", null);
        this.solution.setAttribute("Times", null);
        this.solution.setAttribute("ConventionalTimes", null);
        this.solution.setAttribute("Mutants", null);
        this.solution.setAttribute("ConventionalMutants", null);

        this.solution = null;
        this.nanoTimes = null;
        this.nanoCPUTimes = null;
        this.allMutants = null;
        this.isInvalid = false;
        this.objectiveFunctions = null;
    }
}
