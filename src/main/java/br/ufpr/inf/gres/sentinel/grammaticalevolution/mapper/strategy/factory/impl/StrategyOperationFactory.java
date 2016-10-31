package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.AddAllOperatorsOperation;
import com.google.common.base.Preconditions;
import java.util.Iterator;

/**
 * Created by Giovani Guizzo on 24/10/2016.
 */
public class StrategyOperationFactory implements Factory<Option> {

    private StrategyOperationFactory() {
    }

    @Override
    public Operation createOperation(Option option, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = option.getRules().iterator();
        if (rules.hasNext()) {
            Rule firstRule = rules.next();
            Operation mainOperation;
            switch (firstRule.getName()) {
                case TerminalRuleType.ALL_OPERATORS:
                    mainOperation = new AddAllOperatorsOperation();

                    Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + option.toString());
                    Rule nextRule = rules.next();
                    mainOperation.setSuccessor(FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator));
                    break;
                default:
                    mainOperation = FactoryFlyweight.getNonTerminalFactory().createOperation(firstRule, cyclicIterator);
                    break;
            }
            return mainOperation;
        }
        throw new IllegalArgumentException("Malformed grammar option: " + option.toString());
    }

    public static StrategyOperationFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        private static final StrategyOperationFactory INSTANCE = new StrategyOperationFactory();
    }
}
