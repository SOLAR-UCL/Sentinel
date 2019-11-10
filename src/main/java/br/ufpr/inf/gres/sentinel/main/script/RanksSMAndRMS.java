/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.main.script;

import com.google.common.collect.Streams;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.math3.stat.ranking.NaturalRanking;

/**
 *
 * @author ucacggu
 */
public class RanksSMAndRMS {

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
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(0);
        
        NumberFormat msFormat = NumberFormat.getInstance();
        msFormat.setMaximumFractionDigits(2);
        msFormat.setMinimumFractionDigits(2);

        NaturalRanking ranking = new NaturalRanking();

        for (String system : systems) {
            File rmsFile = new File("testing/" + system + "_RMS.dat");
            File smFile = new File("testing/" + system + "_SM.dat");

            List<String> rmsLines = Files.readLines(rmsFile, Charset.defaultCharset());
            List<String> smLines = Files.readLines(smFile, Charset.defaultCharset());

            List<String> allLines = Streams.concat(rmsLines.stream(), smLines.stream()).collect(Collectors.toList());

            Map<String, Double> averageNumberOfMutants = allLines.stream()
                    .map(line -> line.strip().split("\\s"))
                    .collect(Collectors.groupingBy(line -> line[0], Collectors.mapping(line -> line[1], Collectors.averagingDouble(Double::valueOf))));

            Map<String, Double> averageCPUTimes = allLines.stream()
                    .map(line -> line.strip().split("\\s"))
                    .collect(Collectors.groupingBy(line -> line[0], Collectors.mapping(line -> line[2], Collectors.averagingDouble(Double::valueOf))));
            
            Map<String, Double> averageMutationScore = allLines.stream()
                    .map(line -> line.strip().split("\\s"))
                    .collect(Collectors.groupingBy(line -> line[0], Collectors.mapping(line -> line[3], Collectors.averagingDouble(Double::valueOf))));

            int size = averageCPUTimes.entrySet().size();
            double[] allNumberOfMutants = new double[size];
            double[] allCpuTimes = new double[size];
            String[] labels = new String[size];
            int i = 0;

            List<Map.Entry<String, Double>> entries = averageNumberOfMutants.entrySet().stream().sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue())).collect(Collectors.toList());

            for (Map.Entry<String, Double> entry : entries) {
                Double numberOfMutants = averageNumberOfMutants.get(entry.getKey());
                Double cpuTime = averageCPUTimes.get(entry.getKey());

                allNumberOfMutants[i] = numberOfMutants;
                allCpuTimes[i] = cpuTime;
                labels[i] = entry.getKey();
                i++;
            }

            List<String> labels2 = averageCPUTimes.entrySet().stream().sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue())).map(o1 -> o1.getKey()).collect(Collectors.toList());

            allNumberOfMutants = ranking.rank(allNumberOfMutants);
            allCpuTimes = ranking.rank(allCpuTimes);

            System.out.println("System: " + system);
            StringBuilder rows = new StringBuilder();
            rows.append("Name\tMS\t\t#M\t\tCPU\t#M Rank\tCPU Rank\n");
            int changes = 0;
            for (int j = 0; j < allCpuTimes.length; j++) {
                rows.append(labels[j]).append("\t(").append(msFormat.format(Math.abs(averageMutationScore.get(labels[j])))).append(")\t").append((int) allNumberOfMutants[j]).append("\t");
                if (allNumberOfMutants[j] != allCpuTimes[j]) {
                    changes++;
                    rows.append("!=");
                }
                rows.append("\t").append((int) allCpuTimes[j]).append("\t").append(labels[j]).append("\t").append(labels2.get(j)).append("\n");
            }
            System.out.println("Changes between ranks: " + changes + " (" + numberFormat.format((double) changes / allCpuTimes.length * 100) + "%)");
            System.out.println(rows.toString());
        }
    }

}
