package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.gson.GsonUtil;
import br.ufpr.inf.gres.sentinel.gson.ResultWrapper;
import br.ufpr.inf.gres.sentinel.indictaors.IndicatorFactory;
import br.ufpr.inf.gres.sentinel.main.cli.args.AnalysisArgs;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.uma.jmetal.qualityindicator.impl.GenericIndicator;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;

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
            printNonDominatedParetoFront(resultsFromJson, outputDirectory, analysisArgs);
            printOtherFronts(resultsFromJson, outputDirectory, analysisArgs);
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
                results.put(result.getSession().isEmpty() ? "EMPTY_SESSION" : result.getSession(), result);
            }
        }
        return results;
    }

    private static List<VariableLengthSolution<Integer>> getSolutions(Collection<ResultWrapper> resultsFromJson) {
        Optional<Stream<VariableLengthSolution<Integer>>> reduce = resultsFromJson
                .stream()
                .map(result -> result.getResult().stream())
                .reduce((firstList, secondList) -> {
                    return Stream.concat(firstList, secondList);
                });
        List<VariableLengthSolution<Integer>> allSolutions = new ArrayList<>();
        if (reduce.isPresent()) {
            allSolutions = reduce.get().collect(Collectors.toList());
        }
        return allSolutions;
    }

    private static List<VariableLengthSolution<Integer>> getNonDominatedSolutions(Collection<ResultWrapper> resultsFromJson) {
        return SolutionListUtils.getNondominatedSolutions(getSolutions(resultsFromJson));
    }

    private static List<VariableLengthSolution<Integer>> printNonDominatedParetoFront(ListMultimap<String, ResultWrapper> resultsFromJson, File outputDirectory, AnalysisArgs analysisArgs) throws IOException {
        List<VariableLengthSolution<Integer>> allSolutions = getNonDominatedSolutions(resultsFromJson.values());
        if (analysisArgs.printIntermediateFiles) {
            try (FileWriter writer = new FileWriter(outputDirectory.getPath() + File.separator + "ND.txt")) {
                for (VariableLengthSolution<Integer> resultSolution : allSolutions) {
                    writer.write(resultSolution.getObjective(0) + " " + resultSolution.getObjective(1) + " " + resultSolution.getAttribute("Quantity") + "\n");
                }
            }

            try (FileWriter writer = new FileWriter(outputDirectory.getPath() + File.separator + "FUN_ALL_RUNS.txt")) {
                for (VariableLengthSolution<Integer> resultSolution : getSolutions(resultsFromJson.values())) {
                    writer.write(resultSolution.getObjective(0) + " " + resultSolution.getObjective(1) + " " + resultSolution.getAttribute("Quantity") + "\n");
                }
            }
        }
        XYSeries plotSeries = createNonDominatedSeries("Non-Dominated Solutions", allSolutions);
        printScatterPlot(new XYSeriesCollection(plotSeries), new File(outputDirectory.getPath() + File.separator + "ND.png"), analysisArgs);
//            printChart(new XYSeriesCollection(plotSeries), new File(outputDirectory.getPath() + File.separator + "ND_TQ.png"), analysisArgs);
//            printChart(new XYSeriesCollection(plotSeries), new File(outputDirectory.getPath() + File.separator + "ND_SQ.png"), analysisArgs);
        return allSolutions;
    }

    private static void printOtherFronts(ListMultimap<String, ResultWrapper> resultsFromJson, File outputDirectory, AnalysisArgs analysisArgs) throws IOException {
        XYSeriesCollection allFrontsPlot = new XYSeriesCollection();
        List<VariableLengthSolution<Integer>> nonDominatedSolutions = getNonDominatedSolutions(resultsFromJson.values());
        XYSeries nonDominatedSeries = createNonDominatedSeries("Non-Dominated Solutions", nonDominatedSolutions);
        allFrontsPlot.addSeries(nonDominatedSeries);

        for (String key : resultsFromJson.keySet()) {
            List<ResultWrapper> allResults = resultsFromJson.get(key);
            List<VariableLengthSolution<Integer>> result = getNonDominatedSolutions(allResults);

            String outputSession = outputDirectory.getPath() + File.separator + key + File.separator;
            Files.createDirectories(Paths.get(outputSession));

            if (analysisArgs.printIntermediateFiles) {
                try (FileWriter writer = new FileWriter(outputSession + "FUN.txt")) {
                    for (VariableLengthSolution<Integer> resultSolution : result) {
                        writer.write(resultSolution.getObjective(0) + " " + resultSolution.getObjective(1) + " " + resultSolution.getAttribute("Quantity") + "\n");
                    }
                }
            }
            XYSeries sessionSeries = createNonDominatedSeries(key, result);
            allFrontsPlot.addSeries(sessionSeries);

            XYSeriesCollection runsSeries = new XYSeriesCollection();
            runsSeries.addSeries(nonDominatedSeries);
            for (int i = 0; i < allResults.size(); i++) {
                try (FileWriter writer = new FileWriter(outputSession + "FUN_" + (i + 1) + ".txt")) {
                    ResultWrapper runResult = allResults.get(i);
                    List<VariableLengthSolution<Integer>> resultSolutions = runResult.getResult();
                    if (analysisArgs.printIntermediateFiles) {
//                    new SolutionListOutput(resultSolutions).printObjectivesToFile(outputSession + "FUN_" + (i + 1) + ".txt");
                        for (VariableLengthSolution<Integer> resultSolution : resultSolutions) {
                            writer.write(resultSolution.getObjective(0) + " " + resultSolution.getObjective(1) + " " + resultSolution.getAttribute("Quantity") + "\n");
                        }
                    }
                    XYSeries runSeries = createNonDominatedSeries(key + " " + (i + 1), resultSolutions);
                    runsSeries.addSeries(runSeries);
                }
            }
            printScatterPlot(runsSeries, new File(outputSession + "FUN.png"), analysisArgs);
//            printChart(runsSeries, new File(outputSession + "FUN_TQ.png"), analysisArgs);
//            printChart(runsSeries, new File(outputSession + "FUN_SQ.png"), analysisArgs);
        }
        printScatterPlot(allFrontsPlot, new File(outputDirectory.getPath() + File.separator + "FUN_ALL.png"), analysisArgs);
//        printChart(allFrontsPlot, new File(outputDirectory.getPath() + File.separator + "FUN_ALL_TQ.png"), analysisArgs);
//        printChart(allFrontsPlot, new File(outputDirectory.getPath() + File.separator + "FUN_ALL_SQ.png"), analysisArgs);

    }

    private static void computeQualityIndicators(ListMultimap<String, ResultWrapper> resultsFromJson, File outputDirectory, AnalysisArgs analysisArgs) throws IOException {
        ArrayFront referenceFront = new ArrayFront(getNonDominatedSolutions(resultsFromJson.values()));
        for (String indicatorName : analysisArgs.indicators) {
            ListMultimap<String, Double> results = getIndicatorResults(resultsFromJson, indicatorName, referenceFront);
            DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
            for (String session : results.keySet()) {
                List<Double> indicatorValues = results.get(session);
                if (analysisArgs.printIntermediateFiles) {
                    String outputSession = outputDirectory.getPath() + File.separator + session + File.separator;
                    Files.createDirectories(Paths.get(outputSession));
                    try (FileWriter writer = new FileWriter(outputSession + indicatorName.toUpperCase() + ".txt")) {
                        for (Double doubleValue : indicatorValues) {
                            writer.write(String.valueOf(doubleValue) + "\n");
                        }
                    }
                }
                dataset.add(indicatorValues, "", session);
            }
            printBoxPlot(dataset, indicatorName, new File(outputDirectory + File.separator + indicatorName + ".png"), analysisArgs);
        }
    }

    private static void printBoxPlot(DefaultBoxAndWhiskerCategoryDataset dataSet, String indicatorName, File outputFile, AnalysisArgs analysisArgs) throws IOException {
        final CategoryAxis xAxis = new CategoryAxis(indicatorName);
        xAxis.setLowerMargin(0.15);
        xAxis.setUpperMargin(0.15);
        xAxis.setCategoryMargin(0.25);

        final NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(false);

        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(false);
        renderer.setMeanVisible(false);
        renderer.setMaximumBarWidth(0.1);
        renderer.setUseOutlinePaintForWhiskers(true);
        for (int i = 0; i < dataSet.getColumnCount(); i++) {
            renderer.setSeriesPaint(i, Color.BLACK);
            renderer.setSeriesOutlinePaint(i, Color.BLACK);
            renderer.setSeriesFillPaint(i, Color.GRAY);
        }

        final CategoryPlot plot = new CategoryPlot(dataSet, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart(
                null,
                null,
                plot,
                false
        );

        printChart(chart, outputFile, analysisArgs);
    }

    public static ListMultimap<String, Double> getIndicatorResults(ListMultimap<String, ResultWrapper> resultsFromJson, String indicatorName, Front referenceFront) throws IOException {
        GenericIndicator<VariableLengthSolution<Integer>> indicator = IndicatorFactory.createIndicator(indicatorName, referenceFront);
        ArrayListMultimap<String, Double> results = ArrayListMultimap.create();
        for (String session : resultsFromJson.keySet()) {
            for (ResultWrapper resultWrapper : resultsFromJson.get(session)) {
                Double indicatorResult = indicator.evaluate(resultWrapper.getResult());
                results.put(session, indicatorResult);
            }
        }
        return results;
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

    private static void printScatterPlot(XYSeriesCollection plotData, File outputFile, AnalysisArgs analysisArgs) throws IOException {
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(null, "Time", "Score", plotData, PlotOrientation.VERTICAL, true, false, false);
        printChart(scatterPlot, outputFile, analysisArgs);
    }

    public static void printChart(JFreeChart plot, File outputFile, AnalysisArgs analysisArgs) throws IOException {
        StandardChartTheme theme = createChartTheme();
        theme.apply(plot);
        ChartUtilities.saveChartAsPNG(new File(outputFile.getPath()), plot, analysisArgs.plotWidth, analysisArgs.plotHeight);
    }

    public static StandardChartTheme createChartTheme() {
        StandardChartTheme theme = new StandardChartTheme("JFree/Shadow");
        theme.setPlotBackgroundPaint(Color.WHITE);
        theme.setDomainGridlinePaint(Color.GRAY);
        theme.setRangeGridlinePaint(Color.GRAY);
        return theme;
    }

}
