package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import org.junit.Test;

import static org.junit.Assert.*;

public class MutantsOperatorTypeComparatorTest {

	public MutantsOperatorTypeComparatorTest() {
	}

	@Test
	public void testCompare() {
		MutantsOperatorTypeComparator comparator = new MutantsOperatorTypeComparator();
		Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
		mutant1.getOperators().add(new Operator("Operator1", "Type1"));
		mutant1.getOperators().add(new Operator("Operator2", "Type2"));
		Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
		mutant2.getOperators().add(new Operator("Operator1", "Type2"));
		mutant2.getOperators().add(new Operator("Operator2", "Type3"));
		int result = comparator.compare(mutant1, mutant2);
		assertTrue(result < 0);
	}

	@Test
	public void testCompare2() {
		MutantsOperatorTypeComparator comparator = new MutantsOperatorTypeComparator();
		Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
		mutant1.getOperators().add(new Operator("Operator1", "Type1"));
		mutant1.getOperators().add(new Operator("Operator2", "Type2"));
		Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
		mutant2.getOperators().add(new Operator("Operator1", "Type1"));
		mutant2.getOperators().add(new Operator("Operator2", "Type3"));
		mutant2.getOperators().add(new Operator("Operator3", "Type2"));
		mutant2.getOperators().add(new Operator("Operator4", "Type4"));
		int result = comparator.compare(mutant1, mutant2);
		assertEquals(0, result);
	}

	@Test
	public void testIsSpecific() {
		MutantsOperatorTypeComparator comparator = new MutantsOperatorTypeComparator();
		assertFalse(comparator.isSpecific());
	}

}
