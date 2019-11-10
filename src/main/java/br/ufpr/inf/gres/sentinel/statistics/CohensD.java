/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.statistics;

import com.google.common.base.Joiner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Giovani
 */
public class CohensD {

    public static final String NEGLIGIBLE = "negligible";
    public static final String SMALL = "small";
    public static final String MEDIUM = "medium";
    public static final String LARGE = "large";

    public static double test(double[] x, double[] y, boolean paired) throws IOException, InterruptedException {
        File outputFile = File.createTempFile("output", ".R");
        outputFile.deleteOnExit();
        double result = test(x, y, paired, outputFile);
        outputFile.delete();
        return result;
    }

    public static double test(double[] x, double[] y, boolean paired, File outputFile) throws IOException, InterruptedException {
        StringBuilder script = new StringBuilder();
        script.append("require(\"effsize\")\n")
                .append("x <- c(").append(Joiner.on(",").join(Arrays.stream(x).boxed().toArray())).append(")\n")
                .append("y <- c(").append(Joiner.on(",").join(Arrays.stream(y).boxed().toArray())).append(")\n")
                .append("result <- cohen.d(x,y,paired = ").append(Boolean.toString(paired).toUpperCase()).append(")\n")
                .append("result$estimate\n")
                .append("q()\n");

        File inputFile = File.createTempFile("script", ".R");
        inputFile.deleteOnExit();

        new FileWriter(inputFile).append(script).flush();

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(System.getProperty("os.name").contains("win") ? "R.exe" : "R", "--slave", "-f", inputFile.getAbsolutePath());
        builder.redirectOutput(outputFile);
        builder.start().waitFor();

        List<String> lines = FileUtils.readLines(outputFile);
        String[] result = lines.get(0).strip().split(" ");
        if (result.length == 2) {
            return Double.valueOf(result[1]);
        } else {
            return Double.NaN;
        }
    }
    
    public static String testForMagnitude(double[] x, double[] y, boolean paired) throws IOException, InterruptedException {
        File outputFile = File.createTempFile("output", ".R");
        outputFile.deleteOnExit();
        String result = testForMagnitude(x, y, paired, outputFile);
        outputFile.delete();
        return result;
    }

    public static String testForMagnitude(double[] x, double[] y, boolean paired, File outputFile) throws IOException, InterruptedException {
        StringBuilder script = new StringBuilder();
        script.append("require(\"effsize\")\n")
                .append("x <- c(").append(Joiner.on(",").join(Arrays.stream(x).boxed().toArray())).append(")\n")
                .append("y <- c(").append(Joiner.on(",").join(Arrays.stream(y).boxed().toArray())).append(")\n")
                .append("result <- cohen.d(x,y,paired = ").append(Boolean.toString(paired).toUpperCase()).append(")\n")
                .append("result$magnitude\n")
                .append("q()\n");

        File inputFile = File.createTempFile("script", ".R");
        inputFile.deleteOnExit();

        new FileWriter(inputFile).append(script).flush();

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(System.getProperty("os.name").contains("win") ? "R.exe" : "R", "--slave", "-f", inputFile.getAbsolutePath());
        builder.redirectOutput(outputFile);
        builder.start().waitFor();

        List<String> lines = FileUtils.readLines(outputFile);
        String[] result = lines.get(0).strip().split(" ");
        if (result.length == 2) {
            return result[1];
        } else {
            return "Unknown";
        }
    }

    public static String interpretEffectSize(double effectSize) {
        effectSize = StrictMath.abs(effectSize);
        if (effectSize < 0.2) {
            return NEGLIGIBLE;
        } else if (effectSize < 0.5) {
            return SMALL;
        } else if (effectSize < 0.8) {
            return MEDIUM;
        } else {
            return LARGE;
        }
    }
}
