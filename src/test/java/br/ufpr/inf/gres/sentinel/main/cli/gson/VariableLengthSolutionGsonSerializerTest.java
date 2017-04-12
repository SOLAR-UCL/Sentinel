package br.ufpr.inf.gres.sentinel.main.cli.gson;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.OperationTest;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class VariableLengthSolutionGsonSerializerTest {

    private static MutationStrategyGenerationProblem problem;

    @BeforeClass
    public static void setUpClass() throws IOException {
        problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                10,
                15,
                1,
                10,
                0,
                5,
                Lists.newArrayList(new Program("Test1", null),
                        new Program("Test2", null)));
    }

    @Test
    public void testSerialize() throws IOException {
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 3));
        solution.setObjective(0, 1000);
        solution.setObjective(1, 2000);
        solution.setObjective(2, 3000);
        solution.setAttribute("Strategy", new Strategy(OperationTest.getComplexTestOperationChain()));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonSerializer())
                .registerTypeAdapter(Operation.class, new OperationSerializer())
                .create();
        String result = gson.toJson(solution);
        Assert.assertEquals("{\"variables\":[0,2,1,0,0,1,9,3],\"objectives\":[1000.0,2000.0,3000.0],\"strategy\":{\"firstOperation\":{\"name\":\"TestOperation\",\"successor\":{\"name\":\"TestOperation2\",\"successor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"TestOperation3\"},\"secondSuccessor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"TestOperation5\"},\"secondSuccessor\":{\"name\":\"TestOperation6\"}},\"secondSuccessor\":{\"name\":\"TestOperation4\"}}}}}}}", result);
    }

    @Test
    public void testSerialize2() throws IOException {
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 3));
        solution.setObjective(0, 1000);
        solution.setObjective(1, 2000);
        solution.setObjective(2, 3000);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonSerializer())
                .create();
        Assert.assertEquals("{\"variables\":[0,2,1,0,0,1,9,3],\"objectives\":[1000.0,2000.0,3000.0]}", gson.toJson(solution));
    }

    @Test
    public void testSerialize3() throws IOException {
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonSerializer())
                .create();
        Assert.assertEquals("{\"variables\":[],\"objectives\":[0.0,0.0,0.0]}", gson.toJson(solution));
    }

}
