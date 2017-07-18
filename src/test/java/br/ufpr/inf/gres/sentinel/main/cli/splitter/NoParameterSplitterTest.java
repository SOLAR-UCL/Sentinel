/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.main.cli.splitter;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Giovani
 */
public class NoParameterSplitterTest {

    public NoParameterSplitterTest() {
    }

    @Test
    public void testSplit() {
        NoParameterSplitter splitter = new NoParameterSplitter();
        List<String> splitResult = splitter.split("test, test test");
        assertEquals(1, splitResult.size());
        assertEquals("test, test test", splitResult.get(0));
        splitResult = splitter.split("test");
        assertEquals(1, splitResult.size());
        assertEquals("test", splitResult.get(0));
    }

}
