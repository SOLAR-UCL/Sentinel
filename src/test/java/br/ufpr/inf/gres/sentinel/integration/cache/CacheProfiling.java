package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.impl.CachedObjectiveFunctionObserver;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;
import br.ufpr.inf.gres.sentinel.util.TestPrograms;
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
        programUnderTest = facade.instantiateProgram(TestPrograms.TRIANGLE);
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
        solution.addAllVariables(Lists.newArrayList(130, 128, 11, 57, 147, 125, 80, 139, 45, 84, 44, 173, 136, 157, 25, 105, 97, 17, 128, 102, 145, 21, 146, 110, 87, 179, 29, 67, 32, 61, 125, 142, 64, 131, 174, 78, 32, 120, 0, 51, 159, 8, 163, 31, 162, 112, 109, 150, 117, 3, 112, 46, 128, 33, 121, 155, 174, 44, 79, 67, 164, 96, 63, 91, 105, 145, 139, 145, 164, 17, 78, 149, 127, 49, 149, 39, 72, 70, 68, 32, 110, 149, 177, 105, 25, 149, 48, 52, 41, 118, 124, 17, 96, 154, 90, 76, 150, 116, 95, 92));

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
