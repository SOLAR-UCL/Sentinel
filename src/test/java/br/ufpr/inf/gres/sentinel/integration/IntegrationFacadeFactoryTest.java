package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Giovani Guizzo
 */
public class IntegrationFacadeFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void createIntegrationFacade() throws Exception {
        IntegrationFacade facade = IntegrationFacadeFactory.createIntegrationFacade(IntegrationFacadeFactory.PIT, "");
        assertNotNull(facade);
        assertTrue(facade instanceof PITFacade);
        facade = IntegrationFacadeFactory.createIntegrationFacade("Unknown", "");
    }

}
