/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.main.cli.converter;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.impl.CachedObjectiveFunctionObserver;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.integration.cache.CachedFacade;
import br.ufpr.inf.gres.sentinel.main.cli.args.CacheArgs;
import java.io.File;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 *
 * @author Giovani
 */
public class SentinelCaching {

    public static void cache(CacheArgs cacheArgs, String[] args) {
        if (cacheArgs.verbose) {
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration config = ctx.getConfiguration();
            LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
            loggerConfig.setLevel(Level.ALL);
            ctx.updateLoggers();
        }
        IntegrationFacade facade = buildFacade(cacheArgs);
        List<Program> trainingPrograms = facade.instantiatePrograms(cacheArgs.trainingPrograms);
        for (Program program : trainingPrograms) {
            facade.initializeConventionalStrategy(program, cacheArgs.numberOfRuns);
        }
    }

    public static IntegrationFacade buildFacade(CacheArgs cacheArgs) {
        IntegrationFacade facade
                = IntegrationFacadeFactory.createIntegrationFacade(cacheArgs.facade,
                        cacheArgs.workingDirectory
                        + File.separator
                        + cacheArgs.inputDirectory);
        CachedFacade cachedFacade = new CachedFacade(facade,
                null,
                cacheArgs.workingDirectory
                + File.separator
                + cacheArgs.outputDirectory);
        cachedFacade.attachObserver(new CachedObjectiveFunctionObserver());
        facade = cachedFacade;

        facade.setJavaExecutablePath(cacheArgs.jvmPath);

        IntegrationFacade.setIntegrationFacade(facade);
        return facade;
    }

}
