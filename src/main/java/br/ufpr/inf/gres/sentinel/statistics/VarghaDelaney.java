/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.statistics;

import static br.ufpr.inf.gres.sentinel.statistics.KruskalWallis.test;
import com.google.common.base.Joiner;
import com.google.common.collect.ListMultimap;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Giovani
 */
public class VarghaDelaney {

    public static final String NEGLIGIBLE = "negligible";
    public static final String SMALL = "small";
    public static final String MEDIUM = "medium";
    public static final String LARGE = "large";
    
    public static HashMap<String, HashMap<String, Double>> test(ListMultimap<String, Double> values) throws IOException, InterruptedException {
        File outputFile = File.createTempFile("output", ".R");
        outputFile.deleteOnExit();
        HashMap<String, HashMap<String, Double>> result = test(values, outputFile);
        outputFile.delete();
        return result;
    }

    public static HashMap<String, HashMap<String, Double>> test(ListMultimap<String, Double> values, File outputFile) throws IOException, InterruptedException {
        HashMap<String, HashMap<String, Double>> result = new HashMap<>();

        StringBuilder script = new StringBuilder();
        script.append("require(\"effsize\")\n");

        Set<String> keys = values.keySet();

        for (String key : keys) {
            script.append("\"").append(key).append("\" <- c(").append(Joiner.on(",").join(values.get(key)))
                    .append(")\n");
        }

        String[] groupArray = (String[]) keys.toArray(new String[0]);

        for (int i = 0; i < groupArray.length - 1; i++) {
            String groupA = groupArray[i];
            for (int j = i + 1; j < groupArray.length; j++) {
                String groupB = groupArray[j];
                script.append("VD.A(get(\"").append(groupA).append("\"),get(\"").append(groupB).append("\"))\n");
            }
        }
        script.append("q()\n");

        File inputFile = File.createTempFile("script", ".R");
        inputFile.deleteOnExit();

        new FileWriter(inputFile).append(script).flush();

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(System.getProperty("os.name").contains("win") ? "R.exe" : "R", "--slave", "-f", inputFile.getAbsolutePath());
        builder.redirectOutput(outputFile);
        builder.start().waitFor();

        Scanner scanner = new Scanner(outputFile);

        List<Double> comparisonValues = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("A estimate:")) {
                String[] split = line.split(" ");

                Double doubleValue = Double.parseDouble(split[2]);
                if (doubleValue.isNaN()) {
                    doubleValue = 0.0;
                }

                comparisonValues.add(doubleValue);
            }
        }

        Iterator<Double> iterator = comparisonValues.iterator();
        for (int i = 0; i < groupArray.length - 1; i++) {
            String groupA = groupArray[i];
            for (int j = i + 1; j < groupArray.length; j++) {
                String groupB = groupArray[j];
                HashMap<String, Double> groupAMap = result.get(groupA);
                if (groupAMap == null) {
                    groupAMap = new HashMap<>();
                    result.put(groupA, groupAMap);
                }
                HashMap<String, Double> groupBMap = result.get(groupB);
                if (groupBMap == null) {
                    groupBMap = new HashMap<>();
                    result.put(groupB, groupBMap);
                }
                Double value = iterator.next();
                groupAMap.put(groupB, value);
                groupBMap.put(groupA, 1 - value);
            }
        }

        inputFile.delete();
        
        Files.append("\n" + Joiner.on(" - ").join(keys), outputFile, Charset.defaultCharset());

        return result;
    }

    public static String interpretEffectSize(double effectSize) {
        effectSize = StrictMath.abs(effectSize - 0.5) * 2;
        if (effectSize < 0.147) {
            return NEGLIGIBLE;
        } else if (effectSize < 0.33) {
            return SMALL;
        } else if (effectSize < 0.474) {
            return MEDIUM;
        } else {
            return LARGE;
        }
    }
}
