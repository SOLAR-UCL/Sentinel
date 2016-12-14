package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.MutantQuantityComparator;
import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class MutantQuantityComparatorTest {

    public MutantQuantityComparatorTest() {
    }

    @Test
    public void testCompare() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));
        operator1.getGeneratedMutants().add(new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest()));

        Operator operator2 = new Operator("Operator2", "Type1");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));
        operator2.getGeneratedMutants().add(new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest()));

        int result = comparator.compare(operator1, operator2);
        assertEquals(0, result);
    }

    @Test
    public void testCompare2() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));
        operator1.getGeneratedMutants().add(new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest()));

        Operator operator2 = new Operator("Operator2", "Type1");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));

        int result = comparator.compare(operator1, operator2);
        assertTrue(result > 0);
    }

    @Test
    public void testCompare3() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));

        Operator operator2 = new Operator("Operator2", "Type1");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));
        operator2.getGeneratedMutants().add(new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest()));

        int result = comparator.compare(operator1, operator2);
        assertTrue(result < 0);
    }

    @Test
    public void testCompare4() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));

        Operator operator2 = new Operator("Operator2", "Type1");

        int result = comparator.compare(operator1, operator2);
        assertTrue(result > 0);
    }

    @Test
    public void testCompare5() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();

        Operator operator1 = new Operator("Operator1", "Type1");

        Operator operator2 = new Operator("Operator2", "Type1");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));

        int result = comparator.compare(operator1, operator2);
        assertTrue(result < 0);
    }

    @Test
    public void testIsSpecific() {
        MutantQuantityComparator comparator = new MutantQuantityComparator();
        assertFalse(comparator.isSpecific());
    }

}
