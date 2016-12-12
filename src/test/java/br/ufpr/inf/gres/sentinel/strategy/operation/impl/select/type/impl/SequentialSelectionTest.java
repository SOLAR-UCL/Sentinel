package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class SequentialSelectionTest {

    public SequentialSelectionTest() {
    }

    @Test
    public void testSelectItems() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        List<Integer> result = operation.selectItems(input, 0);
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectItems3() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        List<Integer> result = operation.selectItems(input, 1);
        assertEquals(1, result.size());
        assertEquals((int) 1, (int) result.get(0));
    }

    @Test
    public void testSelectItems4() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        List<Integer> result = operation.selectItems(input, 6);
        assertEquals(6, result.size());
        assertArrayEquals(input.toArray(), result.toArray());
    }

    @Test
    public void testSelectItems6() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        List<Integer> result = operation.selectItems(input, 13);
        assertEquals(13, result.size());
        assertEquals((int) 1, (int) result.get(0));
        assertEquals((int) 2, (int) result.get(1));
        assertEquals((int) 3, (int) result.get(2));
        assertEquals((int) 4, (int) result.get(3));
        assertEquals((int) 5, (int) result.get(4));
        assertEquals((int) 6, (int) result.get(5));
        assertEquals((int) 1, (int) result.get(6));
        assertEquals((int) 2, (int) result.get(7));
        assertEquals((int) 3, (int) result.get(8));
        assertEquals((int) 4, (int) result.get(9));
        assertEquals((int) 5, (int) result.get(10));
        assertEquals((int) 6, (int) result.get(11));
        assertEquals((int) 1, (int) result.get(12));
    }

    @Test
    public void testSelectItems7() {
        List<Integer> input = Lists.newArrayList(1);
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        List<Integer> result = operation.selectItems(input, 3);
        assertEquals(3, result.size());
        assertEquals((int) 1, (int) result.get(0));
        assertEquals((int) 1, (int) result.get(1));
        assertEquals((int) 1, (int) result.get(2));
    }

    @Test
    public void testSelectItems8() {
        List<Integer> input = new ArrayList<>();
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        List<Integer> result = operation.selectItems(input, 3);
        assertEquals(0, result.size());
    }

    @Test
    public void testIsSpecific() {
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        assertFalse(operation.isSpecific());
    }

}
