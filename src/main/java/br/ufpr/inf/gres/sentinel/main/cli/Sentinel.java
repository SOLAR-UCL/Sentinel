package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.GrammaticalEvolutionAlgorithm;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.crossover.impl.SinglePointVariableCrossover;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.duplicate.impl.SimpleDuplicateOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.mutation.impl.SimpleRandomVariableMutation;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl.SimplePruneOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.main.cli.args.MainArgs;
import br.ufpr.inf.gres.sentinel.main.cli.args.TrainingArgs;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

/**
 * Main class.
 *
 * @author Giovani Guizzo
 */
public class Sentinel {

    public static String[] RAW_ARGS;
    public static MainArgs MAIN_ARGS;
    public static TrainingArgs TRAINING_ARGS;

    public static void main(String[] args) throws Exception {
//        args = new String[]{"training", "--grammar", "default_no_homs"};
        RAW_ARGS = args;

        MAIN_ARGS = new MainArgs();
        TRAINING_ARGS = new TrainingArgs();

        JCommander commander = new JCommander(MAIN_ARGS);
        commander.addCommand(TRAINING_ARGS);
        try {
            commander.parse(args);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            usage(commander);
        }

        if (MAIN_ARGS.help || TRAINING_ARGS.help) {
            usage(commander);
        }

        JCommander command = commander.getCommands().get(commander.getParsedCommand());
        if (command == null) {
            System.out.println("Command not found. Here are the usage instructions for you.");
            usage(commander);
        }

        MAIN_ARGS = (MainArgs) command.getObjects().get(0);
        if (MAIN_ARGS instanceof TrainingArgs) {
            TRAINING_ARGS = (TrainingArgs) MAIN_ARGS;
            training();
        } else {
            execute();
        }
    }

    private static void training() throws Exception {
        IntegrationFacade facade
                = IntegrationFacadeFactory.createIntegrationFacade(TRAINING_ARGS.facade,
                        TRAINING_ARGS.workingDirectory
                        + File.separator
                        + TRAINING_ARGS.trainingDirectory);
        IntegrationFacade.setIntegrationFacade(facade);
        String grammarPath = getGrammarPath();
        List<Program> trainingPrograms = facade.instantiatePrograms(TRAINING_ARGS.trainingPrograms);

        MutationStrategyGenerationProblem problem
                = new MutationStrategyGenerationProblem(grammarPath,
                        TRAINING_ARGS.minLength,
                        TRAINING_ARGS.maxLength,
                        TRAINING_ARGS.lowerVariableBound,
                        TRAINING_ARGS.upperVariableBound,
                        TRAINING_ARGS.maxWraps,
                        TRAINING_ARGS.numberOfTrainingRuns,
                        trainingPrograms);

        GrammaticalEvolutionAlgorithm<Integer> algorithm
                = new GrammaticalEvolutionAlgorithm<>(problem,
                        TRAINING_ARGS.maxEvaluations,
                        TRAINING_ARGS.populationSize,
                        new SimpleDuplicateOperator<>(TRAINING_ARGS.duplicateProbability,
                                TRAINING_ARGS.maxLength),
                        new SimplePruneOperator<>(TRAINING_ARGS.pruneProbability,
                                TRAINING_ARGS.minLength),
                        new SinglePointVariableCrossover<>(TRAINING_ARGS.crossoverProbability),
                        new SimpleRandomVariableMutation(TRAINING_ARGS.mutationProbability,
                                TRAINING_ARGS.lowerVariableBound,
                                TRAINING_ARGS.upperVariableBound),
                        new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>()),
                        new SequentialSolutionListEvaluator<>());

        algorithm.run();
        List<VariableLengthSolution<Integer>> result = algorithm.getResult();
        printAlgorithmResults(result);
    }

    private static void execute() {
        //TODO implement it
    }

    private static String getGrammarPath() throws NoSuchFieldException {
        GrammarFiles.setWorkingDirectory(MAIN_ARGS.workingDirectory);

        Stream<String> grammarFileKeys
                = Stream.of(MainArgs.class.getField("grammarFile").getAnnotation(Parameter.class).names());
        Stream<String> grammarFilePathKeys
                = Stream.of(MainArgs.class.getField("grammarFilePath").getAnnotation(Parameter.class).names());

        String path;
        if (grammarFileKeys.anyMatch(key -> Stream.of(RAW_ARGS).anyMatch(key::equals))) {
            path = GrammarFiles.getGrammarPath(MAIN_ARGS.grammarFile);
        } else if (grammarFilePathKeys.anyMatch(key -> Stream.of(RAW_ARGS).anyMatch(key::equals))) {
            path = MAIN_ARGS.workingDirectory + File.separator + MAIN_ARGS.grammarFilePath;
        } else {
            path = GrammarFiles.getGrammarPath(MAIN_ARGS.grammarFile);
        }

        return path;
    }

    private static void printAlgorithmResults(List<VariableLengthSolution<Integer>> result) {
        result.sort(Comparator.comparingDouble(o -> o.getObjective(2)));
        final int[] i = {0};
        System.out.println("Variables -> Strategy description -> Time -> Quantity -> Score");
        result.forEach(solution -> {
            Object strategy = solution.getAttribute("Strategy");
            System.out.println("Solution #"
                    + (++i[0])
                    + "\n\tVariables: "
                    + solution.getVariablesCopy()
                    + "\n\tStrategy: "
                    + (strategy != null ? strategy : "Invalid Strategy")
                    + "\n\tObjectives: "
                    + solution.getObjective(0)
                    + "\t"
                    + solution.getObjective(1)
                    + "\t"
                    + solution.getObjective(2));
        });
    }

    private static void usage(JCommander commander) {
        commander.usage();
        System.exit(0);
    }

}
