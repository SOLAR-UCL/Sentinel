package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import org.junit.Test;

import static org.junit.Assert.*;

public class MutantsOperatorComparatorTest {

    @Test
    public void compare() throws Exception {
        MutantsOperatorComparator comparator = new MutantsOperatorComparator();
        Mutant mutant1 = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        mutant1.setOperator(new Operator("Operator1", "Type1"));
        Mutant mutant2 = new Mutant("Mutant2", null, new Program("Program1", "Program/path"));
        mutant2.setOperator(new Operator("Operator2", "Type2"));
        int result = comparator.compare(mutant1, mutant2);
        assertTrue(result < 0);
    }

    @Test
    public void compare2() throws Exception {
        MutantsOperatorComparator comparator = new MutantsOperatorComparator();
        Mutant mutant1 = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        mutant1.setOperator(new Operator("Operator1", "Type1"));
        Mutant mutant2 = new Mutant("Mutant2", null, new Program("Program1", "Program/path"));
        mutant2.setOperator(new Operator("Operator1", "Type2"));
        int result = comparator.compare(mutant1, mutant2);
        assertEquals(0, result);
    }

    @Test
    public void isSpecific() {
        MutantsOperatorComparator comparator = new MutantsOperatorComparator();
        assertFalse(comparator.isSpecific());
    }

}
