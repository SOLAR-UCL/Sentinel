/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.main.script;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.io.Files;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;

/**
 *
 * @author ucacggu
 */
public class Correlation {

    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<String> systems = new ArrayList<>();
        systems.add("commons-beanutils-1.8.0");
        systems.add("commons-codec-1.4");
        systems.add("commons-collections-3.0");
        systems.add("commons-lang-3.0");
        systems.add("commons-validator-1.4.0");
        systems.add("jfreechart-1.0.0");
        systems.add("jgrapht-0.9.0");
        systems.add("joda-time-2.8");
        systems.add("ognl-3.1");
        systems.add("wire-2.0.0");

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(3);
        numberFormat.setMinimumFractionDigits(3);

        WilcoxonSignedRankTest wilcoxon = new WilcoxonSignedRankTest();
        SpearmansCorrelation spearmansCorrelation = new SpearmansCorrelation();

        HashMap<String, List<Double>> allAverageDifferences = new HashMap<>();
        HashMap<String, List<Boolean>> allIsDifferent = new HashMap<>();
        HashMap<String, Double> allSpearman = new HashMap<>();

        for (String system : systems) {
            File file = new File("testing/" + system + "_RMS.dat");
            List<String> lines = Files.readLines(file, Charset.defaultCharset());

            double[] allCpuTimesArray = new double[lines.size()];
            double[] allNumberOfMutantsArray = new double[lines.size()];
            
            ArrayListMultimap<String, String> multiMap = ArrayListMultimap.create();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] values = line.strip().split("\\s");
                
                allCpuTimesArray[i] = Double.valueOf(values[0]);
                allNumberOfMutantsArray[i] = Double.valueOf(values[1]);
                
                multiMap.put(values[0], values[1]);
            }
            
            allSpearman.put(system, spearmansCorrelation.correlation(allNumberOfMutantsArray, allCpuTimesArray));

            List<Double> averageDifferences = new ArrayList<>();
            List<Boolean> isDifferent = new ArrayList<>();
            for (int i = 1; i <= multiMap.keySet().size(); i++) {
                String numberOfMutants = "0." + i + "0";
                List<String> cpuTimes = multiMap.get(numberOfMutants);
                final double numberOfMutantsDouble = Double.valueOf(numberOfMutants);
                List<Double> differences = cpuTimes.stream().mapToDouble(cpuTime -> Double.valueOf(cpuTime) - numberOfMutantsDouble).boxed().collect(Collectors.toList());

                double[] cpuTimeArray = cpuTimes.stream().mapToDouble(Double::valueOf).toArray();
                double[] numberOfMutantsArray = new double[cpuTimeArray.length];
                Arrays.fill(numberOfMutantsArray, numberOfMutantsDouble);

                double pValue = wilcoxon.wilcoxonSignedRankTest(cpuTimeArray, numberOfMutantsArray, false);
                isDifferent.add(pValue < 0.05);

                averageDifferences.add(differences.stream().mapToDouble(Double::doubleValue).average().getAsDouble());
            }

            allAverageDifferences.put(system, averageDifferences);
            allIsDifferent.put(system, isDifferent);
        }

        double max = allAverageDifferences.values().stream().mapToDouble(list -> list.stream().mapToDouble(Math::abs).max().getAsDouble()).max().getAsDouble();
        double min = allAverageDifferences.values().stream().mapToDouble(list -> list.stream().mapToDouble(Math::abs).min().getAsDouble()).min().getAsDouble();
        
        for (String system : systems) {
            List<Double> averageDifferences = allAverageDifferences.get(system);
            List<Boolean> isDifferent = allIsDifferent.get(system);

            numberFormat.setMinimumFractionDigits(0);
            numberFormat.setMaximumFractionDigits(2);
            List<String> formattedAverageDifferences = new ArrayList<>();
            for (int i = 0; i < averageDifferences.size(); i++) {
                Double averageDifference = averageDifferences.get(i);
                Boolean different = isDifferent.get(i);
                if (different) {
                    String color = averageDifference < 0 ? "red" : "blue";
                    formattedAverageDifferences.add("\\cellcolor{" + color + "!" + (int) (((Math.abs(averageDifference) - min) / (max - min)) * 50 + 25) + "} " + numberFormat.format(averageDifference * 100));
                } else {
                    formattedAverageDifferences.add(numberFormat.format(averageDifference * 100));
                }
            }

            numberFormat.setMaximumFractionDigits(3);
            System.out.println(system.replaceAll("commons-", "") + " & " + numberFormat.format(allSpearman.get(system)) + " & " + Joiner.on(" & ").join(formattedAverageDifferences) + " \\\\");
        }
    }

}
