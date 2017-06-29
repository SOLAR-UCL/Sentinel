package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Giovani Guizzo
 */
public class LastToFirstSelectionTest {

    public LastToFirstSelectionTest() {
    }

    @Test
    public void testIsSpecific() {
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        assertFalse(lastToFirst.isSpecific());
    }

    @Test
    public void testSelectItems() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        Collection<Integer> result = lastToFirst.selectItems(input, 0);
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectItems2() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        Collection<Integer> result = lastToFirst.selectItems(input, 1);
        assertEquals(1, result.size());
        assertEquals(1, (int) result.iterator().next());
    }

    @Test
    public void testSelectItems3() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        Collection<Integer> result = lastToFirst.selectItems(input, 2);
        assertEquals(2, result.size());

        Iterator<Integer> iterator = result.iterator();
        assertEquals(1, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
    }

    @Test
    public void testSelectItems4() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        Collection<Integer> result = lastToFirst.selectItems(input, 3);
        assertEquals(3, result.size());

        Iterator<Integer> iterator = result.iterator();
        assertEquals(1, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
    }

    @Test
    public void testSelectItems5() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        Collection<Integer> result = lastToFirst.selectItems(input, 6);
        assertEquals(6, result.size());

        Iterator<Integer> iterator = result.iterator();
        assertEquals(1, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertEquals(5, (int) iterator.next());
        assertEquals(3, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
    }

    @Test
    public void testSelectItems6() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        Collection<Integer> result = lastToFirst.selectItems(input, 9);
        assertEquals(6, result.size());

        Iterator<Integer> iterator = result.iterator();
        assertEquals(1, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertEquals(5, (int) iterator.next());
        assertEquals(3, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
    }

    @Test
    public void testSelectItems7() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        Collection<Integer> result = lastToFirst.selectItems(input, 10);
        assertEquals(5, result.size());

        Iterator<Integer> iterator = result.iterator();
        assertEquals(1, (int) iterator.next());
        assertEquals(5, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(3, (int) iterator.next());
    }

    @Test
    public void testSelectItems8() {
        List<Integer> input = Lists.newArrayList(1);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        Collection<Integer> result = lastToFirst.selectItems(input, 3);
        assertEquals(1, result.size());

        Iterator<Integer> iterator = result.iterator();
        assertEquals(1, (int) iterator.next());
    }

    @Test
    public void testSelectItems9() {
        List<Integer> input = new ArrayList<>();
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        Collection<Integer> result = lastToFirst.selectItems(input, 3);
        assertEquals(0, result.size());
    }

}
