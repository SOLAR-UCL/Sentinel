package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.DefaultOperationFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.NonTerminalOperationFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.OperatorOperationFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.StrategyOperationFactory;
import java.util.HashMap;

/**
 *
 * @author Giovani Guizzo
 */
public class FactoryFlyweight {

    private FactoryFlyweight() {
    }

    public static Factory getFactory(String name) {
        return FactoryFlyweightHolder.FLYWEIGHT.computeIfAbsent(name, FactoryFlyweight::createNew);
    }

    public static NonTerminalOperationFactory getNonTerminalFactory() {
        return (NonTerminalOperationFactory) getFactory(NonTerminalRuleType.UNKNOWN_NON_TERMINAL);
    }

    private static Factory createNew(String name) throws RuntimeException {
        switch (name) {
            case NonTerminalRuleType.UNKNOWN_NON_TERMINAL:
                return NonTerminalOperationFactory.getInstance();
            case NonTerminalRuleType.STRATEGY:
                return StrategyOperationFactory.getInstance();
            case NonTerminalRuleType.DEFAULT_OPERATION:
                return DefaultOperationFactory.getInstance();
            case NonTerminalRuleType.OPERATOR_OPERATION:
                return OperatorOperationFactory.getInstance();
//            case NonTerminalRuleType.SELECT_OPERATORS:
//            case NonTerminalRuleType.SELECT_MUTANTS:
//                return SelectOperationFactory.getInstance();
            default:
                throw new IllegalArgumentException("Unidentified grammar rule: " + name);
        }
    }

    private static class FactoryFlyweightHolder {

        private static final HashMap<String, Factory> FLYWEIGHT = new HashMap<>();
    }
}
