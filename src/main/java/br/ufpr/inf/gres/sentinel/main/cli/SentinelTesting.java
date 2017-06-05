package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.gson.GsonUtil;
import br.ufpr.inf.gres.sentinel.gson.ResultWrapper;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;
import br.ufpr.inf.gres.sentinel.main.cli.args.TestingArgs;
import com.beust.jcommander.internal.Lists;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ListMultimap;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.uma.jmetal.util.SolutionListUtils;

/**
 *
 * @author Giovani Guizzo
 */
public class SentinelTesting {

    /**
     *
     * @param testingArgs
     * @param rawArgs
     */
    public static void test(TestingArgs testingArgs, String[] rawArgs) throws IOException {
        IntegrationFacade facade = buildFacade(testingArgs);
        MutationStrategyGenerationProblem problem = buildProblem(facade, testingArgs);

        GsonUtil util = new GsonUtil();
        ListMultimap<String, ResultWrapper> resultsFromJson = util.getResultsFromJsonFiles(testingArgs.workingDirectory + File.separator + testingArgs.inputDirectory, testingArgs.inputFilesGlob);

        List<VariableLengthSolution<Integer>> nonDominatedSolutions = getNonDominatedSolutions(resultsFromJson.values());
        if (!resultsFromJson.isEmpty()) {
            runTest(problem, nonDominatedSolutions, testingArgs);
        }
    }

    private static void runTest(MutationStrategyGenerationProblem problem, List<VariableLengthSolution<Integer>> nonDominatedSolutions, TestingArgs testingArgs) throws IOException {
        HashMap<String, ResultWrapper> result = new HashMap<>();

        File outputDirectory = new File(testingArgs.workingDirectory + File.separator + testingArgs.outputDirectory);
        outputDirectory.mkdirs();

        ResultWrapper sentinelResults = runSentinelSolutions(nonDominatedSolutions, problem, testingArgs);
        result.put("Sentinel", sentinelResults);

        ResultWrapper randomMutantSamplingResults = runRandomMutantSampling(problem, testingArgs);
        result.put("Random Mutant Sampling", randomMutantSamplingResults);

        ResultWrapper randomOperatorSelectionResults = runRandomOperatorSelection(problem, testingArgs);
        result.put("Random Operator Selection", randomOperatorSelectionResults);

        ResultWrapper selectiveMutationResults = runSelectiveMutation(problem, testingArgs);
        result.put("Selective Mutation", selectiveMutationResults);

        storeResults(result, testingArgs);
    }

    private static ResultWrapper runSentinelSolutions(List<VariableLengthSolution<Integer>> nonDominatedSolutions, MutationStrategyGenerationProblem problem, TestingArgs testingArgs) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (VariableLengthSolution<Integer> nonDominatedSolution : nonDominatedSolutions) {
            problem.evaluate(nonDominatedSolution);
        }
        stopwatch.stop();

        nonDominatedSolutions.sort(Comparator.comparingDouble(o -> o.getObjective(1)));
        ResultWrapper resultWrapper = new ResultWrapper()
                .setExecutionTimeInMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
                .setResult(nonDominatedSolutions)
                .setGrammarFile(testingArgs.grammarFilePath)
                .setSession("Sentinel")
                .setObjectiveFunctions(testingArgs.objectiveFunctions)
                .setRunNumber(1);
        return resultWrapper;
    }

    private static ResultWrapper runRandomMutantSampling(MutationStrategyGenerationProblem problem, TestingArgs testingArgs) {
        List<VariableLengthSolution<Integer>> solutions = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            VariableLengthSolution<Integer> solution = problem.createSolution();
            solution.clearVariables();

            List<Integer> variables = Lists.newArrayList(
                    0, 2, 1, 0, 0, 1, 9, // Operator Execution
                    1, 0, 1, 0, 1, 1, i, // Mutant Selection
                    3 // Store Mutants
            );
            solution.addAllVariables(variables);
            solution.setAttribute("Name", "RMS_" + (i + 1) * 10 + "");
            solutions.add(solution);
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        for (VariableLengthSolution<Integer> solution : solutions) {
            problem.evaluate(solution);
        }
        stopwatch.stop();

        solutions.sort(Comparator.comparingDouble(o -> o.getObjective(1)));
        ResultWrapper resultWrapper = new ResultWrapper()
                .setExecutionTimeInMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
                .setResult(solutions)
                .setGrammarFile(testingArgs.grammarFilePath)
                .setSession("RandomMutantSampling")
                .setObjectiveFunctions(testingArgs.objectiveFunctions)
                .setRunNumber(1);
        return resultWrapper;
    }

    private static ResultWrapper runRandomOperatorSelection(MutationStrategyGenerationProblem problem, TestingArgs testingArgs) {
        List<VariableLengthSolution<Integer>> solutions = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            VariableLengthSolution<Integer> solution = problem.createSolution();
            solution.clearVariables();

            List<Integer> variables = Lists.newArrayList(
                    0, 2, 1, 0, 1, 1, i, // Operator Execution
                    3 // Store Mutants
            );
            solution.addAllVariables(variables);
            solution.setAttribute("Name", "ROS_" + (i + 1) * 10 + "");
            solutions.add(solution);
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        for (VariableLengthSolution<Integer> solution : solutions) {
            problem.evaluate(solution);
        }
        stopwatch.stop();

        solutions.sort(Comparator.comparingDouble(o -> o.getObjective(1)));
        ResultWrapper resultWrapper = new ResultWrapper()
                .setExecutionTimeInMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
                .setResult(solutions)
                .setGrammarFile(testingArgs.grammarFilePath)
                .setSession("RandomOperatorSelection")
                .setObjectiveFunctions(testingArgs.objectiveFunctions)
                .setRunNumber(1);
        return resultWrapper;
    }

    private static ResultWrapper runSelectiveMutation(MutationStrategyGenerationProblem problem, TestingArgs testingArgs) {
        try {
            List<VariableLengthSolution<Integer>> solutions = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                VariableLengthSolution<Integer> solution = problem.createSolution();
                solution.clearVariables();

                List<Integer> variables = Lists.newArrayList(
                        0, 1, 0, 0, 0, 0, 1, 1, i, // Operator and Mutants Discard
                        0, 2, 1, 0, 0, 1, 9, // Operator Execution
                        3 // Store Mutants
                );
                solution.addAllVariables(variables);
                solution.setAttribute("Name", "SM_" + (i + 1) + "");
                solutions.add(solution);
            }

            Field allOperatorsField = PITFacade.class.getDeclaredField("ALL_OPERATORS");
            allOperatorsField.setAccessible(true);
            ArrayList<Operator> allOperators = (ArrayList<Operator>) allOperatorsField.get(allOperatorsField);

            IntegrationFacade facade = IntegrationFacade.getIntegrationFacade();
            for (String testingProgram : testingArgs.testingPrograms) {
                Program program = facade.instantiateProgram(testingProgram);
                facade.initializeConventionalStrategy(program, testingArgs.numberOfTestingRuns);
                facade.executeOperators(allOperators, program);
            }

            Stopwatch stopwatch = Stopwatch.createStarted();
            for (VariableLengthSolution<Integer> solution : solutions) {
                problem.evaluate(solution);
            }
            stopwatch.stop();

            solutions.sort(Comparator.comparingDouble(o -> o.getObjective(1)));
            ResultWrapper resultWrapper = new ResultWrapper()
                    .setExecutionTimeInMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
                    .setResult(solutions)
                    .setGrammarFile(testingArgs.grammarFilePath)
                    .setSession("SelectiveMutation")
                    .setObjectiveFunctions(testingArgs.objectiveFunctions)
                    .setRunNumber(1);
            return resultWrapper;
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(SentinelTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(SentinelTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SentinelTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SentinelTesting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static List<VariableLengthSolution<Integer>> getNonDominatedSolutions(Collection<ResultWrapper> resultsFromJson) {
        return SolutionListUtils.getNondominatedSolutions(getSolutions(resultsFromJson));
    }

    private static List<VariableLengthSolution<Integer>> getSolutions(Collection<ResultWrapper> resultsFromJson) {
        Optional<Stream<VariableLengthSolution<Integer>>> reduce = resultsFromJson
                .stream()
                .map(result -> result.getResult().stream())
                .reduce((firstList, secondList) -> {
                    return Stream.concat(firstList, secondList);
                });
        List<VariableLengthSolution<Integer>> allSolutions = new ArrayList<>();
        if (reduce.isPresent()) {
            allSolutions = reduce.get().collect(Collectors.toList());
        }
        return allSolutions;
    }

    private static IntegrationFacade buildFacade(TestingArgs testingArgs) {
        IntegrationFacade facade
                = IntegrationFacadeFactory.createIntegrationFacade(testingArgs.facade,
                        testingArgs.workingDirectory
                        + File.separator
                        + testingArgs.inputDirectory);
        IntegrationFacade.setIntegrationFacade(facade);
        return facade;
    }

    public static MutationStrategyGenerationProblem buildProblem(IntegrationFacade facade, TestingArgs testingArgs) throws IOException {
        List<Program> trainingPrograms = facade.instantiatePrograms(testingArgs.testingPrograms);
        String grammarPath = testingArgs.workingDirectory
                + File.separator
                + testingArgs.grammarFilePath;
        MutationStrategyGenerationProblem problem
                = new MutationStrategyGenerationProblem(grammarPath,
                        0,
                        0,
                        0,
                        0,
                        testingArgs.maxWraps,
                        testingArgs.numberOfTestingRuns,
                        1,
                        trainingPrograms,
                        testingArgs.objectiveFunctions);
        return problem;
    }

    private static void storeResults(HashMap<String, ResultWrapper> result, TestingArgs testingArgs) throws IOException {
        for (Map.Entry<String, ResultWrapper> entry : result.entrySet()) {
            ResultWrapper value = entry.getValue();

            File outputFile = new File(testingArgs.workingDirectory
                    + File.separator
                    + testingArgs.outputDirectory
                    + File.separator
                    + value.getSession()
                    + File.separator
                    + "result.json");
            Files.createParentDirs(outputFile);
            GsonUtil gson = new GsonUtil();
            Files.write(gson.toJson(value), outputFile, Charset.defaultCharset());
        }
    }

}
