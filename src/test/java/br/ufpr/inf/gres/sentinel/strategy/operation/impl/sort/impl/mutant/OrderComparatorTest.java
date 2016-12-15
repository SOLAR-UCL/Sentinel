package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Giovani Guizzo on 15/12/2016.
 */
public class OrderComparatorTest {

	@Test
	public void compare() throws Exception {
		OrderComparator comparator = new OrderComparator();
		Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
		mutant1.getConstituentMutants().add(new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest()));
		mutant1.getConstituentMutants().add(new Mutant("Mutant4", null, IntegrationFacade.getProgramUnderTest()));
		Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
		int result = comparator.compare(mutant1, mutant2);
		assertTrue(result > 0);
	}

	@Test
	public void compare2() throws Exception {
		OrderComparator comparator = new OrderComparator();
		Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
		mutant1.getConstituentMutants().add(new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest()));
		mutant1.getConstituentMutants().add(new Mutant("Mutant4", null, IntegrationFacade.getProgramUnderTest()));
		Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
		mutant2.getConstituentMutants().add(new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest()));
		int result = comparator.compare(mutant1, mutant2);
		assertTrue(result > 0);
	}

	@Test
	public void isSpecific() {
		OrderComparator comparator = new OrderComparator();
		assertFalse(comparator.isSpecific());
	}

}