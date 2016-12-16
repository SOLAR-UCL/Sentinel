package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

public class GroupMutantsByOrderTest {

	@Test
	public void createGroupingFunction() throws Exception {
		GroupMutantsByOrder operation = new GroupMutantsByOrder();
		Function<Mutant, Integer> function = operation.createGroupingFunction();
		Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
		Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
		Mutant mutant3 = new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest());

		mutant2.getConstituentMutants().add(mutant1);

		mutant3.getConstituentMutants().add(mutant1);
		mutant3.getConstituentMutants().add(mutant2);

		assertEquals((Integer) 1, function.apply(mutant1));
		assertEquals((Integer) 1, function.apply(mutant2));
		assertEquals((Integer) 2, function.apply(mutant3));
	}

	@Test
	public void isSpecific() throws Exception {
		GroupMutantsByOrder operation = new GroupMutantsByOrder();
		assertFalse(operation.isSpecific());
	}
}