package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import com.google.common.collect.Lists;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class RandomSelectionTest {

    public RandomSelectionTest() {
    }

    @Test
    public void testSelectItems() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        RandomSelection<Integer> operation = new RandomSelection<>();
        List<Integer> result = operation.selectItems(input, 0);
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectItems2() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        RandomSelection<Integer> operation = new RandomSelection<>();
        List<Integer> result = operation.selectItems(input, 1);
        assertEquals(1, result.size());
    }

    @Test
    public void testSelectItems3() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        RandomSelection<Integer> operation = new RandomSelection<>();
        List<Integer> result = operation.selectItems(input, -1);
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectItems4() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        RandomSelection<Integer> operation = new RandomSelection<>();
        List<Integer> result = operation.selectItems(input, 6);
        assertEquals(6, result.size());
    }

    @Test
    public void testSelectItems5() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        RandomSelection<Integer> operation = new RandomSelection<>();
        List<Integer> result = operation.selectItems(input, 7);
        assertEquals(7, result.size());
    }

}
