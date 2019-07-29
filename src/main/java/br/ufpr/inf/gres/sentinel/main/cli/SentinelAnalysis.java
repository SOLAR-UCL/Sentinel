package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.StubProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.gson.GsonUtil;
import br.ufpr.inf.gres.sentinel.gson.ResultWrapper;
import br.ufpr.inf.gres.sentinel.indictaors.IndicatorFactory;
import br.ufpr.inf.gres.sentinel.indictaors.ScoreIndicator;
import br.ufpr.inf.gres.sentinel.indictaors.TimeIndicator;
import br.ufpr.inf.gres.sentinel.main.cli.args.AnalysisArgs;
import br.ufpr.inf.gres.sentinel.statistics.KruskalWallis;
import br.ufpr.inf.gres.sentinel.statistics.VarghaDelaney;
import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.math.Stats;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
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

    public static NumberFormat formatter = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
//    public static NumberFormat formatter = new DecimalFormat("0.00E0");

    private static final List<Paint> COLORS = Lists.newArrayList(
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.PINK,
            Color.ORANGE,
            Color.GRAY,
            Color.CYAN,
            Color.MAGENTA
    );

    /**
     *
     * @param analysisArgs
     * @param args
     * @throws IOException
     */
    public static void analyse(AnalysisArgs analysisArgs, String[] args) throws IOException, InterruptedException {
        System.out.println("####### Initializing Analysis for " + analysisArgs.inputDirectory);
        GsonUtil util = new GsonUtil(new StubProblem());
        ListMultimap<String, ResultWrapper> resultsFromJson = util.getResultsFromJsonFiles(analysisArgs.workingDirectory + File.separator + analysisArgs.inputDirectory, analysisArgs.inputFilesGlob);
        for (Map.Entry<String, ResultWrapper> entry : resultsFromJson.entries()) {
            entry.getValue().setResult(entry.getValue().getResult().stream().filter(solution -> solution.getObjective(1) * -1 >= analysisArgs.threshold).collect(Collectors.toList()));
        }
        if (!resultsFromJson.isEmpty()) {
            File outputDirectory = new File(analysisArgs.workingDirectory + File.separator + analysisArgs.outputDirectory);
            outputDirectory.mkdirs();
            if (analysisArgs.printParetoFronts) {
                printNonDominatedParetoFront(resultsFromJson, outputDirectory, analysisArgs);
                printOtherFronts(resultsFromJson, outputDirectory, analysisArgs);
            }
            computeQualityIndicators(resultsFromJson, outputDirectory, analysisArgs);
        }
    }

    private static void computeQualityIndicators(ListMultimap<String, ResultWrapper> resultsFromJson, File outputDirectory, AnalysisArgs analysisArgs) throws IOException, InterruptedException {
        ArrayFront referenceFront = new ArrayFront(getSolutions(resultsFromJson.values()));
        for (String indicatorName : analysisArgs.indicators) {
            ListMultimap<String, Double> results = getIndicatorResults(resultsFromJson, indicatorName, referenceFront);
            DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

            System.out.println("### Initializing " + indicatorName + " Assessment\n");
            System.out.println(Joiner.on(" & ").join(results.keySet()));
            for (String session : results.keySet()) {
                List<Double> indicatorValues = results.get(session);
                if (analysisArgs.printIntermediateFiles) {
                    String outputSession = outputDirectory.getPath() + File.separator + session + File.separator;
                    Files.createDirectories(Paths.get(outputSession));
                    try (FileWriter writer = new FileWriter(outputSession + indicatorName.toUpperCase() + ".txt")) {
                        for (Double doubleValue : indicatorValues) {
                            writer.write(String.valueOf(doubleValue) + "\n");
                        }
                        final Stats stats = Stats.of(indicatorValues);
                        writer.write("\n");
                        writer.write("Average: " + formatter.format(stats.mean()) + "\n");
                        writer.write("Std: " + formatter.format(stats.populationStandardDeviation()) + "\n");

                        System.out.print(formatter.format(stats.mean()) + " (" + formatter.format(stats.populationStandardDeviation()) + ") & ");
                    }
                }
                dataset.add(indicatorValues, "", session);
            }
            System.out.println("\b\b\b\n");
            System.out.println("### Startring Statistical Test Analysis\n");
            printStatisticalTests(results, outputDirectory, indicatorName);
            printBoxPlot(dataset, indicatorName, new File(outputDirectory + File.separator + indicatorName + ".png"), analysisArgs);
        }
    }

    private static void printStatisticalTests(ListMultimap<String, Double> results, File outputDirectory, String indicatorName) throws IOException, InterruptedException {
        int maxSize = results.asMap().entrySet().stream().mapToInt(entry -> entry.getValue().size()).max().getAsInt();
        for (String group : results.keySet()) {
            List<Double> groupValues = results.get(group);
            if (groupValues.size() == 1) {
                for (int i = 0; i < maxSize; i++) {
                    results.put(group, groupValues.get(0));
                }
            }
        }

        System.out.println("## Kruskal-Wallis Comparison");
        HashMap<String, HashMap<String, Boolean>> kruskalResults = KruskalWallis.test(results, new File(outputDirectory.getAbsolutePath() + File.separator + indicatorName + "_KRUSKAL.txt"));
        for (String session : kruskalResults.keySet()) {
            System.out.println("Algorithm: " + session);
            HashMap<String, Boolean> comparisons = kruskalResults.get(session);
            System.out.println("\t" + Joiner.on(" & ").join(comparisons.keySet()));
            System.out.print("\t");
            for (String comparedTo : comparisons.keySet()) {
                System.out.print(comparisons.get(comparedTo) + " & ");
            }
            System.out.println("\b\b\b\n");
        }
        System.out.println();

        System.out.println("## Vargha-Delaney Comparison");
        HashMap<String, HashMap<String, Double>> effectSizeResults = VarghaDelaney.test(results, new File(outputDirectory.getAbsolutePath() + File.separator + indicatorName + "_ES.txt"));
        for (String session : effectSizeResults.keySet()) {
            System.out.println("Algorithm: " + session);
            HashMap<String, Double> comparisons = effectSizeResults.get(session);
            System.out.println("\t" + Joiner.on(" & ").join(comparisons.keySet()));
            System.out.print("\t");
            for (String comparedTo : comparisons.keySet()) {
                Double value = comparisons.get(comparedTo);
                System.out.print(formatter.format(value) + " (" + VarghaDelaney.interpretEffectSize(value) + ") & ");
            }
            System.out.println("\b\b\b\n");
        }
        System.out.println();
    }

    private static StandardChartTheme createChartTheme() {
        StandardChartTheme theme = new StandardChartTheme("JFree/Shadow");
        theme.setPlotBackgroundPaint(Color.WHITE);
        theme.setDomainGridlinePaint(Color.GRAY);
        theme.setRangeGridlinePaint(Color.GRAY);
        theme.setDrawingSupplier(new DefaultDrawingSupplier(
                COLORS.toArray(new Paint[COLORS.size()]),
                new Paint[]{Color.decode("0xFFFF00"),
                    Color.decode("0x0036CC")},
                new Stroke[]{new BasicStroke(2.0f)},
                new Stroke[]{new BasicStroke(0.5f)},
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        return theme;
    }

    private static XYSeries createNonDominatedSeries(String title, List<VariableLengthSolution<Integer>> nonDominatedSolutions) {
        XYSeries nonDominatedSeries = new XYSeries(title);
        for (VariableLengthSolution<Integer> nonDominatedSolution : nonDominatedSolutions) {
            nonDominatedSeries.add(nonDominatedSolution.getObjective(0), nonDominatedSolution.getObjective(1));
        }
        return nonDominatedSeries;
    }

    private static ListMultimap<String, Double> getIndicatorResults(ListMultimap<String, ResultWrapper> resultsFromJson, String indicatorName, Front referenceFront) throws IOException {
        GenericIndicator<VariableLengthSolution<Integer>> indicator = IndicatorFactory.createIndicator(indicatorName, referenceFront);
        ArrayListMultimap<String, Double> results = ArrayListMultimap.create();
        for (String session : resultsFromJson.keySet()) {
            for (ResultWrapper resultWrapper : resultsFromJson.get(session)) {
                Double indicatorResult = indicator.evaluate(new ArrayList<>(resultWrapper.getResult()));
                results.put(session, indicatorResult);
            }
        }
        return results;
    }

    private static List<VariableLengthSolution<Integer>> getNonDominatedSolutions(Collection<ResultWrapper> resultsFromJson) {
        return SolutionListUtils.getNondominatedSolutions(getSolutions(resultsFromJson));
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
        renderer.setMaximumBarWidth(0.04);
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

    /**
     *
     * @param plot
     * @param outputFile
     * @param analysisArgs
     * @throws IOException
     */
    public static void printChart(JFreeChart plot, File outputFile, AnalysisArgs analysisArgs) throws IOException {
        StandardChartTheme theme = createChartTheme();
        theme.apply(plot);
        ChartUtilities.saveChartAsPNG(new File(outputFile.getPath()), plot, analysisArgs.plotWidth, analysisArgs.plotHeight);
    }

    private static void printNonDominatedParetoFront(ListMultimap<String, ResultWrapper> resultsFromJson, File outputDirectory, AnalysisArgs analysisArgs) throws IOException {
        List<VariableLengthSolution<Integer>> nonDominatedSolutions = getNonDominatedSolutions(resultsFromJson.values());
        if (analysisArgs.printIntermediateFiles) {
            try (FileWriter writer = new FileWriter(outputDirectory.getPath() + File.separator + "ND.txt")) {
                for (VariableLengthSolution<Integer> resultSolution : nonDominatedSolutions) {
                    writer.write(resultSolution.getObjective(0) + " " + resultSolution.getObjective(1) + "\n");
                }
            }

            try (FileWriter writer = new FileWriter(outputDirectory.getPath() + File.separator + "FUN_ALL_RUNS.txt")) {
                for (VariableLengthSolution<Integer> resultSolution : getSolutions(resultsFromJson.values())) {
                    writer.write(resultSolution.getObjective(0) + " " + resultSolution.getObjective(1) + "\n");
                }
            }
        }
        XYSeries plotSeries = createNonDominatedSeries("Non-Dominated Solutions", nonDominatedSolutions);
        printScatterPlot(new XYSeriesCollection(plotSeries), new File(outputDirectory.getPath() + File.separator + "ND.png"), analysisArgs);
    }

    private static void printOtherFronts(ListMultimap<String, ResultWrapper> resultsFromJson, File outputDirectory, AnalysisArgs analysisArgs) throws IOException {
        XYSeriesCollection allFrontsPlot = new XYSeriesCollection();

        for (String key : resultsFromJson.keySet()) {
            List<ResultWrapper> allResults = resultsFromJson.get(key);
            List<VariableLengthSolution<Integer>> result;

            if (analysisArgs.printDominatedSolutions) {
                result = getSolutions(allResults);
            } else {
                result = getNonDominatedSolutions(allResults);
            }

            result = result.stream()
                    .filter(solution
                            -> solution.getObjective(0) > 0.0 && solution.getObjective(0) <= 1.0
                    && Math.abs(solution.getObjective(1)) > 0.0 && Math.abs(solution.getObjective(1)) <= 1.0)
                    .collect(Collectors.toList());
            for (VariableLengthSolution<Integer> solution : result) {
                for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
                    solution.setObjective(i, Math.abs(solution.getObjective(i)));
                }
            }

            String outputSession = outputDirectory.getPath() + File.separator + key + File.separator;
            Files.createDirectories(Paths.get(outputSession));

            if (analysisArgs.printIntermediateFiles) {
                try (FileWriter writer = new FileWriter(outputSession + "FUN.txt")) {
                    for (VariableLengthSolution<Integer> resultSolution : result) {
                        writer.write(resultSolution.getObjective(0) + " " + resultSolution.getObjective(1) + "\n");
                    }
                }
            }

            switch (key) {
                case "RandomMutantSampling":
                    key = "RMS";
                    break;
                case "RandomOperatorSelection":
                    key = "ROS";
                    break;
                case "SelectiveMutation":
                    key = "SM";
                    break;
                default:
                    key = "Sentinel Strategies";
                    break;
            }

            XYSeries sessionSeries = createNonDominatedSeries(key, result);
            allFrontsPlot.addSeries(sessionSeries);

            XYSeriesCollection runsSeries = new XYSeriesCollection();
            for (ResultWrapper runResult : allResults) {
                List<VariableLengthSolution<Integer>> resultSolutions;

                if (analysisArgs.printDominatedSolutions) {
                    resultSolutions = runResult.getResult();
                } else {
                    resultSolutions = SolutionListUtils.getNondominatedSolutions(runResult.getResult());
                }

                if (analysisArgs.printIntermediateFiles) {
                    try (FileWriter writer = new FileWriter(outputSession + "FUN_" + runResult.getRunNumber() + ".txt")) {
                        for (VariableLengthSolution<Integer> resultSolution : resultSolutions) {
                            writer.write(resultSolution.getObjective(0) + " " + resultSolution.getObjective(1) + "\n");
                        }
                    }
                }
                XYSeries runSeries = createNonDominatedSeries(key + " " + runResult.getRunNumber(), resultSolutions);
                runsSeries.addSeries(runSeries);
            }
            printScatterPlot(runsSeries, new File(outputSession + "FUN.png"), analysisArgs);
        }
        printScatterPlot(allFrontsPlot, new File(outputDirectory.getPath() + File.separator + "FUN_ALL.png"), analysisArgs);
    }

    private static void printScatterPlot(XYSeriesCollection plotData, File outputFile, AnalysisArgs analysisArgs) throws IOException {
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(null, analysisArgs.axisLabels.get(0), analysisArgs.axisLabels.get(1), plotData, PlotOrientation.VERTICAL, true, false, false);
        printChart(scatterPlot, outputFile, analysisArgs);
    }

}
