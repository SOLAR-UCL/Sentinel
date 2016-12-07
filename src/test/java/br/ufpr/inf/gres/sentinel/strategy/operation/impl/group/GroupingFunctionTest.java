package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class GroupingFunctionTest {

    public GroupingFunctionTest() {
    }

    @Test
    public void testDoOperation() {
        GroupingFunction<String> function = new GroupingFunction<>("Stub", false, String::length);
        List<List<String>> result = function.doOperation(Collections.emptyList());

        assertEquals(0, result.size());
    }

    @Test
    public void testDoOperation2() {
        GroupingFunction<String> function = new GroupingFunction<>("Stub", false, String::length);
        List<List<String>> result = function.doOperation(Lists.newArrayList("1", "12", "12", ""));

        assertEquals(3, result.size());
        assertEquals(1, result.get(0).size());
        assertEquals("", result.get(0).get(0));
        assertEquals(1, result.get(1).size());
        assertEquals("1", result.get(1).get(0));
        assertEquals(2, result.get(2).size());
        assertEquals("12", result.get(2).get(0));
        assertEquals("12", result.get(2).get(1));
    }

}
