package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.AddAllOperatorsOperation;
import java.util.Iterator;

/**
 * Created by Giovani Guizzo on 24/10/2016.
 */
public class StrategyOperationFactory implements Factory<Option> {

    private static final String ALL_OPERATORS = "All Operators";

    private StrategyOperationFactory() {
    }

    @Override
    public Operation createOperation(Option option, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = option.getRules().iterator();
        if (rules.hasNext()) {
            Rule firstRule = rules.next();
            Operation mainOperation;
            switch (firstRule.getName()) {
                case ALL_OPERATORS:
                    mainOperation = new AddAllOperatorsOperation();
                    if (rules.hasNext()) {
                        Rule nextRule = rules.next();
                        if (!nextRule.isTerminal()) {
                            mainOperation.setSuccessor(FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator));
                        } else {
                            throw new RuntimeException("Malformed grammar option: " + option.toString());
                        }
                    }
                    break;
                default:
                    mainOperation = FactoryFlyweight.getNonTerminalFactory().createOperation(firstRule, cyclicIterator);
            }
            return mainOperation;
        }
        throw new RuntimeException("Malformed grammar option: " + option.toString());
    }

    public static StrategyOperationFactory getInstance() {
        return StrategyOperationFactoryHolder.INSTANCE;
    }

    private static class StrategyOperationFactoryHolder {

        private static final StrategyOperationFactory INSTANCE = new StrategyOperationFactory();
    }
}
