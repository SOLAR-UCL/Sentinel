package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.impl.CachedObjectiveFunctionObserver;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class CacheProfiling {

    private static final Logger LOGGER = LogManager.getLogger(CacheProfiling.class);

    private static CachedFacade facade;
    private static Program programUnderTest;

    @BeforeClass
    public static void setUpClass() {
        LOGGER.debug("Initializing CachedFacadeTest.");
        String directory = System.getProperty("user.dir") + File.separator + "training";
        facade = new CachedFacade(new PITFacade(directory), "src" + File.separator + "test" + File.separator + "resources", "src" + File.separator + "test" + File.separator + "resources");
        programUnderTest = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;");
        LOGGER.debug("Initializing program.");
        facade.initializeConventionalStrategy(programUnderTest, 5);
    }

    @Test
    @Ignore
    public void profiling() throws IOException {
        LOGGER.debug("Testing method: profiling");
        String directory = System.getProperty("user.dir") + File.separator + "training";
        CachedFacade facade = new CachedFacade(new PITFacade(directory), directory, null);
        IntegrationFacade.setIntegrationFacade(facade);

        CachedObjectiveFunctionObserver observer = new CachedObjectiveFunctionObserver();
        facade.attachObserver(observer);

        MutationStrategyGenerationProblem problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                10,
                15,
                1,
                10,
                10,
                10,
                1,
                Lists.newArrayList(new Program("joda-time-2.8.1", "")),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
        problem.dettachAllObservers();
        problem.attachObserver(observer);

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(93, 72, 127, 152, 87, 91, 108, 82, 22, 176, 71, 66, 45, 69, 139, 108, 178, 134, 145, 98, 0, 7, 68, 87, 9, 151, 177, 108, 101, 20, 11, 30, 94, 171, 142, 131, 5, 17, 26, 71, 162, 171, 172, 138, 175, 51, 173, 24, 45, 35, 6, 12, 53, 160));

        problem.evaluate(solution);
        problem.evaluate(solution);
        problem.evaluate(solution);
        problem.evaluate(solution);
        problem.evaluate(solution);

        facade.dettachObserver(observer);
    }

    @Test
    @Ignore
    public void profiling2() throws IOException {
        LOGGER.debug("Testing method: profiling2");
        String directory = System.getProperty("user.dir") + File.separator + "training";
        CachedFacade facade = new CachedFacade(new PITFacade(directory), "src" + File.separator + "test" + File.separator + "resources", null);
        IntegrationFacade.setIntegrationFacade(facade);

        CachedObjectiveFunctionObserver observer = new CachedObjectiveFunctionObserver();
        facade.attachObserver(observer);

        MutationStrategyGenerationProblem problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                10,
                15,
                1,
                10,
                0,
                5,
                1,
                Lists.newArrayList(new Program("wire", "")),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
        problem.dettachAllObservers();
        problem.attachObserver(observer);

        List<VariableLengthSolution<Integer>> allSolutions = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            VariableLengthSolution<Integer> solution = problem.createSolution();
            solution.clearVariables();
            solution.addAllVariables(Lists.newArrayList(124, 20, 75, 14, 126, 47, 163, 96, 95, 6, 119, 165, 74, 123, 54, 70, 173, 116, 22, 121, 57, 109, 34, 89, 153, 95, 96, 107, 26, 50, 102, 160, 171, 9, 96, 95, 6, 119, 165, 74, 123, 54, 70, 173, 116, 22, 121, 57, 109, 34, 89, 153, 95, 96));
            allSolutions.add(solution);
            problem.evaluate(solution);
        }

        facade.dettachObserver(observer);
    }

}
