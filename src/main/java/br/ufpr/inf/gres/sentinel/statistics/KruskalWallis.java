package br.ufpr.inf.gres.sentinel.statistics;

import com.google.common.base.Joiner;
import com.google.common.collect.ListMultimap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author giovani
 */
public class KruskalWallis {

    public static HashMap<String, HashMap<String, Boolean>> test(ListMultimap<String, Double> values) throws IOException, InterruptedException {
        File outputFile = File.createTempFile("output", ".R");
        outputFile.deleteOnExit();
        HashMap<String, HashMap<String, Boolean>> result = test(values, outputFile);
        outputFile.delete();
        return result;
    }

    public static HashMap<String, HashMap<String, Boolean>> test(ListMultimap<String, Double> values, File outputFile) throws IOException, InterruptedException {
        StringBuilder scriptBuilder = new StringBuilder();
        scriptBuilder.append("require(pgirmess)\n")
                .append("ARRAY <- c(");
        Joiner.on(",").appendTo(scriptBuilder, values.values());
        scriptBuilder.append(")\n")
                .append("categs <- as.factor(c(\"");
        Joiner.on("\",\"").appendTo(scriptBuilder, values.keys());
        scriptBuilder.append("\"))\n")
                .append("teste <- kruskal.test(ARRAY,categs)\n")
                .append("print(teste)\n")
                .append("pos_teste <- kruskalmc(ARRAY,categs)\n")
                .append("print(pos_teste)");

        File scriptFile = File.createTempFile("script", ".R");
        scriptFile.deleteOnExit();

        try (FileWriter scriptWriter = new FileWriter(scriptFile)) {
            scriptWriter.append(scriptBuilder.toString());
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                System.getProperty("os.name").contains("win") || System.getProperty("os.name").contains("Win") ? "R.exe" : "R",
                "--slave",
                "-f", scriptFile.getAbsolutePath());
        processBuilder.redirectOutput(outputFile);

        Process process = processBuilder.start();
        process.waitFor();

        ArrayList<Map.Entry<String, Collection<Double>>> entrySets = new ArrayList<>(values.asMap().entrySet());

        HashMap<String, HashMap<String, Boolean>> result = new HashMap<>();

        for (int i = 0; i < entrySets.size() - 1; i++) {
            String entry1 = entrySets.get(i).getKey();
            for (int j = i + 1; j < entrySets.size(); j++) {
                String entry2 = entrySets.get(j).getKey();

                try (Scanner scanner = new Scanner(outputFile)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains(entry1 + "-" + entry2)
                                || line.contains(entry2 + "-" + entry1)) {
                            HashMap<String, Boolean> entry1Map = result.get(entry1);
                            if (entry1Map == null) {
                                entry1Map = new HashMap<>();
                                result.put(entry1, entry1Map);
                            }
                            HashMap<String, Boolean> entry2Map = result.get(entry2);
                            if (entry2Map == null) {
                                entry2Map = new HashMap<>();
                                result.put(entry2, entry2Map);
                            }
                            if (line.contains("TRUE")) {
                                entry1Map.put(entry2, true);
                                entry2Map.put(entry1, true);
                                break;
                            } else if (line.contains("FALSE")) {
                                entry1Map.put(entry2, false);
                                entry2Map.put(entry1, false);
                                break;
                            }
                        }
                    }
                }
            }
        }

        scriptFile.delete();

        return result;
    }

}
