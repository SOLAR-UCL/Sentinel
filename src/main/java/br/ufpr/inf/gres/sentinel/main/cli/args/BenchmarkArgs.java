package br.ufpr.inf.gres.sentinel.main.cli.args;

import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.main.cli.converter.SeparatorConverter;
import br.ufpr.inf.gres.sentinel.main.cli.splitter.NoParameterSplitter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.List;
import org.pitest.process.DefaultJavaExecutableLocator;

/**
 * @author Giovani Guizzo
 */
@Parameters(commandDescription = "If Sentinel must be executed for benchmarking execution (saving execution time and etc).",
        commandNames = "benchmark")
public class BenchmarkArgs {

    /**
     *
     */
    @Parameter(names = {"--facade", "--integrationFacade", "--tool", "--mutationTool", "-m"},
            description = "The tool used to effectively generate the mutants. Available options: " + IntegrationFacadeFactory.PIT + ".")
    public String facade = "PIT";

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
    @Parameter(names = "--numberOfRuns",
            description = "Number of runs for each training program for creating the the benchmark.")
    public Integer numberOfRuns = 5;

    /**
     *
     */
    @Parameter(names = {"--outputDirectory", "-od"},
            description = "The directory (relative to the working directory) in which the benchmark results will be outputed.",
            converter = SeparatorConverter.class)
    public String outputDirectory = ".";

    /**
     *
     */
    @Parameter(names = {"--inputDirectory", "-id"},
            description = "The directory (relative to the working directory) in which the programs for the benchmark are located.",
            converter = SeparatorConverter.class)
    public String inputDirectory = ".";

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
            variableArity = true,
            splitter = NoParameterSplitter.class,
            required = true)
    public List<String> trainingPrograms;

    /**
     *
     */
    @Parameter(names = {"--jvmPath"}, description = "The JVM path for executing the tests.")
    public String jvmPath = new DefaultJavaExecutableLocator().javaExecutable();
}
