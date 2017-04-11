package br.ufpr.inf.gres.sentinel.main.cli.gson;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import br.ufpr.inf.gres.sentinel.strategy.operation.OperationTest;
import com.google.common.collect.Lists;
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

        VariableLengthSolutionGsonSerializer serializer = new VariableLengthSolutionGsonSerializer();
        Assert.assertEquals("{\"variables\":[0,2,1,0,0,1,9,3],\"objectives\":[1000.0,2000.0,3000.0],\"strategy\":[\"1.TestOperation - 2.TestOperation2 - 3.New Branch - 4.TestOperation3\",\"3.1.New Branch - 3.2.New Branch - 3.3.TestOperation5\",\"3.2.1.TestOperation6\",\"3.1.1.TestOperation4\"]}", serializer.serialize(solution, null, null).toString());
    }

    @Test
    public void testSerialize2() throws IOException {
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(0, 2, 1, 0, 0, 1, 9, 3));
        solution.setObjective(0, 1000);
        solution.setObjective(1, 2000);
        solution.setObjective(2, 3000);

        VariableLengthSolutionGsonSerializer serializer = new VariableLengthSolutionGsonSerializer();
        Assert.assertEquals("{\"variables\":[0,2,1,0,0,1,9,3],\"objectives\":[1000.0,2000.0,3000.0],\"strategy\":\"Invalid!\"}", serializer.serialize(solution, null, null).toString());
    }

    @Test
    public void testSerialize3() throws IOException {
        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();

        VariableLengthSolutionGsonSerializer serializer = new VariableLengthSolutionGsonSerializer();
        Assert.assertEquals("{\"variables\":[],\"objectives\":[0.0,0.0,0.0],\"strategy\":\"Invalid!\"}", serializer.serialize(solution, null, null).toString());
    }

}
