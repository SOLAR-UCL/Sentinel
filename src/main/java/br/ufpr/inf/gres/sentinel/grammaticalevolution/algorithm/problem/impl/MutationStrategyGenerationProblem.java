package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.iterator.CountingIterator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.list.SetUniqueList;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.VariableLengthIntegerProblem;

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
    private int numberOfObjectives;
    private int numberOfConstraints;

    public MutationStrategyGenerationProblem(String grammarFile,
            int minLength,
            int maxLength,
            int lowerVariableBound,
            int upperVariableBound,
            int maxWraps,
            int numberOfStrategyRuns,
            List<Program> testPrograms) throws IOException {
        this.strategyMapper = new StrategyMapper(grammarFile);
        this.lowerVariableBound = lowerVariableBound;
        this.upperVariableBound = upperVariableBound;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.maxWraps = maxWraps;
        this.numberOfStrategyRuns = numberOfStrategyRuns;
        this.testPrograms = testPrograms;
        this.evaluationCount = 0;
        this.numberOfObjectives = 2;
        this.numberOfConstraints = 0;
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
        return numberOfObjectives;
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

            Program tempProgram = IntegrationFacade.getProgramUnderTest();
            IntegrationFacade integrationFacade = IntegrationFacade.getIntegrationFacade();
            double numberOfMutants = 0;
            double score = 0;
            double elapsedNano = 0;
            programFor:
            for (Program testProgram : testPrograms) {
                IntegrationFacade.setProgramUnderTest(testProgram);
                integrationFacade.initializeForProgram(testProgram, numberOfStrategyRuns * 10);
                for (int i = 0; i < numberOfStrategyRuns; i++) {
                    ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
                    long currentThreadCpuTime = threadBean.getCurrentThreadCpuTime();
                    List<Mutant> mutants = strategy.run();
                    integrationFacade.executeMutants(mutants);
                    currentThreadCpuTime = threadBean.getCurrentThreadCpuTime() - currentThreadCpuTime;
                    if (mutants.isEmpty()) {
                        numberOfMutants = 0;
                        break programFor;
                    }
                    // Summing the elapsed time
                    long conventionalMutationTime = integrationFacade.getConventionalMutationTime(testProgram, TimeUnit.NANOSECONDS);
                    elapsedNano
                            += (double) currentThreadCpuTime
                            / (double) (conventionalMutationTime == 0 ? 1 : conventionalMutationTime);
                    // Summing the number of mutants
                    numberOfMutants
                            += (double) mutants.size() / (double) integrationFacade.getConventionalQuantityOfMutants(testProgram);
                    // Summing the score
                    score += integrationFacade.getRelativeMutationScore(testProgram,
                            mutants.stream()
                                    .map(Mutant::getKillingTestCases)
                                    .reduce((testCases, testCases2) -> SetUniqueList
                                    .setUniqueList(Lists.newArrayList(
                                            Iterables.concat(testCases,
                                                    testCases2))))
                                    .orElse(SetUniqueList.setUniqueList(new ArrayList<>())));
                }
            }
            IntegrationFacade.setProgramUnderTest(tempProgram);

            if (numberOfMutants > 0) {
                // Normalizing
                int normalizationFactor = testPrograms.size() * numberOfStrategyRuns;
                elapsedNano /= normalizationFactor;
                numberOfMutants /= normalizationFactor;
                score /= normalizationFactor;

                solution.setObjective(0, elapsedNano);
                System.out.println("Time: " + elapsedNano);
                solution.setObjective(1, score * -1);
                solution.setAttribute("Quantity", numberOfMutants);
                solution.setAttribute("Evaluation", evaluationCount);
            } else {
                setWorst(solution);
            }
        } catch (Exception ex) {
            System.out.println("Exception! Solution: " + solution.getVariablesCopy());
            System.out.println(ex.getMessage());
            // Invalid strategy. Probably discarded due to maximum wraps.
            setWorst(solution);
        }
    }

    private void setWorst(VariableLengthSolution<Integer> solution) {
        solution.setObjective(0, Double.MAX_VALUE);
        solution.setObjective(1, Double.MAX_VALUE);
        solution.setAttribute("Quantity", Double.MAX_VALUE);
    }

    private Strategy createStrategy(VariableLengthSolution<Integer> solution) {
        List<Integer> variables = solution.getVariablesCopy();
        CountingIterator<Integer> variablesIterator
                = new CountingIterator<>(Iterables.limit(Iterables.cycle(variables), variables.size() * (maxWraps + 1)).iterator());
        Strategy strategy = strategyMapper.interpret(variablesIterator);
        solution.setAttribute("Strategy", strategy);
        solution.setAttribute("Consumed Items Count", variablesIterator.getCount());
        return (Strategy) strategy;
    }

    @Override
    public VariableLengthSolution<Integer> createSolution() {
        return new DefaultVariableLengthIntegerSolution(this);
    }

}
