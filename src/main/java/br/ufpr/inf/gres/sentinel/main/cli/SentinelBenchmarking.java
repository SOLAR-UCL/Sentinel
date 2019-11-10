/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.main.cli.args.BenchmarkArgs;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 *
 * @author Giovani
 */
public class SentinelBenchmarking {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(SentinelBenchmarking.class);

    public static void benchmark(BenchmarkArgs benchmarkArgs, String[] args) throws IOException {
        if (benchmarkArgs.verbose) {
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration config = ctx.getConfiguration();
            LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
            loggerConfig.setLevel(Level.ALL);
            ctx.updateLoggers();
        }
        IntegrationFacade facade = buildFacade(benchmarkArgs);
        List<Program> trainingPrograms = facade.instantiatePrograms(benchmarkArgs.trainingPrograms);
        for (Program program : trainingPrograms) {
            LOGGER.info("Starting benchmark phase.");
            facade.initializeConventionalStrategy(program, benchmarkArgs.numberOfRuns);
            LOGGER.info("Finishing benchmark phase.");
            List<Long> executionTimes = facade.getConventionalExecutionTimes().get(program);
            storeExecutionTimeResults(executionTimes, benchmarkArgs, program.getName() + "_time.txt");
            executionTimes = facade.getConventionalExecutionCPUTimes().get(program);
            storeExecutionTimeResults(executionTimes, benchmarkArgs, program.getName() + "_cpu.txt");
        }
    }

    public static IntegrationFacade buildFacade(BenchmarkArgs benchmarkArgs) {
        IntegrationFacade facade
                = IntegrationFacadeFactory.createIntegrationFacade(benchmarkArgs.facade,
                        benchmarkArgs.workingDirectory
                        + File.separator
                        + benchmarkArgs.inputDirectory);

        IntegrationFacade.setIntegrationFacade(facade);
        return facade;
    }

    private static void storeExecutionTimeResults(List<Long> executionTimes, BenchmarkArgs benchmarkArgs, String fileName) throws IOException {
        File outputFile = new File(benchmarkArgs.workingDirectory
                + File.separator
                + benchmarkArgs.outputDirectory
                + File.separator
                + fileName);
        LOGGER.info("Storing results to " + outputFile.getAbsolutePath());
        Files.createParentDirs(outputFile);
        try (FileWriter writer = new FileWriter(outputFile)) {
            for (Long executionTime : executionTimes) {
                LOGGER.debug(executionTime + "ns\n");
                writer.append(DurationFormatUtils.formatDurationHMS(TimeUnit.NANOSECONDS.toMillis(executionTime)) + " (" + executionTime + " ns)\n");
            }
            final long average = (long) executionTimes.stream().mapToDouble(Long::doubleValue).average().getAsDouble();
            LOGGER.debug(average + "ns avg\n");
            writer.append("Average: " + DurationFormatUtils.formatDurationHMS(TimeUnit.NANOSECONDS.toMillis(average)) + " (" + average + " ns)\n");
            writer.flush();
        }
    }

}
