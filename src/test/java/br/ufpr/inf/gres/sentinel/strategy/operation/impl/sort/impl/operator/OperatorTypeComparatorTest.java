package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import org.junit.Test;

import static org.junit.Assert.*;

public class OperatorTypeComparatorTest {

    public OperatorTypeComparatorTest() {
    }

    @Test
    public void testCompare() {
        OperatorTypeComparator comparator = new OperatorTypeComparator();

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator0", "Type1");

        int result = comparator.compare(operator1, operator2);
        assertEquals(0, result);
    }

    @Test
    public void testCompare2() {
        OperatorTypeComparator comparator = new OperatorTypeComparator();

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator0", "Type2");

        int result = comparator.compare(operator1, operator2);
        assertTrue(result < 0);
    }

    @Test
    public void testCompare3() {
        OperatorTypeComparator comparator = new OperatorTypeComparator();

        Operator operator1 = new Operator("Operator1", "Type2");
        Operator operator2 = new Operator("Operator0", "Type1");

        int result = comparator.compare(operator1, operator2);
        assertTrue(result > 0);
    }

    @Test
    public void testIsSpecific() {
        OperatorTypeComparator comparator = new OperatorTypeComparator();
        assertFalse(comparator.isSpecific());
    }

}
