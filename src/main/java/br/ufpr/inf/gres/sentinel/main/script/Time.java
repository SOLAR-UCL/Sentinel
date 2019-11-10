package br.ufpr.inf.gres.sentinel.main.script;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.cache.FacadeCache;
import com.google.common.base.Joiner;
import com.google.common.math.Quantiles;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Time {

    public static void main(String[] args) {

        FacadeCache cache = new FacadeCache("./cache", "./cache_temp");
        Set<Program> cachedPrograms = cache.getCachedPrograms();
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Integer> sizes = new ArrayList<>();
        for (Program cachedProgram : cachedPrograms) {
            labels.add("\"" + cachedProgram.getName().substring(0, cachedProgram.getName().indexOf("-")) + "\"");
            Collection<Double> cachedMutantsCPUTime = cache.getCachedMutantsCPUTime(cachedProgram);
            sizes.add(cachedMutantsCPUTime.size());
            final double min = cachedMutantsCPUTime.stream().mapToDouble(value -> value).min().getAsDouble();
            final double max = cachedMutantsCPUTime.stream().mapToDouble(value -> value).max().getAsDouble();
//            double average = cachedMutantsCPUTime.stream().mapToDouble(value -> (double) ((double) value - (double) min) / (double) ((double) max - (double) min)).average().getAsDouble();
            double average = Quantiles.median().compute(cachedMutantsCPUTime.stream().mapToDouble(value -> (double) ((double) value - (double) min) / (double) ((double) max - (double) min)).boxed().collect(Collectors.toList()));
            System.out.println("res <- wilcox.test(scan(\"" + cachedProgram.getName() + ".txt\"), mu = " + String.format("%.10f", average) + "); format(round(res$p.value, 4), nsmall=4); res <- VD.A(scan(\"" + cachedProgram.getName() + ".txt\"), " + String.format("%.10f", average) + "); res$estimate;");
            try (FileWriter writer = new FileWriter("testing/" + cachedProgram.getName() + ".txt")) {
                for (Double value : cachedMutantsCPUTime) {
                    writer.write(String.format("%.10f", (double) ((double) value - (double) min) / (double) ((double) max - (double) min)) + "\n");
                }
            } catch (IOException ex) {
                Logger.getLogger(Time.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }

//        System.out.println("\nDistributon:\n");
//
//        for (Program program : cachedPrograms) {
//            System.out.println(program.getName().substring(0, program.getName().indexOf("-")) + " <- scan(\"" + program + ".txt\");");
//        }
//
//        System.out.println("labels <- rep(c(" + Joiner.on(",").join(labels) + "),c(" + Joiner.on(",").join(sizes) + "));");
//        System.out.println("times <- c("+Joiner.on(",").join(cachedPrograms.stream().map(program -> program.getName().substring(0, program.getName().indexOf("-"))).collect(Collectors.toList()))+");");
//        System.out.println("df <- data.frame(name = labels, time = times);");

    }

}
