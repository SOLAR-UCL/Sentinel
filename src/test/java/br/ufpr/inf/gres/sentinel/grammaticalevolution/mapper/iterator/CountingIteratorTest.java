package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.iterator;

import com.google.common.collect.Iterators;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class CountingIteratorTest {

    public CountingIteratorTest() {
    }

    @Test
    public void testIterator() {
        CountingIterator<Integer> iterator = new CountingIterator<>(Iterators.forArray(1, 2, 3, 4, 5));
        while (iterator.hasNext()) {
            iterator.next();
        }
        Assert.assertEquals(5, iterator.getCount());
    }

    @Test
    public void testIterator2() {
        CountingIterator<Integer> iterator = new CountingIterator<>(Iterators.forArray());
        while (iterator.hasNext()) {
            iterator.next();
        }
        Assert.assertEquals(0, iterator.getCount());
    }

}
