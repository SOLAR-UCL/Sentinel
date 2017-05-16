package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class SequentialSelectionTest {

    public SequentialSelectionTest() {
    }

    @Test
    public void testIsSpecific() {
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        assertFalse(operation.isSpecific());
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
        assertEquals(1, (int) result.get(0));
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
        assertEquals(1, (int) result.get(0));
        assertEquals(2, (int) result.get(1));
        assertEquals(3, (int) result.get(2));
        assertEquals(4, (int) result.get(3));
        assertEquals(5, (int) result.get(4));
        assertEquals(6, (int) result.get(5));
        assertEquals(1, (int) result.get(6));
        assertEquals(2, (int) result.get(7));
        assertEquals(3, (int) result.get(8));
        assertEquals(4, (int) result.get(9));
        assertEquals(5, (int) result.get(10));
        assertEquals(6, (int) result.get(11));
        assertEquals(1, (int) result.get(12));
    }

    @Test
    public void testSelectItems7() {
        List<Integer> input = Lists.newArrayList(1);
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        List<Integer> result = operation.selectItems(input, 3);
        assertEquals(3, result.size());
        assertEquals(1, (int) result.get(0));
        assertEquals(1, (int) result.get(1));
        assertEquals(1, (int) result.get(2));
    }

    @Test
    public void testSelectItems8() {
        List<Integer> input = new ArrayList<>();
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        List<Integer> result = operation.selectItems(input, 3);
        assertEquals(0, result.size());
    }

}
