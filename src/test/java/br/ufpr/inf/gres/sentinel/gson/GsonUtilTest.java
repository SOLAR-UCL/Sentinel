package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.gson.ResultWrapper;
import br.ufpr.inf.gres.sentinel.gson.GsonUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class GsonUtilTest {

    public GsonUtil gson;

    public GsonUtilTest() throws IOException {
        gson = new GsonUtil();
    }

    @Test
    public void testSerialize() throws IOException {
        Assert.assertEquals("{\n"
                + "  \"executionTimeInMillis\": 0\n"
                + "}", gson.toJson(new ResultWrapper()));
    }

    @Test
    public void testSerialize2() throws IOException {
        ResultWrapper resultWrapper = new ResultWrapper()
                .setExecutionTimeInMillis(10)
                .setGrammarFile("testGrammar")
                .setResult(new ArrayList<>())
                .setSession("testSession");
        Assert.assertEquals("{\n"
                + "  \"session\": \"testSession\",\n"
                + "  \"grammarFile\": \"testGrammar\",\n"
                + "  \"executionTimeInMillis\": 10,\n"
                + "  \"result\": []\n"
                + "}", gson.toJson(resultWrapper));
    }

    @Test
    public void testDeserialize() throws IOException {
        ResultWrapper fromJson = gson.fromJson("src"
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
        ResultWrapper fromJson = gson.fromJson("src"
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

}
