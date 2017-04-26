package br.ufpr.inf.gres.sentinel.main.cli.args;

import br.ufpr.inf.gres.sentinel.main.cli.converter.SeparatorConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
@Parameters(commandDescription = "If Sentinel must be executed for analysing training results.",
        commandNames = "analyse")
public class AnalysisArgs {

    @Parameter(names = {"--help", "-h"}, description = "Shows this message.")
    public boolean help = false;

    @Parameter(names = {"--workingDirectory", "-w"},
            description = "The working directory of Sentinel.",
            converter = SeparatorConverter.class)
    public String workingDirectory = System.getProperty("user.dir");

    @Parameter(names = {"--trainingDirectory", "-td"},
            description = "The directory (relative to the working directory) in which the training was executed. This is the directory in which Sentinel will look for results to compute the analysis.",
            converter = SeparatorConverter.class)
    public String trainingDirectory = "training";

    @Parameter(names = {"--outputDirectory", "-od"},
            description = "The directory (relative to the working directory) in which the analysis results will be outputed.",
            converter = SeparatorConverter.class)
    public String outputDirectory = "analysis";

    @Parameter(names = {"--inputFilesRegex", "-ir"},
            description = "The regex for finding input files for the analysis. Sentinel will look into the training directory for these.")
    public String inputFilesRegex = "result*.json";

    @Parameter(names = {"--inputFiles", "-i"},
            description = "The input files for the analysis. Sentinel will look into the training directory for these. Sentinel will give priority to this argument over \"--inputFilesRegex\".",
            variableArity = true)
    public List<String> inputFiles = new ArrayList<>();

}
