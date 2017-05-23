package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.VariableLengthIntegerProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunctionFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.iterator.CountingIterator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Giovani Guizzo
 */
public class MutationStrategyGenerationProblem implements VariableLengthIntegerProblem {

    private int evaluationCount;
    private int lowerVariableBound;
    private int maxLength;
    private int maxWraps;
    private int minLength;
    private int numberOfConstraints;
    private final int numberOfStrategyRuns;
    private final List<ObjectiveFunction> objectiveFunctions;
    private final List<ObjectiveFunction> objectivesToStoreAsAttribute;
    private final StrategyMapper strategyMapper;
    private final List<Program> testPrograms;
    private int upperVariableBound;
    private final int conventionalStrategyRunMultiplier;

    /**
     *
     * @param grammarFile
     * @param minLength
     * @param maxLength
     * @param lowerVariableBound
     * @param upperVariableBound
     * @param maxWraps
     * @param numberOfStrategyRuns
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
            int conventionalStrategyRunMultiplier,
            List<Program> testPrograms,
            List<String> objectiveFunctions) throws IOException {
        this.strategyMapper = new StrategyMapper(grammarFile);
        this.lowerVariableBound = lowerVariableBound;
        this.upperVariableBound = upperVariableBound;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.maxWraps = maxWraps;
        this.numberOfStrategyRuns = numberOfStrategyRuns;
        this.testPrograms = testPrograms;
        this.evaluationCount = 0;
        this.numberOfConstraints = 0;
        this.objectiveFunctions = Collections.unmodifiableList(ObjectiveFunctionFactory.createObjectiveFunctions(objectiveFunctions));
        this.objectivesToStoreAsAttribute = ObjectiveFunctionFactory.createAllObjectiveFunctions();
        this.objectivesToStoreAsAttribute.removeAll(this.objectiveFunctions);
        this.conventionalStrategyRunMultiplier = conventionalStrategyRunMultiplier;
    }

    private void computeObjectiveValues(VariableLengthSolution<Integer> solution) {
        for (int i = 0; i < this.objectiveFunctions.size(); i++) {
            ObjectiveFunction objectiveFunction = this.objectiveFunctions.get(i);
            Double objectiveValue = objectiveFunction.computeFitness(solution);
            solution.setAttribute(objectiveFunction.getName(), objectiveValue);
            solution.setObjective(i, objectiveValue);
        }
        for (ObjectiveFunction objectiveFunction : this.objectivesToStoreAsAttribute) {
            solution.setAttribute(objectiveFunction.getName(), objectiveFunction.computeFitness(solution));
        }
    }

    /**
     *
     * @return
     */
    @Override
    public VariableLengthSolution<Integer> createSolution() {
        return new DefaultVariableLengthIntegerSolution(this);
    }

    private Strategy createStrategy(VariableLengthSolution<Integer> solution) {
        List<Integer> variables = solution.getVariablesCopy();
        CountingIterator<Integer> variablesIterator = new CountingIterator<>(Iterables.limit(Iterables.cycle(variables), variables.size() * (this.maxWraps + 1)).iterator());
        Strategy strategy = this.strategyMapper.interpret(variablesIterator);
        solution.setAttribute("Consumed Items Count", variablesIterator.getCount());
        return strategy;
    }

    /**
     *
     * @param solution
     */
    @Override
    public void evaluate(VariableLengthSolution<Integer> solution) {
        System.out.println("Evaluation: " + (++this.evaluationCount));
        try {
            this.setWorst(solution);
            Strategy strategy = this.createStrategy(solution);
            ArrayListMultimap<Program, Long> nanoTimes = ArrayListMultimap.create();
            ArrayListMultimap<Program, Long> nanoCPUTimes = ArrayListMultimap.create();
            ArrayListMultimap<Program, Collection<Mutant>> allMutants = ArrayListMultimap.create();
            IntegrationFacade integrationFacade = IntegrationFacade.getIntegrationFacade();
            boolean isValid = true;
            evaluationFor:
            for (Program testProgram : this.testPrograms) {
                IntegrationFacade.setProgramUnderTest(testProgram);
                integrationFacade.initializeConventionalStrategy(testProgram, this.numberOfStrategyRuns * this.conventionalStrategyRunMultiplier);
                for (int i = 0; i < this.numberOfStrategyRuns; i++) {
                    Stopwatch stopWatch = Stopwatch.createStarted();
                    ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
                    long currentThreadCpuTime = threadBean.getCurrentThreadCpuTime();
                    List<Mutant> mutants = strategy.run();
                    integrationFacade.executeMutants(mutants);
                    currentThreadCpuTime = threadBean.getCurrentThreadCpuTime() - currentThreadCpuTime;
                    stopWatch.stop();

                    if (mutants.isEmpty()) {
                        isValid = false;
                        break evaluationFor;
                    }

                    nanoTimes.put(testProgram, stopWatch.elapsed(TimeUnit.NANOSECONDS));
                    nanoCPUTimes.put(testProgram, currentThreadCpuTime);
                    allMutants.put(testProgram, mutants);
                }
            }
            solution.setAttribute("Strategy", strategy);
            solution.setAttribute("Evaluation Found", this.evaluationCount);
            if (isValid) {
                solution.setAttribute("CPUTimes", nanoCPUTimes);
                solution.setAttribute("ConventionalCPUTimes", integrationFacade.getConventionalExecutionCPUTimes());
                solution.setAttribute("Times", nanoTimes);
                solution.setAttribute("ConventionalTimes", integrationFacade.getConventionalExecutionTimes());
                solution.setAttribute("Mutants", allMutants);
                solution.setAttribute("ConventionalMutants", integrationFacade.getConventionalMutants());
                this.computeObjectiveValues(solution);
            }
        } catch (Exception ex) {
            // Invalid strategy. Probably discarded due to maximum wraps.
            System.out.println("Exception! Solution: " + solution.getVariablesCopy());
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * @param solution
     */
    public void evaluateNoConstraints(VariableLengthSolution<Integer> solution) {
        System.out.println("Evaluation: " + (++this.evaluationCount));
        try {
            this.setWorst(solution);
            Strategy strategy = this.createStrategy(solution);
            ArrayListMultimap<Program, Long> nanoTimes = ArrayListMultimap.create();
            ArrayListMultimap<Program, Long> nanoCPUTimes = ArrayListMultimap.create();
            ArrayListMultimap<Program, Collection<Mutant>> allMutants = ArrayListMultimap.create();
            IntegrationFacade integrationFacade = IntegrationFacade.getIntegrationFacade();
            for (Program testProgram : this.testPrograms) {
                IntegrationFacade.setProgramUnderTest(testProgram);
                integrationFacade.initializeConventionalStrategy(testProgram, this.numberOfStrategyRuns * this.conventionalStrategyRunMultiplier);
                for (int i = 0; i < this.numberOfStrategyRuns; i++) {
                    Stopwatch stopWatch = Stopwatch.createStarted();
                    ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
                    long currentThreadCpuTime = threadBean.getCurrentThreadCpuTime();
                    List<Mutant> mutants = strategy.run();
                    integrationFacade.executeMutants(mutants);
                    currentThreadCpuTime = threadBean.getCurrentThreadCpuTime() - currentThreadCpuTime;
                    stopWatch.stop();

                    nanoTimes.put(testProgram, stopWatch.elapsed(TimeUnit.NANOSECONDS));
                    nanoCPUTimes.put(testProgram, currentThreadCpuTime);
                    allMutants.put(testProgram, mutants);
                }
            }
            solution.setAttribute("Strategy", strategy);
            solution.setAttribute("Evaluation Found", this.evaluationCount);
            solution.setAttribute("CPUTimes", nanoCPUTimes);
            solution.setAttribute("ConventionalCPUTimes", integrationFacade.getConventionalExecutionCPUTimes());
            solution.setAttribute("Times", nanoTimes);
            solution.setAttribute("ConventionalTimes", integrationFacade.getConventionalExecutionTimes());
            solution.setAttribute("Mutants", allMutants);
            solution.setAttribute("ConventionalMutants", integrationFacade.getConventionalMutants());
            this.computeObjectiveValues(solution);
        } catch (Exception ex) {
            // Invalid strategy. Probably discarded due to maximum wraps.
            System.out.println("Exception! Solution: " + solution.getVariablesCopy());
            System.out.println(ex.getMessage());
        }
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

    private void setWorst(VariableLengthSolution<Integer> solution) {
        for (int i = 0; i < this.objectiveFunctions.size(); i++) {
            ObjectiveFunction objectiveFunction = this.objectiveFunctions.get(i);
            Double worstValue = objectiveFunction.getWorstValue();
            solution.setAttribute(objectiveFunction.getName(), worstValue);
            solution.setObjective(i, worstValue);
        }
        for (ObjectiveFunction objectiveFunction : this.objectivesToStoreAsAttribute) {
            solution.setAttribute(objectiveFunction.getName(), objectiveFunction.getWorstValue());
        }
    }

}
