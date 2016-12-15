package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Giovani Guizzo on 15/12/2016.
 */
public class MutantsOperatorComparatorTest {

	@Test
	public void compare() throws Exception {
		MutantsOperatorComparator comparator = new MutantsOperatorComparator();
		Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
		mutant1.getOperators().add(new Operator("Operator1", "Type1"));
		mutant1.getOperators().add(new Operator("Operator2", "Type2"));
		Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
		mutant1.getOperators().add(new Operator("Operator2", "Type2"));
		mutant1.getOperators().add(new Operator("Operator3", "Type3"));
		int result = comparator.compare(mutant1, mutant2);
		assertTrue(result < 0);
	}

	@Test
	public void isSpecific() {
		MutantsOperatorComparator comparator = new MutantsOperatorComparator();
		assertFalse(comparator.isSpecific());
	}

}