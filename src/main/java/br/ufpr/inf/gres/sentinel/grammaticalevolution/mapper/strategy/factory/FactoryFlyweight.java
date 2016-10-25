package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.DefaultOperationFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.NonTerminalOperationFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.StrategyOperationFactory;
import java.util.HashMap;

/**
 *
 * @author Giovani Guizzo
 */
public class FactoryFlyweight {

    public static final String NON_TERMINAL = "non-terminal";
    public static final String STRATEGY = "strategy";
    public static final String DEFAULT_OPERATION = "defaultOperation";

    private FactoryFlyweight() {
    }

    public static Factory getFactory(String name) {
        return FactoryFlyweightHolder.FLYWEIGHT.computeIfAbsent(name, FactoryFlyweight::createNew);
    }

    public static Factory getNonTerminalFactory() {
        return getFactory(NON_TERMINAL);
    }

    private static Factory createNew(String name) throws RuntimeException {
        switch (name) {
            case NON_TERMINAL:
                return NonTerminalOperationFactory.getInstance();
            case STRATEGY:
                return StrategyOperationFactory.getInstance();
            case DEFAULT_OPERATION:
                return DefaultOperationFactory.getInstance();
            default:
                throw new RuntimeException("Unidentified grammar rule: " + name);
        }
    }

    private static class FactoryFlyweightHolder {

        private static final HashMap<String, Factory> FLYWEIGHT = new HashMap<>();
    }
}
