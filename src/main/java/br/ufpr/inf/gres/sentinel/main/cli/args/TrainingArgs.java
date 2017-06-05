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

    /**
     *
     */
    @Parameter(names = "--crossoverProbability", description = "Crossover probability.")
    public Double crossoverProbability = 1.0D;
    /**
     *
     */
    @Parameter(names = "--duplicateProbability", description = "Duplicate probability.")
    public Double duplicateProbability = 0.1D;

    /**
     *
     */
    @Parameter(names = {"--facade", "--integrationFacade", "--tool", "--mutationTool", "-m"},
            description = "The tool used to effectively generate the mutants. Available options: " + IntegrationFacadeFactory.PIT + ".")
    public String facade = "PIT";

    /**
     *
     */
    @Parameter(names = {"--grammar", "--grammarFile", "-g"},
            description = "The grammar file path (relative to the working directory) used to interpret the strategies.",
            converter = SeparatorConverter.class)
    public String grammarFilePath = "grammars/default_grammar.bnf";
    /**
     *
     */
    @Parameter(names = {"--help", "-h"}, description = "Shows this message.")
    public boolean help = false;
    /**
     *
     */
    @Parameter(names = "--lowerBound", description = "Lower bound for each variable.")
    public Integer lowerVariableBound = 0;
    /**
     *
     */
    @Parameter(names = "--maxEvaluations", description = "Maximum number of fitness evaluations.")
    public Integer maxEvaluations = 10000;

    /**
     *
     */
    @Parameter(names = "--maxLength", description = "Maximum length for the chromosome.")
    public Integer maxLength = 100;

    /**
     *
     */
    @Parameter(names = "--maxWraps", description = "Maximum chromosome wraps.")
    public Integer maxWraps = 10;
    /**
     *
     */
    @Parameter(names = "--minLength", description = "Minimum length for the chromosome.")
    public Integer minLength = 15;
    /**
     *
     */
    @Parameter(names = "--mutationProbability", description = "Mutation probability.")
    public Double mutationProbability = 0.01D;

    /**
     *
     */
    @Parameter(names = "--trainingRuns",
            description = "Number of training runs for each training program in each training evaluation.")
    public Integer numberOfTrainingRuns = 5;

    /**
     *
     */
    @Parameter(names = {"--numberOfConventionalRuns"},
            description = "Number of times that the conventional strategy shall be executed.")
    public Integer numberOfConventionalRuns = 5;

    /**
     *
     */
    @Parameter(names = {"--objectiveFunctions", "-of"},
            description = "The objective functions used to evolve the strategies. Available options are: " + ObjectiveFunction.AVERAGE_CPU_TIME + ", " + ObjectiveFunction.AVERAGE_SCORE + ", " + ObjectiveFunction.AVERAGE_QUANTITY + ".",
            variableArity = true)
    public List<String> objectiveFunctions = Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE);

    /**
     *
     */
    @Parameter(names = "--populationSize", description = "Population size.")
    public Integer populationSize = 100;

    /**
     *
     */
    @Parameter(names = "--pruneProbability", description = "Prune probability.")
    public Double pruneProbability = 0.1D;
    /**
     *
     */
    @Parameter(names = "--runNumber",
            description = "The number of the independent run being executed in a same session.")
    public int runNumber = 1;

    /**
     *
     */
    @Parameter(names = "--session",
            description = "Session name for the results. This is used later for analysis. Results from the same session are used to compute the overall quality of the algorithm used in this session. If no session is provided, then all the results are outputed to the output directory.",
            converter = SeparatorConverter.class)
    public String session = "Experiment";
    /**
     *
     */
    @Parameter(names = {"--outputDirectory", "-od"},
            description = "The directory (relative to the working directory) in which the training results will be outputed.",
            converter = SeparatorConverter.class)
    public String outputDirectory = "training";

    /**
     *
     */
    @Parameter(names = {"--inputDirectory", "-id"},
            description = "The directory (relative to the working directory) in which the programs for the training are located.",
            converter = SeparatorConverter.class)
    public String inputDirectory = "training";

    /**
     *
     */
    @Parameter(names = "--upperBound", description = "Upper bound for each variable.")
    public Integer upperVariableBound = 179;
    /**
     *
     */
    @Parameter(names = {"--workingDirectory", "-w"},
            description = "The working directory of Sentinel.",
            converter = SeparatorConverter.class)
    public String workingDirectory = System.getProperty("user.dir");

    /**
     *
     */
    @Parameter(names = {"--trainingPrograms", "-tp"},
            description = "Training programs for the training phase. Each String represents one training program with the following pattern: "
            + "<name>;<sourceDir>;<targetClassesGlob>;<targetTestsGlob>;<classpathItems>"
            + ". name is the program's name (for result purposes), sourceDir represents the source directory for the program, "
            + "targetClassesGlob includes classes to be mutated, targetTestsGlob includes classes to test the software, and classpathItems is "
            + "used to add dependencies of the program in the Java classpath. The classpath can have as many items as necessary. The classpath "
            + "must contain the dependencies of the program, the root directory for the compiled classes, and the root directory for the test classes. "
            + "All directories are in relative to the inputDirectory argument.",
            variableArity = true)
    public List<String> trainingPrograms = Lists.newArrayList("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;");
}
