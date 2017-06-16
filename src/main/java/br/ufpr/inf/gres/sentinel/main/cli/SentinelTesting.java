package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.impl.UnconstrainedObjectiveFunctionObserver;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.gson.GsonUtil;
import br.ufpr.inf.gres.sentinel.gson.ResultWrapper;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;
import br.ufpr.inf.gres.sentinel.main.cli.args.TestingArgs;
import com.beust.jcommander.internal.Lists;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.uma.jmetal.util.SolutionListUtils;

/**
 *
 * @author Giovani Guizzo
 */
public class SentinelTesting {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(SentinelTesting.class);

    /**
     *
     * @param testingArgs
     * @param rawArgs
     */
    public static void test(TestingArgs testingArgs, String[] rawArgs) {
        try {
            if (testingArgs.verbose) {
                LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
                Configuration config = ctx.getConfiguration();
                LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
                loggerConfig.setLevel(Level.ALL);
                ctx.updateLoggers();
            }

            Stopwatch stopwatch = Stopwatch.createStarted();
            LOGGER.info("Starting test phase.");
            IntegrationFacade facade = buildFacade(testingArgs);
            MutationStrategyGenerationProblem problem = buildProblem(facade, testingArgs);
            problem.dettachAllObservers();
            problem.attachObserver(new UnconstrainedObjectiveFunctionObserver());

            String inputDir = testingArgs.workingDirectory + File.separator + testingArgs.inputDirectory;
            LOGGER.debug("Reading result files from " + inputDir);
            GsonUtil util = new GsonUtil(problem);
            ListMultimap<String, ResultWrapper> resultsFromJson = util.getResultsFromJsonFiles(inputDir, testingArgs.inputFilesGlob);

            LOGGER.info("Files read. Found " + resultsFromJson.entries().size() + " training files.");
            if (!resultsFromJson.isEmpty()) {
                LOGGER.debug("Starting testing runs.");
                runTest(problem, resultsFromJson, testingArgs);
            }
            stopwatch.stop();
            LOGGER.info("Testing finished in " + DurationFormatUtils.formatDurationHMS(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        } catch (IOException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException ex) {
            LOGGER.fatal("Something went horribly wrong. Exception is: " + ex.getMessage(), ex);
        }
    }

    private static void runTest(MutationStrategyGenerationProblem problem, ListMultimap<String, ResultWrapper> resultsFromJson, TestingArgs testingArgs) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        ListMultimap<String, ResultWrapper> result = ArrayListMultimap.create();

        File outputDirectory = new File(testingArgs.workingDirectory + File.separator + testingArgs.outputDirectory);
        outputDirectory.mkdirs();

        LOGGER.info("Starting Sentinel executions. There are " + resultsFromJson.values().stream().mapToInt((resultWrapper) -> resultWrapper.getResult().size()).sum() + " Sentinel startegies to run.");
        for (ResultWrapper trainingResult : resultsFromJson.values()) {
            LOGGER.debug("Running " + trainingResult.getSession() + " " + trainingResult.getRunNumber());
            ResultWrapper testResult = runSentinelSolutions(trainingResult, problem, testingArgs);
            result.put(testResult.getSession(), testResult);
            LOGGER.debug("Execution finished in " + DurationFormatUtils.formatDurationHMS(testResult.getExecutionTimeInMillis()));
        }
//        Set<String> sessions = resultsFromJson.keySet();
//        LOGGER.info("Found " + sessions.size() + " sessions: " + sessions.toString());
//        for (String session : sessions) {
//            List<VariableLengthSolution<Integer>> solutions = new ArrayList<>();
//            resultsFromJson.get(session).stream().forEach(tempWrapper -> solutions.addAll(tempWrapper.getResult()));
//            List<VariableLengthSolution<Integer>> nonDominatedSolutions = SolutionListUtils.getNondominatedSolutions(solutions);
//
//            ResultWrapper trainingResult = resultsFromJson.get(session).stream().findFirst().get();
//            trainingResult.setRunNumber(1);
//            trainingResult.setResult(nonDominatedSolutions);
//            LOGGER.debug("Running " + trainingResult.getSession() + " " + trainingResult.getRunNumber());
//            LOGGER.debug("Running " + nonDominatedSolutions.size() + " strategies.");
//            ResultWrapper testResult = runSentinelSolutions(trainingResult, problem, testingArgs);
//            result.put(testResult.getSession(), testResult);
//            LOGGER.debug("Execution finished in " + DurationFormatUtils.formatDurationHMS(testResult.getExecutionTimeInMillis()));
//        }

        LOGGER.info("Starting Random Mutant Sampling executions.");
        ResultWrapper randomMutantSamplingResults = runRandomMutantSampling(problem, testingArgs);
        result.put("Random Mutant Sampling", randomMutantSamplingResults);
        LOGGER.debug("Execution finished in " + DurationFormatUtils.formatDurationHMS(randomMutantSamplingResults.getExecutionTimeInMillis()));

        LOGGER.info("Starting Random Operator Selection executions.");
        ResultWrapper randomOperatorSelectionResults = runRandomOperatorSelection(problem, testingArgs);
        result.put("Random Operator Selection", randomOperatorSelectionResults);
        LOGGER.debug("Execution finished in " + DurationFormatUtils.formatDurationHMS(randomOperatorSelectionResults.getExecutionTimeInMillis()));

        LOGGER.info("Starting Selective Mutation executions.");
        ResultWrapper selectiveMutationResults = runSelectiveMutation(problem, testingArgs);
        result.put("Selective Mutation", selectiveMutationResults);
        LOGGER.debug("Execution finished in " + DurationFormatUtils.formatDurationHMS(selectiveMutationResults.getExecutionTimeInMillis()));

        storeResults(result, testingArgs);
    }

    private static ResultWrapper runSentinelSolutions(ResultWrapper trainingResult, MutationStrategyGenerationProblem problem, TestingArgs testingArgs) {
        List<VariableLengthSolution<Integer>> nonDominatedSolutions = SolutionListUtils.getNondominatedSolutions(trainingResult.getResult());

        Stopwatch stopwatch = Stopwatch.createStarted();
        for (VariableLengthSolution<Integer> nonDominatedSolution : nonDominatedSolutions) {
            problem.evaluate(nonDominatedSolution);
        }
        stopwatch.stop();

        nonDominatedSolutions.sort(Comparator.comparingDouble(o -> o.getObjective(1)));
        ResultWrapper testingResult = new ResultWrapper()
                .setExecutionTimeInMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
                .setResult(nonDominatedSolutions)
                .setGrammarFile(testingArgs.grammarFilePath)
                .setSession(trainingResult.getSession())
                .setObjectiveFunctions(testingArgs.objectiveFunctions)
                .setRunNumber(trainingResult.getRunNumber());
        return testingResult;
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

    private static ResultWrapper runSelectiveMutation(MutationStrategyGenerationProblem problem, TestingArgs testingArgs) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
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
                        testingArgs.numberOfTestingRuns,
                        trainingPrograms,
                        testingArgs.objectiveFunctions);
        return problem;
    }

    private static void storeResults(ListMultimap<String, ResultWrapper> result, TestingArgs testingArgs) throws IOException {
        for (ResultWrapper testingResult : result.values()) {

            File outputFile = new File(testingArgs.workingDirectory
                    + File.separator
                    + testingArgs.outputDirectory
                    + File.separator
                    + testingResult.getSession()
                    + File.separator
                    + "result_" + testingResult.getRunNumber() + ".json");
            Files.createParentDirs(outputFile);
            GsonUtil gson = new GsonUtil();
            Files.write(gson.toJson(testingResult), outputFile, Charset.defaultCharset());
        }
    }

}
