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
    public String grammarFilePath = "grammars/default_grammar_no_homs.bnf";

    /**
     *
     */
    @Parameter(names = {"--testingPrograms", "-tp"},
            description = "The names of the testing programs to evaluate the strategies. Sentinel will search for the programs in /path/to/testing/directory/ according to the tool used for the mutant generation.",
            variableArity = true)
    public List<String> testingPrograms = Lists.newArrayList("br.ufpr.inf.gres.TriTyp");

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
    @Parameter(names = {"--workingDirectory", "-w"},
            description = "The working directory of Sentinel.",
            converter = SeparatorConverter.class)
    public String workingDirectory = System.getProperty("user.dir");

    /**
     *
     */
    @Parameter(names = "--numberOfTestingRuns",
            description = "Number of testing runs for each testing program in each testing evaluation.")
    public Integer numberOfTestingRuns = 100;

    /**
     *
     */
    @Parameter(names = {"--objectiveFunctions", "-of"},
            description = "The objective functions used to test the strategies. Available options are: " + ObjectiveFunction.AVERAGE_CPU_TIME + ", " + ObjectiveFunction.AVERAGE_SCORE + ", " + ObjectiveFunction.AVERAGE_QUANTITY + ".",
            variableArity = true)
    public List<String> objectiveFunctions = Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE);

    /**
     *
     */
    @Parameter(names = {"--outputDirectory", "-od"},
            description = "The directory (relative to the working directory) in which the testing programs are located and where the testing results will be outputed.",
            converter = SeparatorConverter.class)
    public String outputDirectory = "testing";

    /**
     *
     */
    @Parameter(names = {"--inputDirectory", "-id"},
            description = "The directory (relative to the working directory) in which the training was executed. This is the directory in which Sentinel will look for results to build strategies and execute the testing.",
            converter = SeparatorConverter.class)
    public String inputDirectory = "training";

    /**
     *
     */
    @Parameter(names = {"--inputFilesGlob"},
            description = "The input files glob for finding strategy input files for the testing. If no input directory is provided, then Sentinel will look for files in the training directory.")
    public String inputFilesGlob = "**.json";

}
