package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.DefaultFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.NonTerminalFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.OperatorExecutionTypeFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.OperatorFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.OperatorGroupSelectionTypeFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.OperatorGroupSortingFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.OperatorGroupingFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.OperatorSelectionTypeFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.OperatorSortingFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.SelectOperatorsFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.SelectionTypeFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.StrategyFactory;
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

    public static NonTerminalFactory getNonTerminalFactory() {
        return (NonTerminalFactory) getFactory(NonTerminalRuleType.UNKNOWN_NON_TERMINAL);
    }

    private static Factory createNew(String name) throws RuntimeException {
        switch (name) {
            case NonTerminalRuleType.UNKNOWN_NON_TERMINAL:
                return NonTerminalFactory.getInstance();
            case NonTerminalRuleType.STRATEGY:
                return StrategyFactory.getInstance();
            case NonTerminalRuleType.DEFAULT_OPERATION:
                return DefaultFactory.getInstance();
            case NonTerminalRuleType.SELECTION_TYPE:
                return SelectionTypeFactory.getInstance();
            case NonTerminalRuleType.OPERATOR_OPERATION:
                return OperatorFactory.getInstance();
            case NonTerminalRuleType.SELECT_OPERATORS:
            case NonTerminalRuleType.SELECT_OPERATOR_GROUPS:
                return SelectOperatorsFactory.getInstance();
            case NonTerminalRuleType.OPERATOR_SELECTION_TYPE:
                return OperatorSelectionTypeFactory.getInstance();
            case NonTerminalRuleType.OPERATOR_GROUP_SELECTION_TYPE:
                return OperatorGroupSelectionTypeFactory.getInstance();
            case NonTerminalRuleType.OPERATOR_SORTING:
                return OperatorSortingFactory.getInstance();
            case NonTerminalRuleType.OPERATOR_GROUPING:
                return OperatorGroupingFactory.getInstance();
            case NonTerminalRuleType.OPERATOR_GROUP_SORTING:
                return OperatorGroupSortingFactory.getInstance();
            case NonTerminalRuleType.OPERATOR_EXECUTION_TYPE:
                return OperatorExecutionTypeFactory.getInstance();
            default:
                throw new IllegalArgumentException("Unidentified grammar rule: " + name);
        }
    }

    private static class FactoryFlyweightHolder {

        private static final HashMap<String, Factory> FLYWEIGHT = new HashMap<>();
    }
}
