package br.ufpr.inf.gres.sentinel.main.cli.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

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
                .setGrammarFile("test2");

        Assert.assertEquals("test", resultWrapper.getSession());
        Assert.assertEquals("test2", resultWrapper.getGrammarFile());
        Assert.assertEquals(arrayList, resultWrapper.getResult());
        Assert.assertEquals(100L, resultWrapper.getExecutionTimeInMillis());
    }

}
