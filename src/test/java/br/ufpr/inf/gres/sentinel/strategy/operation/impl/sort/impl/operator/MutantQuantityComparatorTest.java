package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import org.junit.Test;

import static org.junit.Assert.*;

public class MutantQuantityComparatorTest {

    public MutantQuantityComparatorTest() {
    }

    @Test
    public void testCompare() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));
        operator1.getGeneratedMutants().add(new Mutant("Mutant2", null, new Program("Program1", "Program/path")));

        Operator operator2 = new Operator("Operator2", "Type1");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));
        operator2.getGeneratedMutants().add(new Mutant("Mutant2", null, new Program("Program1", "Program/path")));

        int result = comparator.compare(operator1, operator2);
        assertEquals(0, result);
    }

    @Test
    public void testCompare2() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));
        operator1.getGeneratedMutants().add(new Mutant("Mutant2", null, new Program("Program1", "Program/path")));

        Operator operator2 = new Operator("Operator2", "Type1");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));

        int result = comparator.compare(operator1, operator2);
        assertTrue(result > 0);
    }

    @Test
    public void testCompare3() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));

        Operator operator2 = new Operator("Operator2", "Type1");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));
        operator2.getGeneratedMutants().add(new Mutant("Mutant2", null, new Program("Program1", "Program/path")));

        int result = comparator.compare(operator1, operator2);
        assertTrue(result < 0);
    }

    @Test
    public void testCompare4() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));

        Operator operator2 = new Operator("Operator2", "Type1");

        int result = comparator.compare(operator1, operator2);
        assertTrue(result > 0);
    }

    @Test
    public void testCompare5() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();

        Operator operator1 = new Operator("Operator1", "Type1");

        Operator operator2 = new Operator("Operator2", "Type1");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));

        int result = comparator.compare(operator1, operator2);
        assertTrue(result < 0);
    }

    @Test
    public void testIsSpecific() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();
        assertFalse(comparator.isSpecific());
    }

}
