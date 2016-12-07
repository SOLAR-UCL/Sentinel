package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort;

import com.google.common.collect.Lists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class AbstractSorterOperationTest {

    public AbstractSorterOperationTest() {
    }

    @Test
    public void testDoOperation() {
        AbstractSorterOperationStub stub = new AbstractSorterOperationStub();
        int result = stub.doOperation(Lists.newArrayList(1, 1));
        assertEquals(0, result);
    }

    @Test
    public void testDoOperation2() {
        AbstractSorterOperationStub stub = new AbstractSorterOperationStub();
        int result = stub.doOperation(Lists.newArrayList(1, 2));
        assertTrue(result < 0);
    }

    @Test
    public void testDoOperation3() {
        AbstractSorterOperationStub stub = new AbstractSorterOperationStub();
        int result = stub.doOperation(Lists.newArrayList(2, 1));
        assertTrue(result > 0);
    }

    @Test
    public void testReversed() {
        AbstractSorterOperation<Integer> stub = new AbstractSorterOperationStub().reversed();
        int result = stub.doOperation(Lists.newArrayList(1, 1));
        assertEquals(0, result);
    }

    @Test
    public void testReversed2() {
        AbstractSorterOperation<Integer> stub = new AbstractSorterOperationStub().reversed();
        int result = stub.doOperation(Lists.newArrayList(1, 2));
        assertTrue(result > 0);
    }

    @Test
    public void testReversed3() {
        AbstractSorterOperation<Integer> stub = new AbstractSorterOperationStub().reversed();
        int result = stub.doOperation(Lists.newArrayList(2, 1));
        assertTrue(result < 0);
    }

    public class AbstractSorterOperationStub extends AbstractSorterOperation<Integer> {

        public AbstractSorterOperationStub() {
            super("");
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            return Integer.compare(o1, o2);
        }

        @Override
        public boolean isSpecific() {
            return false;
        }
    }

}
