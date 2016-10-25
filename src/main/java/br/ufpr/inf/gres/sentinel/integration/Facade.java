package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public abstract class Facade {

    private static Facade FACADE_INSTANCE;

    public static Facade getFacade() {
        return FACADE_INSTANCE;
    }

    public static void setFacade(Facade facade) {
        FACADE_INSTANCE = facade;
    }

    public abstract List<Operator> getAllOperators();

}
