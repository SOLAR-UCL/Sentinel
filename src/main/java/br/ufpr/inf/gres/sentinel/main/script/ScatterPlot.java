/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.main.script;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.StubProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.gson.GsonUtil;
import br.ufpr.inf.gres.sentinel.gson.ResultWrapper;
import br.ufpr.inf.gres.sentinel.integration.cache.FacadeCache;
import com.google.common.collect.ListMultimap;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author ucacggu
 */
public class ScatterPlot {

    public static void main(String[] args) {
        try {
            ArrayList<String> systems = new ArrayList<>();
            systems.add("commons-beanutils-1.8.0");
            systems.add("commons-beanutils-1.8.1");
            systems.add("commons-beanutils-1.8.2");
            systems.add("commons-beanutils-1.8.3");
            systems.add("commons-codec-1.4");
            systems.add("commons-codec-1.5");
            systems.add("commons-codec-1.6");
            systems.add("commons-codec-1.11");
            systems.add("commons-collections-3.0");
            systems.add("commons-collections-3.1");
            systems.add("commons-collections-3.2");
            systems.add("commons-collections-3.2.1");
            systems.add("commons-lang-3.0");
            systems.add("commons-lang-3.0.1");
            systems.add("commons-lang-3.1");
            systems.add("commons-lang-3.2");
            systems.add("commons-validator-1.4.0");
            systems.add("commons-validator-1.4.1");
            systems.add("commons-validator-1.5.0");
            systems.add("commons-validator-1.5.1");
            systems.add("jfreechart-1.0.0");
            systems.add("jfreechart-1.0.1");
            systems.add("jfreechart-1.0.2");
            systems.add("jfreechart-1.0.3");
            systems.add("jgrapht-0.9.0");
            systems.add("jgrapht-0.9.1");
            systems.add("jgrapht-0.9.2");
            systems.add("jgrapht-1.0.0");
            systems.add("joda-time-2.8");
            systems.add("joda-time-2.8.1");
            systems.add("joda-time-2.8.2");
            systems.add("joda-time-2.9");
            systems.add("ognl-3.1");
            systems.add("ognl-3.1.1");
            systems.add("ognl-3.1.2");
            systems.add("ognl-3.1.3");
            systems.add("wire-2.0.0");
            systems.add("wire-2.0.1");
            systems.add("wire-2.0.2");
            systems.add("wire-2.0.3");

            System.out.println("cd 'C:\\Users\\ucacggu\\Documents\\NetBeansProjects\\Sentinel\\testing'");
            System.out.println("set xlabel 'Percentage of Mutants'");
            System.out.println("set ylabel 'CPU Time'");
            System.out.println("set xrange [0:1]");
            System.out.println("set yrange [0:1]");
            System.out.println("set xtics 0.1");
            System.out.println("set ytics 0.1");
            System.out.println("set grid");
            System.out.println("set key inside left");
            System.out.println("set terminal pngcairo enhanced size 800,600 font 'Verdana,12'");
            System.out.println("f(x) = x");

            FacadeCache cache = new FacadeCache("./cache", "./cache_temp");
            GsonUtil util = new GsonUtil(new StubProblem());
            FileWriter allRMSWritter = new FileWriter("./testing/ALL_RMS.dat");
            FileWriter allSMWritter = new FileWriter("./testing/ALL_SM.dat");
            for (int i = 0; i < systems.size(); i++) {
                String system = systems.get(i);
                String trainingSystem = systems.get((i / 4) * 4);
                ListMultimap<String, ResultWrapper> rmsResults = util.getResultsFromJsonFiles("./testing/" + system + "/RandomMutantSampling", "**.json");
                FileWriter rmsWritter = new FileWriter("./testing/" + system + "_RMS.dat");
                for (Map.Entry<String, ResultWrapper> entry : rmsResults.entries()) {
                    for (VariableLengthSolution<Integer> solution : entry.getValue().getResult()) {
                        String name = solution.getAttribute("Name").toString();
                        String percentage = name.substring(name.indexOf("_") + 1);
                        String rmsRow = "0." + percentage + " " + solution.getObjective(0) + "\n";
                        rmsWritter.append(rmsRow);
                        allRMSWritter.append(rmsRow);
                    }
                }
                rmsWritter.close();
                ListMultimap<String, ResultWrapper> smResults = util.getResultsFromJsonFiles("./testing/" + system + "/SelectiveMutation", "**.json");
                FileWriter smWritter = new FileWriter("./testing/" + system + "_SM.dat");
                Program cachedProgram = cache.getCachedPrograms().stream().filter(program -> program.getName().equals(trainingSystem)).findFirst().get();
                Collection<Operator> cachedOperators = cache.getOperators(cachedProgram).stream()
                        .sorted((o1, o2) -> o2.getGeneratedMutants().size() - o1.getGeneratedMutants().size())
                        .collect(Collectors.toList());
                int mutantsSize = cache.getMutants(cachedProgram).size();
                for (Map.Entry<String, ResultWrapper> entry : smResults.entries()) {
                    List<VariableLengthSolution<Integer>> result = entry.getValue().getResult().stream()
                            .filter(solution
                                    -> solution.getObjective(0) > 0.0 && solution.getObjective(0) <= 1.0
                            && Math.abs(solution.getObjective(1)) > 0.0 && Math.abs(solution.getObjective(1)) <= 1.0)
                            .collect(Collectors.toList());
                    for (VariableLengthSolution<Integer> solution : result) {
                        String name = solution.getAttribute("Name").toString();
                        String percentage = name.substring(name.indexOf("_") + 1);
                        String smValue = ((double) cachedOperators.stream().skip(Integer.valueOf(percentage)).mapToDouble(op -> op.getGeneratedMutants().size()).sum() / (double) mutantsSize) + " " + solution.getObjective(0) + "\n";
                        smWritter.append(smValue);
                        allSMWritter.append(smValue);
                    }
                }
                smWritter.close();
                System.out.println("set output 'plots/scatter/" + system + ".png'");
                System.out.println("plot '" + system + "_RMS.dat' pt 12 title 'Random Mutant Sampling', '" + system + "_SM.dat' pt 1 title 'Selective Mutation', f(x) dt 5 linecolor black title 'Theoretical Fit'");
            }
            allRMSWritter.close();
            allSMWritter.close();
            System.out.println("set output 'plots/scatter/ALL.png'");
            System.out.println("plot 'ALL_RMS.dat' pt 12 title 'Random Mutant Sampling', 'ALL_SM.dat' pt 12 title 'Selective Mutation', f(x) dt 5 linecolor black title 'Theoretical Fit'");
        } catch (IOException ex) {
            Logger.getLogger(ScatterPlot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
