package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.integration.hg4hom.HG4HOMFacade;
import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;

/**
 * @author Giovani Guizzo
 */
public class IntegrationFacadeFactory {

    public static final String MU_JAVA = "MUJAVA";
    public static final String HG4HOM = "HG4HOM";
    public static final String PIT = "PIT";

    public static IntegrationFacade createIntegrationFacade(String name, String trainingDirectory) {
        IntegrationFacade facade;
        switch (name.toUpperCase()) {
            case MU_JAVA:
            case HG4HOM:
                facade = new HG4HOMFacade(trainingDirectory);
                break;
            case PIT:
                facade = new PITFacade(trainingDirectory);
                break;
            default:
                throw new IllegalArgumentException("Mutation tool " + name + " not found!");
        }
        return facade;
    }

}
