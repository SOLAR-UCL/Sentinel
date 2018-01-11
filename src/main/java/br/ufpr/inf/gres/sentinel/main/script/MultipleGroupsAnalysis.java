/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.main.script;

import br.ufpr.inf.gres.sentinel.statistics.Friedman;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Giovani
 */
public class MultipleGroupsAnalysis {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        String indicator = "HYPERVOLUME";
//        String indicator = "IGD";

        HashMap<String, String> dirs = new LinkedHashMap<>();
        dirs.put("commons-collections-3.0", "../Sentinel-Results/commons-collections-3.0/testingresults");
        dirs.put("commons-collections-3.1", "../Sentinel-Results/commons-collections-3.1/testingresults");
        dirs.put("commons-collections-3.2", "../Sentinel-Results/commons-collections-3.2/testingresults");
        dirs.put("commons-collections-3.3", "../Sentinel-Results/commons-collections-3.3/testingresults");
        dirs.put("commons-collections-4.0", "../Sentinel-Results/commons-collections-4.0/testingresults");
        dirs.put("commons-collections-4.1", "../Sentinel-Results/commons-collections-4.1/testingresults");
        dirs.put("jfreechart-1.0.0", "../Sentinel-Results/jfreechart-1.0.0/testingresults");
        dirs.put("joda-time-2.8", "../Sentinel-Results/joda-time-2.8/testingresults");
        dirs.put("joda-time-2.9", "../Sentinel-Results/joda-time-2.9/testingresults");
        dirs.put("wire-2.0.0", "../Sentinel-Results/wire-2.0.0/testingresults");
        dirs.put("wire-2.1.0", "../Sentinel-Results/wire-2.1.0/testingresults");
        dirs.put("wire-2.2.0", "../Sentinel-Results/wire-2.2.0/testingresults");

        ListMultimap<String, Double> results = ArrayListMultimap.create();
        for (Map.Entry<String, String> entry : dirs.entrySet()) {
            String system = entry.getKey();
            String dir = entry.getValue();

            try (Scanner scanner = new Scanner(new File(dir + "/" + system + "/" + indicator + ".txt"))) {
                final double nextDouble = scanner.nextDouble();
                System.out.println(nextDouble);
                results.put("Sentinel", nextDouble);
            }
        }
        System.out.println("# Sentinel Average: " + results.get("Sentinel").stream().mapToDouble(value -> value).average().getAsDouble());

        System.out.println("");

        for (Map.Entry<String, String> entry : dirs.entrySet()) {
            String dir = entry.getValue();
            try (Scanner scanner = new Scanner(new File(dir + "/RandomMutantSampling/" + indicator + ".txt"))) {
                final double nextDouble = scanner.nextDouble();
                System.out.println(nextDouble);
                results.put("RandomMutantSampling", nextDouble);
            }
        }
        System.out.println("# RandomMutantSampling Average: " + results.get("RandomMutantSampling").stream().mapToDouble(value -> value).average().getAsDouble());

        System.out.println("");

        for (Map.Entry<String, String> entry : dirs.entrySet()) {
            String dir = entry.getValue();
            try (Scanner scanner = new Scanner(new File(dir + "/RandomOperatorSelection/" + indicator + ".txt"))) {
                final double nextDouble = scanner.nextDouble();
                System.out.println(nextDouble);
                results.put("RandomOperatorSelection", nextDouble);
            }
        }
        System.out.println("# RandomOperatorSelection Average: " + results.get("RandomOperatorSelection").stream().mapToDouble(value -> value).average().getAsDouble());

        System.out.println("");

        for (Map.Entry<String, String> entry : dirs.entrySet()) {
            String dir = entry.getValue();
            try (Scanner scanner = new Scanner(new File(dir + "/SelectiveMutation/" + indicator + ".txt"))) {
                final double nextDouble = scanner.nextDouble();
                System.out.println(nextDouble);
                results.put("SelectiveMutation", nextDouble);
            }
        }
        System.out.println("# SelectiveMutation Average: " + results.get("SelectiveMutation").stream().mapToDouble(value -> value).average().getAsDouble());

        Friedman.test(results, new File("../Sentinel-Results" + File.separator + indicator + "_FRIEDMAN.txt"));
    }

}
