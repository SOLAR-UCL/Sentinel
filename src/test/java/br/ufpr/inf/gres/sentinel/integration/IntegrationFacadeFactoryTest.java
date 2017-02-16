package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.integration.hg4hom.HG4HOMFacade;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class IntegrationFacadeFactoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void createIntegrationFacade() throws Exception {
		IntegrationFacade
				facade =
				IntegrationFacadeFactory.createIntegrationFacade(IntegrationFacadeFactory.MU_JAVA, "");
		assertNotNull(facade);
		assertTrue(facade instanceof HG4HOMFacade);
		facade = IntegrationFacadeFactory.createIntegrationFacade(IntegrationFacadeFactory.HG4HOM, "");
		assertNotNull(facade);
		assertTrue(facade instanceof HG4HOMFacade);
		facade = IntegrationFacadeFactory.createIntegrationFacade("Unknown", "");
	}

}