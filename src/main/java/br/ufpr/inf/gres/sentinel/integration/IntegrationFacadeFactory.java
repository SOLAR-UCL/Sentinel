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
        switch (name.toUpperCase()) {
            case MU_JAVA:
            case HG4HOM:
                return new HG4HOMFacade(trainingDirectory);
            case PIT:
                return new PITFacade(trainingDirectory);
            default:
                throw new IllegalArgumentException("Mutation tool " + name + " not found!");
        }
    }

}
