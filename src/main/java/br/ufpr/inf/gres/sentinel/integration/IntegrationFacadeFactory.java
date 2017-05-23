package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;

/**
 * @author Giovani Guizzo
 */
public class IntegrationFacadeFactory {

    /**
     *
     */
    public static final String PIT = "PIT";

    /**
     *
     * @param name
     * @param inputDirectory
     * @return
     */
    public static IntegrationFacade createIntegrationFacade(String name, String inputDirectory) {
        IntegrationFacade facade;
        switch (name.toUpperCase()) {
            case PIT:
                facade = new PITFacade(inputDirectory);
                break;
            default:
                throw new IllegalArgumentException("Mutation tool " + name + " not found!");
        }
        return facade;
    }

}
