package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.GrammaticalEvolutionAlgorithm;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.crossover.impl.SinglePointVariableCrossover;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.duplicate.impl.SimpleDuplicateOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.mutation.impl.SimpleRandomVariableMutation;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl.SimplePruneOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.main.cli.args.TrainingArgs;
import br.ufpr.inf.gres.sentinel.main.cli.gson.OperationSerializer;
import br.ufpr.inf.gres.sentinel.main.cli.gson.ResultWrapper;
import br.ufpr.inf.gres.sentinel.main.cli.gson.VariableLengthSolutionGsonSerializer;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
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

    public static void train(TrainingArgs trainingArgs, String[] rawArgs) throws Exception {
        IntegrationFacade facade
                = IntegrationFacadeFactory.createIntegrationFacade(trainingArgs.facade,
                        trainingArgs.workingDirectory
                        + File.separator
                        + trainingArgs.trainingDirectory);
        IntegrationFacade.setIntegrationFacade(facade);
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
                        trainingPrograms);

        GrammaticalEvolutionAlgorithm<Integer> algorithm
                = new GrammaticalEvolutionAlgorithm<>(problem,
                        trainingArgs.maxEvaluations,
                        trainingArgs.populationSize,
                        new SimpleDuplicateOperator<>(trainingArgs.duplicateProbability,
                                trainingArgs.maxLength),
                        new SimplePruneOperator<>(trainingArgs.pruneProbability,
                                trainingArgs.minLength),
                        new SinglePointVariableCrossover<>(trainingArgs.crossoverProbability),
                        new SimpleRandomVariableMutation(trainingArgs.mutationProbability,
                                trainingArgs.lowerVariableBound,
                                trainingArgs.upperVariableBound),
                        new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>()),
                        new SequentialSolutionListEvaluator<>());

        long timeMillis = System.currentTimeMillis();
        algorithm.run();
        timeMillis = System.currentTimeMillis() - timeMillis;

        List<VariableLengthSolution<Integer>> resultSolutions = algorithm.getResult();
        resultSolutions.sort(Comparator.comparingDouble(o -> o.getObjective(1)));

        ResultWrapper result = new ResultWrapper(timeMillis, resultSolutions);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonSerializer())
                .registerTypeAdapter(VariableLengthSolution.class, new VariableLengthSolutionGsonSerializer())
                .registerTypeAdapter(Operation.class, new OperationSerializer())
                .setPrettyPrinting()
                .create();
        Files.write(gson.toJson(result), new File(trainingArgs.workingDirectory
                + File.separator
                + trainingArgs.trainingDirectory
                + File.separator
                + trainingArgs.outputFile), Charset.defaultCharset());
    }

}
