package br.ufpr.inf.gres.sentinel.main.script;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.StubProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.gson.GsonUtil;
import br.ufpr.inf.gres.sentinel.gson.ResultWrapper;
import br.ufpr.inf.gres.sentinel.integration.cache.FacadeCache;
import com.google.common.collect.ListMultimap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class TimeRQ3Analysis {

    public static void main(String[] args) {

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.DEBUG);
        ctx.updateLoggers();

        HashSet<String> systems = new LinkedHashSet<>();
        systems.add("commons-beanutils-1.8.0");
        systems.add("commons-beanutils-1.8.1");
        systems.add("commons-beanutils-1.8.2");
        systems.add("commons-beanutils-1.8.3");
        systems.add("commons-codec-1.11");
        systems.add("commons-codec-1.4");
        systems.add("commons-codec-1.5");
        systems.add("commons-codec-1.6");
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

        FacadeCache cache = new FacadeCache("./cache", "./cache_temp");
        Set<Program> cachedPrograms = cache.getCachedPrograms();

        HashMap<String, Long> trainingTimes = getTrainingTimes(cachedPrograms, cache);
        HashMap<String, Long> conventionalTimes = getConventionalTimes(systems);

        for (String program : trainingTimes.keySet()) {
            Long trainingTime = trainingTimes.get(program);
            Long conventionalTime = conventionalTimes.get(program);

            System.out.println(program + ": " + (trainingTime / conventionalTime));
        }

    }

    private static HashMap<String, Long> getTrainingTimes(Set<Program> cachedPrograms, FacadeCache cache) {
        HashMap<String, Long> trainingTimes = new LinkedHashMap<>();
        for (Program cachedProgram : cachedPrograms) {
            Long time = 0L;

            Collection<Double> cachedOperatorTimes = cache.getCachedOperatorsCPUTime(cachedProgram);
            for (Double opTime : cachedOperatorTimes) {
                time += TimeUnit.NANOSECONDS.toMillis(opTime.longValue());
            }

            Collection<Double> cachedMutantTimes = cache.getCachedMutantsCPUTime(cachedProgram);
            for (Double mutTime : cachedMutantTimes) {
                time += TimeUnit.NANOSECONDS.toMillis(mutTime.longValue());
            }
            System.out.println("Time: " + time);
            trainingTimes.put(cachedProgram.getName(), time);
        }
        return trainingTimes;
    }

    private static HashMap<String, Long> getConventionalTimes(HashSet<String> systems) {
        HashMap<String, Long> conventionalTimes = new LinkedHashMap<>();

        try {
            GsonUtil util = new GsonUtil(new StubProblem());
            for (String system : systems) {
                ListMultimap<String, ResultWrapper> resultsFromJson = util.getResultsFromJsonFiles("./testing/" + system, "**.json");
                List<Double> times = new ArrayList<>();
                for (Map.Entry<String, Collection<ResultWrapper>> entry : resultsFromJson.asMap().entrySet()) {
                    Collection<ResultWrapper> results = entry.getValue();
                    for (ResultWrapper result : results) {
                        long executionTimeInMillis = result.getExecutionTimeInMillis();
                        executionTimeInMillis /= 5;

                        double divideBy = 1;
                        for (VariableLengthSolution<Integer> solution : result.getResult()) {
                            double objective = solution.getObjective(0);
                            divideBy += objective;
                        }
                        times.add(executionTimeInMillis / divideBy);
                    }
                }
                long average = (long) times.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
                conventionalTimes.put(system, average);
            }
        } catch (IOException ex) {
        }

        return conventionalTimes;
    }

}
