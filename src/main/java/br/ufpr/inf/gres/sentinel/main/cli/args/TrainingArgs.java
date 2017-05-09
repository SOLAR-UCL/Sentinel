package br.ufpr.inf.gres.sentinel.main.cli.args;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.main.cli.converter.SeparatorConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.common.collect.Lists;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
@Parameters(commandDescription = "If Sentinel must be executed for generating strategies (training).",
        commandNames = "train")
public class TrainingArgs {

    @Parameter(names = {"--help", "-h"}, description = "Shows this message.")
    public boolean help = false;

    @Parameter(names = {"--facade", "--integrationFacade", "--tool", "--mutationTool", "-m"},
            description = "The tool used to effectively generate the mutants. Available options: " + IntegrationFacadeFactory.PIT + ".")
    public String facade = "PIT";

    @Parameter(names = {"--workingDirectory", "-w"},
            description = "The working directory of Sentinel.",
            converter = SeparatorConverter.class)
    public String workingDirectory = System.getProperty("user.dir");

    @Parameter(names = {"--trainingDirectory", "-td"},
            description = "The directory (relative to the working directory) in which the training programs are located and where the training will be executed.",
            converter = SeparatorConverter.class)
    public String trainingDirectory = "training";

    @Parameter(names = {"--grammar", "--grammarFile", "-g"},
            description = "The grammar file path (relative to the working directory) used to interpret the strategies.",
            converter = SeparatorConverter.class)
    public String grammarFilePath = "grammars/default_grammar_no_homs.bnf";

    @Parameter(names = {"--trainingPrograms", "-tp"},
            description = "The names of the training programs to generate strategies. Sentinel will search for the programs in /path/to/training/directory/ according to the tool used for the mutant generation.",
            variableArity = true)
    public List<String> trainingPrograms = Lists.newArrayList("br.ufpr.inf.gres.TriTyp");

    @Parameter(names = {"--objectiveFunctions", "-of"},
            description = "The objective functions used to evolve the strategies. Available options are: " + ObjectiveFunction.AVERAGE_CPU_TIME + ", " + ObjectiveFunction.AVERAGE_SCORE + ", " + ObjectiveFunction.AVERAGE_QUANTITY + ".",
            variableArity = true)
    public List<String> objectiveFunctions = Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE);

    @Parameter(names = "--minLength", description = "Minimum length for the chromosome.")
    public Integer minLength = 15;

    @Parameter(names = "--maxLength", description = "Maximum length for the chromosome.")
    public Integer maxLength = 100;

    @Parameter(names = "--lowerBound", description = "Lower bound for each variable.")
    public Integer lowerVariableBound = 0;

    @Parameter(names = "--upperBound", description = "Upper bound for each variable.")
    public Integer upperVariableBound = 179;

    @Parameter(names = "--maxWraps", description = "Maximum chromosome wraps.")
    public Integer maxWraps = 10;

    @Parameter(names = "--trainingRuns",
            description = "Number of training runs for each training program in each training evaluation.")
    public Integer numberOfTrainingRuns = 5;

    @Parameter(names = "--maxEvaluations", description = "Maximum number of fitness evaluations.")
    public Integer maxEvaluations = 10000;

    @Parameter(names = "--populationSize", description = "Population size.")
    public Integer populationSize = 100;

    @Parameter(names = "--duplicateProbability", description = "Duplicate probability.")
    public Double duplicateProbability = 0.1D;

    @Parameter(names = "--pruneProbability", description = "Prune probability.")
    public Double pruneProbability = 0.1D;

    @Parameter(names = "--crossoverProbability", description = "Crossover probability.")
    public Double crossoverProbability = 1.0D;

    @Parameter(names = "--mutationProbability", description = "Mutation probability.")
    public Double mutationProbability = 0.01D;

    @Parameter(names = "--session",
            description = "Session name for the results. This is used later for analysis. Results from the same session are used to compute the overall quality of the algorithm used in this session. If no session is provided, then all the results are outputed to the training directory.",
            converter = SeparatorConverter.class)
    public String session = "Experiment";

    @Parameter(names = "--runNumber",
            description = "The number of the independent run being executed in a same session.")
    public int runNumber = 1;
}
