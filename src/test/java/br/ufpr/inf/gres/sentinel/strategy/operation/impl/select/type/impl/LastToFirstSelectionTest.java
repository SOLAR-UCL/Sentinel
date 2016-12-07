package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import com.google.common.collect.Lists;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class LastToFirstSelectionTest {

    public LastToFirstSelectionTest() {
    }

    @Test
    public void testSelectItems() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        List<Integer> result = lastToFirst.selectItems(input, 0);
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectItems2() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        List<Integer> result = lastToFirst.selectItems(input, 1);
        assertEquals(1, result.size());
        assertEquals((int) 1, (int) result.get(0));
    }

    @Test
    public void testSelectItems3() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        List<Integer> result = lastToFirst.selectItems(input, 2);
        assertEquals(2, result.size());
        assertEquals((int) 1, (int) result.get(0));
        assertEquals((int) 6, (int) result.get(1));
    }

    @Test
    public void testSelectItems4() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        List<Integer> result = lastToFirst.selectItems(input, 3);
        assertEquals(3, result.size());
        assertEquals((int) 1, (int) result.get(0));
        assertEquals((int) 6, (int) result.get(1));
        assertEquals((int) 2, (int) result.get(2));
    }

    @Test
    public void testSelectItems5() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        List<Integer> result = lastToFirst.selectItems(input, 6);
        assertEquals(6, result.size());
        assertEquals((int) 1, (int) result.get(0));
        assertEquals((int) 6, (int) result.get(1));
        assertEquals((int) 2, (int) result.get(2));
        assertEquals((int) 5, (int) result.get(3));
        assertEquals((int) 3, (int) result.get(4));
        assertEquals((int) 4, (int) result.get(5));
    }

    @Test
    public void testSelectItems6() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        List<Integer> result = lastToFirst.selectItems(input, 9);
        assertEquals(9, result.size());
        assertEquals((int) 1, (int) result.get(0));
        assertEquals((int) 6, (int) result.get(1));
        assertEquals((int) 2, (int) result.get(2));
        assertEquals((int) 5, (int) result.get(3));
        assertEquals((int) 3, (int) result.get(4));
        assertEquals((int) 4, (int) result.get(5));
        assertEquals((int) 1, (int) result.get(6));
        assertEquals((int) 6, (int) result.get(7));
        assertEquals((int) 2, (int) result.get(8));
    }

    @Test
    public void testSelectItems7() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5);
        LastToFirstSelection<Integer> lastToFirst = new LastToFirstSelection();
        List<Integer> result = lastToFirst.selectItems(input, 10);
        assertEquals(10, result.size());
        assertEquals((int) 1, (int) result.get(0));
        assertEquals((int) 5, (int) result.get(1));
        assertEquals((int) 2, (int) result.get(2));
        assertEquals((int) 4, (int) result.get(3));
        assertEquals((int) 3, (int) result.get(4));
        assertEquals((int) 5, (int) result.get(5));
        assertEquals((int) 1, (int) result.get(6));
        assertEquals((int) 4, (int) result.get(7));
        assertEquals((int) 2, (int) result.get(8));
        assertEquals((int) 5, (int) result.get(9));
    }

}
