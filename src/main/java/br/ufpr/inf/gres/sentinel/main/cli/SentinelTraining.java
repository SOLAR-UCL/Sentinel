package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.GrammaticalEvolutionAlgorithm;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.crossover.impl.SinglePointVariableCrossover;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.duplicate.impl.SimpleDuplicateOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.mutation.impl.SimpleRandomVariableMutation;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl.PruneToUsedOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.gson.GsonUtil;
import br.ufpr.inf.gres.sentinel.gson.ResultWrapper;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.main.cli.args.TrainingArgs;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

/**
 *
 * @author Giovani Guizzo
 */
public class SentinelTraining {

    /**
     *
     * @param trainingArgs
     * @param rawArgs
     * @throws Exception
     */
    public static void train(TrainingArgs trainingArgs, String[] rawArgs) throws Exception {
        IntegrationFacade facade = buildFacade(trainingArgs);
        MutationStrategyGenerationProblem problem = buildProblem(facade, trainingArgs);
        GrammaticalEvolutionAlgorithm<Integer> algorithm = buildAlgorithm(problem, trainingArgs);
        long timeMillis = runAlgorithm(algorithm);
        storeResults(algorithm, timeMillis, trainingArgs);
        facade.tearDown();
    }

    private static GrammaticalEvolutionAlgorithm<Integer> buildAlgorithm(MutationStrategyGenerationProblem problem, TrainingArgs trainingArgs) {
        GrammaticalEvolutionAlgorithm<Integer> algorithm
                = new GrammaticalEvolutionAlgorithm<>(problem,
                        trainingArgs.maxEvaluations,
                        trainingArgs.populationSize,
                        new SimpleDuplicateOperator<>(trainingArgs.duplicateProbability,
                                trainingArgs.maxLength),
                        new PruneToUsedOperator<>(trainingArgs.pruneProbability),
                        new SinglePointVariableCrossover<>(trainingArgs.crossoverProbability, trainingArgs.maxLength),
                        new SimpleRandomVariableMutation(trainingArgs.mutationProbability,
                                trainingArgs.lowerVariableBound,
                                trainingArgs.upperVariableBound),
                        new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>()),
                        new SequentialSolutionListEvaluator<>());
        return algorithm;
    }

    public static IntegrationFacade buildFacade(TrainingArgs trainingArgs) {
        IntegrationFacade facade
                = IntegrationFacadeFactory.createIntegrationFacade(trainingArgs.facade,
                        trainingArgs.workingDirectory
                        + File.separator
                        + trainingArgs.inputDirectory);
        IntegrationFacade.setIntegrationFacade(facade);
        return facade;
    }

    public static MutationStrategyGenerationProblem buildProblem(IntegrationFacade facade, TrainingArgs trainingArgs) throws IOException {
        List<Program> trainingPrograms = facade.instantiatePrograms(trainingArgs.trainingPrograms);
        String grammarPath = trainingArgs.workingDirectory
                + File.separator
                + trainingArgs.grammarFilePath;
        MutationStrategyGenerationProblem problem
                = new MutationStrategyGenerationProblem(grammarPath,
                        trainingArgs.minLength,
                        trainingArgs.maxLength,
                        trainingArgs.lowerVariableBound,
                        trainingArgs.upperVariableBound,
                        trainingArgs.maxWraps,
                        trainingArgs.numberOfTrainingRuns,
                        trainingArgs.numberOfConventionalRuns,
                        trainingPrograms,
                        trainingArgs.objectiveFunctions);
        return problem;
    }

    private static long runAlgorithm(GrammaticalEvolutionAlgorithm<Integer> algorithm) {
        long timeMillis = System.currentTimeMillis();
        algorithm.run();
        timeMillis = System.currentTimeMillis() - timeMillis;
        return timeMillis;
    }

    private static void storeResults(GrammaticalEvolutionAlgorithm<Integer> algorithm, long timeMillis, TrainingArgs trainingArgs) throws IOException {
        List<VariableLengthSolution<Integer>> resultSolutions = algorithm.getResult();
        resultSolutions.sort(Comparator.comparingDouble(o -> o.getObjective(1)));

        ResultWrapper result = new ResultWrapper()
                .setExecutionTimeInMillis(timeMillis)
                .setResult(resultSolutions)
                .setGrammarFile(trainingArgs.grammarFilePath)
                .setSession(trainingArgs.session)
                .setObjectiveFunctions(trainingArgs.objectiveFunctions)
                .setRunNumber(trainingArgs.runNumber);

        File outputFile = new File(trainingArgs.workingDirectory
                + File.separator
                + trainingArgs.outputDirectory
                + File.separator
                + trainingArgs.session
                + File.separator
                + "result_" + trainingArgs.runNumber + ".json");
        Files.createParentDirs(outputFile);
        GsonUtil gson = new GsonUtil(trainingArgs);
        Files.write(gson.toJson(result), outputFile, Charset.defaultCharset());
    }

}
