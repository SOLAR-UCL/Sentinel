package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
        Collection<Integer> result = operation.selectItems(input, 0);
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectItems3() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        Collection<Integer> result = operation.selectItems(input, 1);
        assertEquals(1, result.size());
        assertEquals(1, (int) result.iterator().next());
    }

    @Test
    public void testSelectItems4() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        Collection<Integer> result = operation.selectItems(input, 6);
        assertEquals(6, result.size());
        assertArrayEquals(input.toArray(), result.toArray());
    }

    @Test
    public void testSelectItems6() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        Collection<Integer> result = operation.selectItems(input, 13);
        assertEquals(6, result.size());

        Iterator<Integer> iterator = result.iterator();
        assertEquals(1, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertEquals(3, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(5, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
    }

    @Test
    public void testSelectItems7() {
        List<Integer> input = Lists.newArrayList(1);
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        Collection<Integer> result = operation.selectItems(input, 3);
        assertEquals(1, result.size());
        assertEquals(1, (int) result.iterator().next());
    }

    @Test
    public void testSelectItems8() {
        List<Integer> input = new ArrayList<>();
        SequentialSelection<Integer> operation = new SequentialSelection<>();
        Collection<Integer> result = operation.selectItems(input, 3);
        assertEquals(0, result.size());
    }

}
