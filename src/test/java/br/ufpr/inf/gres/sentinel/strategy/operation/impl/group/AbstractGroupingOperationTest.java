package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group;

import com.google.common.collect.Lists;
import java.util.*;
import java.util.function.Function;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Giovani Guizzo
 */
public class AbstractGroupingOperationTest {

    public AbstractGroupingOperationTest() {
    }

    @Test
    public void testDoOperation() {
        AbstractGroupingOperation<String> function = new StubGroupingOperation();
        Collection<Collection<String>> result = function.doOperation(Collections.emptyList(), null);

        assertEquals(0, result.size());
    }

    @Test
    public void testDoOperation2() {
        AbstractGroupingOperation<String> function = new StubGroupingOperation();
        Collection<Collection<String>> result = function.doOperation(Lists.newArrayList("1", "12", "12", ""), null);

        List<Collection<String>> tempResult = new ArrayList<>(result);
        assertEquals(3, tempResult.size());
        assertEquals(1, tempResult.get(0).size());
        assertEquals("", tempResult.get(0).iterator().next());
        assertEquals(1, tempResult.get(1).size());
        assertEquals("1", tempResult.get(1).iterator().next());
        assertEquals(2, tempResult.get(2).size());

        Iterator<String> iterator = tempResult.get(2).iterator();
        assertEquals("12", iterator.next());
        assertEquals("12", iterator.next());
    }

    private static class StubGroupingOperation extends AbstractGroupingOperation<String> {

        public StubGroupingOperation() {
            super("Stub Grouping");
        }

        @Override
        public Function<String, ?> createGroupingFunction() {
            return String::length;
        }

        @Override
        public boolean isSpecific() {
            return false;
        }
    }

}
