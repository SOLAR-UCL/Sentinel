package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.AbstractSelectionOperation;
import com.google.common.collect.Iterables;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class SelectOperationFactory implements Factory<Option> {

    private SelectOperationFactory() {
    }

    public static SelectOperationFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Operation createOperation(Option option, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = option.getRules().iterator();
        if (rules.hasNext()) {
            Rule firstRule = rules.next();
            AbstractSelectionOperation mainOperation = (AbstractSelectionOperation) FactoryFlyweight.getNonTerminalFactory().createOperation(firstRule, cyclicIterator);
            if (rules.hasNext()) {
                Rule secondRule = rules.next();
                Rule numberRule = Iterables.getOnlyElement(secondRule.getOption(cyclicIterator).getRules());
                switch (secondRule.getName()) {
                    case NonTerminalRuleType.QUANTITY:
                        Integer intValue = Integer.parseInt(numberRule.getName());
                        mainOperation.setQuantity(intValue);
                        break;
                    case NonTerminalRuleType.PERCENTAGE:
                        Double doulbe = Double.parseDouble(numberRule.getName());
                        mainOperation.setPercentage(doulbe);
                        break;
                }
            }
            return mainOperation;
        }
        throw new RuntimeException("Malformed grammar option: " + option.toString());
    }

    private static class SingletonHolder {

        private static final SelectOperationFactory INSTANCE = new SelectOperationFactory();
    }
}
