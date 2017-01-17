package br.ufpr.inf.gres.sentinel.integration.mujava;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class MuJavaFacadeTest {

	public MuJavaFacadeTest() {
	}

	@Test
	public void getAllOperators() throws Exception {
		IntegrationFacade muJavaFacade = IntegrationFacade.createMuJavaFacade();
		List<Operator> allOperators = muJavaFacade.getAllOperators();
		assertNotNull(allOperators);
		assertEquals(53, allOperators.size());
	}

	@Test
	public void executeOperator() throws Exception {

	}

	@Test
	public void combineMutants() throws Exception {

	}

}
