package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

public class GroupMutantsByFOMOrHOMTest {

	@Test
	public void createGroupingFunction() throws Exception {
		GroupMutantsByFOMOrHOM operation = new GroupMutantsByFOMOrHOM();
		Function<Mutant, String> function = operation.createGroupingFunction();
		Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
		Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
		Mutant mutant3 = new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest());

		mutant2.getConstituentMutants().add(mutant1);

		mutant3.getConstituentMutants().add(mutant1);
		mutant3.getConstituentMutants().add(mutant2);

		assertEquals("FOM", function.apply(mutant1));
		assertEquals("FOM", function.apply(mutant2));
		assertEquals("HOM", function.apply(mutant3));
	}

	@Test
	public void isSpecific() throws Exception {
		GroupMutantsByFOMOrHOM operation = new GroupMutantsByFOMOrHOM();
		assertFalse(operation.isSpecific());
	}

}