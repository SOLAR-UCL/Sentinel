package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.main.cli.args.AnalysisArgs;
import br.ufpr.inf.gres.sentinel.main.cli.gson.GsonUtil;
import br.ufpr.inf.gres.sentinel.main.cli.gson.ResultWrapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;

/**
 *
 * @author Giovani Guizzo
 */
public class SentinelAnalysis {

    public static void analyse(AnalysisArgs analysisArgs, String[] args) throws IOException {
        ListMultimap<String, ResultWrapper> resultsFromJson = getResultsFromJson(analysisArgs);
        File outputDirectory = new File(analysisArgs.workingDirectory + File.separator + analysisArgs.outputDirectory);
        outputDirectory.mkdirs();
        if (analysisArgs.printParetoFronts) {
            List<VariableLengthSolution<Integer>> nonDominatedSolutions = printNonDominatedParetoFront(resultsFromJson, outputDirectory, analysisArgs);
            printOtherFronts(nonDominatedSolutions, resultsFromJson, outputDirectory, analysisArgs);
        }
        computeQualityIndicators(resultsFromJson, outputDirectory, analysisArgs);
    }

    public static ListMultimap<String, ResultWrapper> getResultsFromJson(AnalysisArgs analysisArgs) throws IOException {
        File inputDirectory = new File(analysisArgs.workingDirectory + File.separator + analysisArgs.inputDirectory);
        ListMultimap<String, ResultWrapper> results = ArrayListMultimap.create();

        GsonUtil gson = new GsonUtil();
        try (DirectoryStream<Path> jsonFiles = Files.newDirectoryStream(inputDirectory.toPath(), analysisArgs.inputFilesRegex)) {
            for (Path jsonFile : jsonFiles) {
                ResultWrapper result = gson.fromJson(jsonFile);
                results.put(result.getSession(), result);
            }
        }
        return results;
    }

    private static List<VariableLengthSolution<Integer>> printNonDominatedParetoFront(ListMultimap<String, ResultWrapper> resultsFromJson, File outputDirectory, AnalysisArgs analysisArgs) throws IOException {
        Optional<Stream<VariableLengthSolution<Integer>>> reduce = resultsFromJson
                .values()
                .stream()
                .map(result -> result.getResult().stream())
                .reduce((firstList, secondList) -> {
                    return Stream.concat(firstList, secondList);
                });
        List<VariableLengthSolution<Integer>> allSolutions = null;
        if (reduce.isPresent()) {
            allSolutions = reduce.get().collect(Collectors.toList());
            allSolutions = SolutionListUtils.getNondominatedSolutions(allSolutions);
            if (analysisArgs.printIntermediateFiles) {
                new SolutionListOutput(allSolutions).printObjectivesToFile(outputDirectory.getPath() + File.separator + "ND.txt");
            }
            XYSeries plotSeries = createNonDominatedSeries("Non-Dominated Solutions", allSolutions);
            printChart(new XYSeriesCollection(plotSeries), new File(outputDirectory.getPath() + File.separator + "ND.png"), analysisArgs);
//            printChart(new XYSeriesCollection(plotSeries), new File(outputDirectory.getPath() + File.separator + "ND_TQ.png"), analysisArgs);
//            printChart(new XYSeriesCollection(plotSeries), new File(outputDirectory.getPath() + File.separator + "ND_SQ.png"), analysisArgs);
        }
        return allSolutions;
    }

    private static void printOtherFronts(List<VariableLengthSolution<Integer>> nonDominatedSolutions, ListMultimap<String, ResultWrapper> resultsFromJson, File outputDirectory, AnalysisArgs analysisArgs) throws IOException {
        XYSeriesCollection allFrontsPlot = new XYSeriesCollection();
        XYSeries nonDominatedSeries = createNonDominatedSeries("Non-Dominated Solutions", nonDominatedSolutions);
        allFrontsPlot.addSeries(nonDominatedSeries);

        for (String key : resultsFromJson.keySet()) {
            List<ResultWrapper> allResults = resultsFromJson.get(key);
            Optional<Stream<VariableLengthSolution<Integer>>> reduce = allResults.stream()
                    .map(result -> result.getResult().stream())
                    .reduce((firstList, secondList) -> {
                        return Stream.concat(firstList, secondList);
                    });

            key = key.isEmpty() ? "EMPTY_SESSION" : key;
            String outputSession = outputDirectory.getPath() + File.separator + key + File.separator;
            Files.createDirectories(Paths.get(outputSession));
            if (reduce.isPresent()) {
                List<VariableLengthSolution<Integer>> result = reduce.get().collect(Collectors.toList());
                result = SolutionListUtils.getNondominatedSolutions(result);
                if (analysisArgs.printIntermediateFiles) {
                    new SolutionListOutput(result).printObjectivesToFile(outputSession + "FUN.txt");
                }
                XYSeries sessionSeries = createNonDominatedSeries(key, result);
                allFrontsPlot.addSeries(sessionSeries);
            }

            XYSeriesCollection allRuns = new XYSeriesCollection();
            allRuns.addSeries(nonDominatedSeries);
            for (int i = 0; i < allResults.size(); i++) {
                ResultWrapper result = allResults.get(i);
                List<VariableLengthSolution<Integer>> resultSolutions = result.getResult();
                XYSeries sessionSeries = createNonDominatedSeries(key + " " + (i + 1), resultSolutions);
                allRuns.addSeries(sessionSeries);
            }
            printChart(allRuns, new File(outputSession + "FUN.png"), analysisArgs);
//            printChart(allRuns, new File(outputSession + "FUN_TQ.png"), analysisArgs);
//            printChart(allRuns, new File(outputSession + "FUN_SQ.png"), analysisArgs);
        }
        printChart(allFrontsPlot, new File(outputDirectory.getPath() + File.separator + "FUN_ALL.png"), analysisArgs);
//        printChart(allFrontsPlot, new File(outputDirectory.getPath() + File.separator + "FUN_ALL_TQ.png"), analysisArgs);
//        printChart(allFrontsPlot, new File(outputDirectory.getPath() + File.separator + "FUN_ALL_SQ.png"), analysisArgs);

    }

    private static XYSeries createNonDominatedSeries(String title, List<VariableLengthSolution<Integer>> nonDominatedSolutions) {
        XYSeries nonDominatedSeries = new XYSeries(title);
        for (VariableLengthSolution<Integer> nonDominatedSolution : nonDominatedSolutions) {
            nonDominatedSeries.add(nonDominatedSolution.getObjective(0), nonDominatedSolution.getObjective(1));
//            nonDominatedSeries.add(nonDominatedSolution.getObjective(0), (double) nonDominatedSolution.getAttribute("Quantity"));
//            nonDominatedSeries.add((double) nonDominatedSolution.getAttribute("Quantity"), nonDominatedSolution.getObjective(1));
        }
        return nonDominatedSeries;
    }

    private static void printChart(XYSeriesCollection plotData, File outputFile, AnalysisArgs analysisArgs) throws IOException {
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(null, "Quantity", "Score", plotData, PlotOrientation.VERTICAL, true, false, false);
        StandardChartTheme theme = new StandardChartTheme("JFree/Shadow");
        theme.setPlotBackgroundPaint(Color.WHITE);
        theme.setDomainGridlinePaint(Color.GRAY);
        theme.setRangeGridlinePaint(Color.GRAY);
        theme.apply(scatterPlot);
        ChartUtilities.saveChartAsPNG(new File(outputFile.getPath()), scatterPlot, analysisArgs.plotWidth, analysisArgs.plotHeight);
    }

    private static void computeQualityIndicators(ListMultimap<String, ResultWrapper> resultsFromJson, File outputDirectory, AnalysisArgs analysisArgs) {
    }

}
