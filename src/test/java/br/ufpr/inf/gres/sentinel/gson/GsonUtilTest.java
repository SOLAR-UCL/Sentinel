package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator.GroupOperatorsByType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.GroupSelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.QuantityInGroupComparator;
import br.ufpr.inf.gres.sentinel.util.TestPrograms;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class GsonUtilTest {

    public GsonUtil gson;
    private static MutationStrategyGenerationProblem problem;

    public GsonUtilTest() throws IOException {
        IntegrationFacade facade = IntegrationFacadeFactory.createIntegrationFacade("PIT",
                System.getProperty("user.dir") + File.separator + "training");
        IntegrationFacade.setIntegrationFacade(facade);
        problem = new MutationStrategyGenerationProblem(GrammarFiles.getGrammarPath(GrammarFiles.DEFAULT_GRAMMAR),
                15,
                100,
                0,
                179,
                10,
                5,
                1,
                Lists.newArrayList(facade.instantiateProgram(TestPrograms.TRIANGLE)),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
        this.gson = new GsonUtil(problem);
    }

    @Test
    public void testDeserialize() throws IOException {
        ResultWrapper fromJson = this.gson.fromJson("src"
                + File.separator
                + "test"
                + File.separator
                + "resources"
                + File.separator
                + "test.json");
        Assert.assertEquals(3000, fromJson.getExecutionTimeInMillis());
    }

    @Test
    public void testDeserialize2() throws IOException {
        ResultWrapper fromJson = this.gson.fromJson("src"
                + File.separator
                + "test"
                + File.separator
                + "resources"
                + File.separator
                + "test2.json");
        Assert.assertEquals(10, fromJson.getExecutionTimeInMillis());
        Assert.assertEquals("testGrammar", fromJson.getGrammarFile());
        Assert.assertEquals("testSession", fromJson.getSession());
        Assert.assertArrayEquals(new Object[]{}, fromJson.getResult().toArray());
    }

    @Test
    public void testSerialize() throws IOException {
        Assert.assertEquals("{\n"
                + "  \"runNumber\": 0,\n"
                + "  \"executionTimeInMillis\": 0\n"
                + "}", this.gson.toJson(new ResultWrapper()));
    }

    @Test
    public void testSerialize2() throws IOException {
        ResultWrapper resultWrapper = new ResultWrapper()
                .setExecutionTimeInMillis(10)
                .setGrammarFile("testGrammar")
                .setResult(new ArrayList<>())
                .setSession("testSession")
                .setRunNumber(12)
                .setObjectiveFunctions(Lists.newArrayList("Test", "Test2"));
        Assert.assertEquals("{\n"
                + "  \"session\": \"testSession\",\n"
                + "  \"runNumber\": 12,\n"
                + "  \"grammarFile\": \"testGrammar\",\n"
                + "  \"executionTimeInMillis\": 10,\n"
                + "  \"objectiveFunctions\": [\n"
                + "    \"Test\",\n"
                + "    \"Test2\"\n"
                + "  ],\n"
                + "  \"result\": []\n"
                + "}", this.gson.toJson(resultWrapper));
    }

    @Test
    @Ignore
    public void testSerialize3() throws IOException {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupOperatorsByType());
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new QuantityInGroupComparator());
        groupOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp2 = new GroupSelectionOperation<>();
        groupOp2.setSelectionOperation(groupOp);
        groupOp2.setGroupingFunction(new GroupOperatorsByType());
        groupOp2.setSelectionType(new SequentialSelection());
        groupOp2.setSorter(new QuantityInGroupComparator());
        groupOp2.setQuantity(1);

//        Assert.assertEquals("", gson.getGson().toJson(groupOp2));
    }

}
