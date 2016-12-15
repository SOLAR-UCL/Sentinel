package br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest.IntegrationFacadeStub;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class StoreMutantsOperationTest {

	public StoreMutantsOperationTest() {
	}

	@BeforeClass
	public static void setUpClass() {
		IntegrationFacade.setIntegrationFacade(new IntegrationFacadeStub());
	}

	@Test
	public void testDoOperation() {
		Solution solution = new Solution();
		solution.getMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));
		solution.getMutants().add(new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest()));

		StoreMutantsOperation storeMutantsOperation = new StoreMutantsOperation();
		List<Mutant> result = storeMutantsOperation.doOperation(solution);

		assertEquals(2, result.size());
	}

	@Test
	public void testIsSpecific() {
		StoreMutantsOperation storeMutantsOperation = new StoreMutantsOperation();
		assertFalse(storeMutantsOperation.isSpecific());
	}

}
