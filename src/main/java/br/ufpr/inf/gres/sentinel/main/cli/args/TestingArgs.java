package br.ufpr.inf.gres.sentinel.main.cli.args;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.main.cli.converter.SeparatorConverter;
import br.ufpr.inf.gres.sentinel.main.cli.splitter.NoParameterSplitter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.common.collect.Lists;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
@Parameters(commandDescription = "If Sentinel must be executed for executing strategies (testing).",
        commandNames = "test")
public class TestingArgs {

    /**
     *
     */
    @Parameter(names = {
        "--facade", "--integrationFacade", "--tool", "--mutationTool", "-m"
    },
            description = "The tool used to effectively generate the mutants. Available options: " + IntegrationFacadeFactory.PIT + ".")
    public String facade = "PIT";

    /**
     *
     */
    @Parameter(names = {
        "--grammar", "--grammarFile", "-g"
    },
            description = "The grammar file path (relative to the working directory) used to interpret the strategies.",
            converter = SeparatorConverter.class)
    public String grammarFilePath = "grammars/default_grammar.bnf";

    /**
     *
     */
    @Parameter(names = {"--testingPrograms", "-tp"},
            description = "Testing programs for the testing phase. Each String represents one testing program with the following pattern: "
            + "<name>;<sourceDir>;<targetClassesGlob>;<targetTestsGlob>;<classpathItems>"
            + ". name is the program's name (for result purposes), sourceDir represents the source directory for the program, "
            + "targetClassesGlob includes classes to be mutated, targetTestsGlob includes classes to test the software, and classpathItems is "
            + "used to add dependencies of the program in the Java classpath. The classpath can have as many items as necessary. The classpath "
            + "must contain the dependencies of the program, the root directory for the compiled classes, and the root directory for the test classes. "
            + "All directories are in relative to the inputDirectory argument.",
            variableArity = true,
            splitter = NoParameterSplitter.class,
            required = true)
    public List<String> testingPrograms;

    /**
     *
     */
    @Parameter(names = "--maxWraps", description = "Maximum chromosome wraps.")
    public Integer maxWraps = 10;

    /**
     *
     */
    @Parameter(names = {"--help", "-h"}, description = "Shows this message.")
    public boolean help = false;

    /**
     *
     */
    @Parameter(names = {"--verbose"}, description = "If Sentinel should log everything.")
    public boolean verbose = false;

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
    @Parameter(names = "--numberOfTestingRuns",
            description = "Number of testing runs for each testing program in each testing evaluation.")
    public Integer numberOfTestingRuns = 10;

    /**
     *
     */
    @Parameter(names = {"--objectiveFunctions", "-of"},
            description = "The objective functions used to test the strategies. Available options are: " + ObjectiveFunction.AVERAGE_CPU_TIME + ", " + ObjectiveFunction.AVERAGE_SCORE + ", " + ObjectiveFunction.AVERAGE_QUANTITY + ".",
            variableArity = true,
            splitter = NoParameterSplitter.class)
    public List<String> objectiveFunctions = Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE);

    /**
     *
     */
    @Parameter(names = {"--outputDirectory", "-od"},
            description = "The directory (relative to the working directory) where the testing results will be outputed.",
            converter = SeparatorConverter.class)
    public String outputDirectory = "testing";

    /**
     *
     */
    @Parameter(names = {"--inputDirectory", "-id"},
            description = "The directory (relative to the working directory) in which the testing programs are located and the training was executed. This is the directory in which Sentinel will look for results to build strategies and execute the testing.",
            converter = SeparatorConverter.class)
    public String inputDirectory = "training";

    /**
     *
     */
    @Parameter(names = {"--inputFilesGlob"},
            description = "The input files glob for finding strategy input files for the testing. If no input directory is provided, then Sentinel will look for files in the training directory.")
    public String inputFilesGlob = "**.json";

}
