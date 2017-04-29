package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.OperationTest;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import org.junit.*;

/**
 *
 * @author Giovani Guizzo
 */
public class VariableLengthSolutionGsonSerializerTest {

    private static MutationStrategyGenerationProblem problem;
    private static IntegrationFacade facade;
    private static IntegrationFacade oldFacade;

    @BeforeClass
    public static void setUpClass() throws IOException {
        facade = IntegrationFacadeFactory.createIntegrationFacade("PIT",
                System.getProperty("user.dir") + File.separator + "training");
        oldFacade = IntegrationFacade.getIntegrationFacade();
        IntegrationFacade.setIntegrationFacade(facade);

        problem = new MutationStrategyGenerationProblem(GrammarFiles.getGrammarPath(GrammarFiles.DEFAULT_GRAMMAR_NO_HOMS),
                15,
                100,
                0,
                179,
                10,
                5,
                Lists.newArrayList(facade.instantiateProgram("br.ufpr.inf.gres.TriTyp")));
    }

    @AfterClass
    public static void tearDownClass() {
        IntegrationFacade.setIntegrationFacade(oldFacade);
    }

    @Test
    public void testSerialize() throws IOException {
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 3));
        solution.setObjective(0, 1000);
        solution.setObjective(1, 2000);
        solution.setAttribute("Strategy", new Strategy(OperationTest.getComplexTestOperationChain()));
        solution.setAttribute("Quantity", 3000);
        solution.setAttribute("Evaluation", 100);
        solution.setAttribute("Consumed Items Count", 2);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonSerializer())
                .registerTypeAdapter(Operation.class, new OperationSerializer())
                .create();
        String result = gson.toJson(solution);
        Assert.assertEquals("{\"objectives\":[1000.0,2000.0],\"quantity\":3000,\"evaluation\":100,\"consumedItemsCount\":2,\"variables\":[0,2,1,0,0,1,9,3],\"strategy\":{\"firstOperation\":{\"name\":\"TestOperation\",\"successor\":{\"name\":\"TestOperation2\",\"successor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"TestOperation3\"},\"secondSuccessor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"TestOperation5\"},\"secondSuccessor\":{\"name\":\"TestOperation6\"}},\"secondSuccessor\":{\"name\":\"TestOperation4\"}}}}}}}", result);
    }

    @Test
    public void testSerialize2() throws IOException {
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 3));
        solution.setObjective(0, 1000);
        solution.setObjective(1, 2000);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonSerializer())
                .create();
        Assert.assertEquals("{\"objectives\":[1000.0,2000.0],\"variables\":[0,2,1,0,0,1,9,3]}", gson.toJson(solution));
    }

    @Test
    public void testSerialize3() throws IOException {
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonSerializer())
                .create();
        Assert.assertEquals("{\"objectives\":[0.0,0.0],\"variables\":[]}", gson.toJson(solution));
    }

    @Test
    @Ignore
    public void testSerialize4() throws IOException {
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(152, 152, 44, 123, 144, 11, 161, 16, 154, 103, 91, 37, 177, 124, 165));
        problem.evaluate(solution);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonSerializer())
                .registerTypeAdapter(Operation.class, new OperationSerializer())
                .setPrettyPrinting()
                .create();
        System.out.println(gson.toJson(solution));
    }

}
