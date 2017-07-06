package br.ufpr.inf.gres.sentinel.main.cli.args;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.indictaors.IndicatorFactory;
import br.ufpr.inf.gres.sentinel.main.cli.converter.SeparatorConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
@Parameters(commandDescription = "If Sentinel must be executed for analysing training results.",
        commandNames = "analyse")
public class AnalysisArgs {

    /**
     *
     */
    @Parameter(names = {"--axisLabels"},
            description = "The label for the axis of the graphs (x, y, z).",
            variableArity = true)
    public List<String> axisLabels = Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE);

    /**
     *
     */
    @Parameter(names = {"--help", "-h"}, description = "Shows this message.")
    public boolean help = false;
    /**
     *
     */
    @Parameter(names = "--indicators",
            description = "The indicators used to compute the quality of the results. Available options are: hypervolume, igd.",
            variableArity = true)
    public List<String> indicators = Lists.newArrayList(IndicatorFactory.HYPERVOLUME, IndicatorFactory.IGD);

    /**
     *
     */
    @Parameter(names = {"--inputDirectory", "-id"},
            description = "The directory (relative to the working directory) in which the training was executed. This is the directory in which Sentinel will look for results to compute the analysis.",
            converter = SeparatorConverter.class)
    public String inputDirectory = "training";

    /**
     *
     */
    @Parameter(names = {"--inputFilesGlob"},
            description = "The input files glob for finding input files for the analysis. If no input directory is provided, then Sentinel will look for files in the training directory.")
    public String inputFilesGlob = "**.json";
    /**
     *
     */
    @Parameter(names = {"--outputDirectory", "-od"},
            description = "The directory (relative to the working directory) in which the analysis results will be outputed.",
            converter = SeparatorConverter.class)
    public String outputDirectory = "analysis";
    /**
     *
     */
    @Parameter(names = "--plotHeight",
            description = "The height of the plots.")
    public int plotHeight = 768;
    /**
     *
     */
    @Parameter(names = "--plotWidth",
            description = "The width of the plots.")
    public int plotWidth = 1366;

    /**
     *
     */
    @Parameter(names = "--printIntermediateFiles",
            description = "A boolean argument to determine if Sentinel should print intermediate files, such as text files representing Pareto fronts.")
    public boolean printIntermediateFiles = false;

    /**
     *
     */
    @Parameter(names = "--printDominatedSolutions",
            description = "A boolean argument to determine if Sentinel should print dominated solutions information.")
    public boolean printDominatedSolutions = false;

    /**
     *
     */
    @Parameter(names = "--printParetoFronts",
            description = "A boolean argument to determine if Sentinel should print Pareto fronts.")
    public boolean printParetoFronts = true;
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
    @Parameter(names = "--threshold",
            description = "The threshold for excluding solutions with scores lower than that.")
    public double threshold = 0.05;

}
