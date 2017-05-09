package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Giovani Guizzo
 */
public class ResultWrapperTest {

    public ResultWrapperTest() {
    }

    @Test
    public void testGettersAndSetters() {
        ResultWrapper resultWrapper = new ResultWrapper();
        ArrayList<VariableLengthSolution<Integer>> arrayList = new ArrayList<>();
        resultWrapper.setExecutionTimeInMillis(100)
                .setResult(arrayList)
                .setSession("test")
                .setGrammarFile("test2")
                .setRunNumber(1)
                .setObjectiveFunctions(Lists.newArrayList("Test", "test2"));

        assertEquals("test", resultWrapper.getSession());
        assertEquals("test2", resultWrapper.getGrammarFile());
        assertEquals(arrayList, resultWrapper.getResult());
        assertEquals(100L, resultWrapper.getExecutionTimeInMillis());
        assertEquals(1, resultWrapper.getRunNumber());
        Assert.assertArrayEquals(new Object[]{"Test", "Test2"}, Lists.newArrayList("Test", "Test2").toArray());
    }

}
