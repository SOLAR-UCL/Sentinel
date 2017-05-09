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

    private final StrategyMapper strategyMapper;
    private final int numberOfStrategyRuns;
    private final List<Program> testPrograms;
    private int lowerVariableBound;
    private int upperVariableBound;
    private int minLength;
    private int maxLength;
    private int maxWraps;
    private int evaluationCount;
    private int numberOfConstraints;
    private final List<ObjectiveFunction> objectiveFunctions;
    private final List<ObjectiveFunction> objectivesToStoreAsAttribute;

    public MutationStrategyGenerationProblem(String grammarFile,
            int minLength,
            int maxLength,
            int lowerVariableBound,
            int upperVariableBound,
            int maxWraps,
            int numberOfStrategyRuns,
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
    }

    public int getMaxWraps() {
        return maxWraps;
    }

    public List<ObjectiveFunction> getObjectiveFunctions() {
        return objectiveFunctions;
    }

    @Override
    public int getUpperVariableBound() {
        return upperVariableBound;
    }

    @Override
    public int getLowerVariableBound() {
        return lowerVariableBound;
    }

    @Override
    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public int getMinLength() {
        return minLength;
    }

    @Override
    public int getNumberOfVariables() {
        return minLength;
    }

    @Override
    public int getNumberOfObjectives() {
        return objectiveFunctions.size();
    }

    @Override
    public int getNumberOfConstraints() {
        return numberOfConstraints;
    }

    @Override
    public String getName() {
        return "Mutation Strategy Generation Problem";
    }

    @Override
    public void evaluate(VariableLengthSolution<Integer> solution) {
        System.out.println("Evaluation: " + (++evaluationCount));
        try {
            setWorst(solution);
            Strategy strategy = createStrategy(solution);

            ArrayListMultimap<Program, Long> nanoTimes = ArrayListMultimap.create();
            ArrayListMultimap<Program, Long> nanoCPUTimes = ArrayListMultimap.create();
            ArrayListMultimap<Program, Collection<Mutant>> allMutants = ArrayListMultimap.create();

            IntegrationFacade integrationFacade = IntegrationFacade.getIntegrationFacade();
            for (Program testProgram : testPrograms) {
                IntegrationFacade.setProgramUnderTest(testProgram);
                integrationFacade.initializeConventionalStrategy(testProgram, numberOfStrategyRuns * 10);
                for (int i = 0; i < numberOfStrategyRuns; i++) {
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
            solution.setAttribute("Evaluation Found", evaluationCount);

            solution.setAttribute("CPUTimes", nanoCPUTimes);
            solution.setAttribute("ConventionalCPUTimes", integrationFacade.getConventionalExecutionCPUTimes());

            solution.setAttribute("Times", nanoTimes);
            solution.setAttribute("ConventionalTimes", integrationFacade.getConventionalExecutionTimes());

            solution.setAttribute("Mutants", allMutants);
            solution.setAttribute("ConventionalMutants", integrationFacade.getConventionalMutants());

            computeObjectiveValues(solution);
        } catch (Exception ex) {
            // Invalid strategy. Probably discarded due to maximum wraps.
            System.out.println("Exception! Solution: " + solution.getVariablesCopy());
            System.out.println(ex.getMessage());
        }
    }

    private void setWorst(VariableLengthSolution<Integer> solution) {
        for (int i = 0; i < objectiveFunctions.size(); i++) {
            ObjectiveFunction objectiveFunction = objectiveFunctions.get(i);
            Double worstValue = objectiveFunction.getWorstValue();
            solution.setAttribute(objectiveFunction.getName(), worstValue);
            solution.setObjective(i, worstValue);
        }
        for (ObjectiveFunction objectiveFunction : objectivesToStoreAsAttribute) {
            solution.setAttribute(objectiveFunction.getName(), objectiveFunction.getWorstValue());
        }
    }

    private void computeObjectiveValues(VariableLengthSolution<Integer> solution) {
        for (int i = 0; i < objectiveFunctions.size(); i++) {
            ObjectiveFunction objectiveFunction = objectiveFunctions.get(i);
            Double objectiveValue = objectiveFunction.computeFitness(solution);
            solution.setAttribute(objectiveFunction.getName(), objectiveValue);
            solution.setObjective(i, objectiveValue);
        }
        for (ObjectiveFunction objectiveFunction : objectivesToStoreAsAttribute) {
            solution.setAttribute(objectiveFunction.getName(), objectiveFunction.computeFitness(solution));
        }
    }

    private Strategy createStrategy(VariableLengthSolution<Integer> solution) {
        List<Integer> variables = solution.getVariablesCopy();
        CountingIterator<Integer> variablesIterator
                = new CountingIterator<>(Iterables.limit(Iterables.cycle(variables), variables.size() * (maxWraps + 1)).iterator());
        Strategy strategy = strategyMapper.interpret(variablesIterator);
        solution.setAttribute("Consumed Items Count", variablesIterator.getCount());
        return strategy;
    }

    @Override
    public VariableLengthSolution<Integer> createSolution() {
        return new DefaultVariableLengthIntegerSolution(this);
    }

}
