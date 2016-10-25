package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class SingleNonTerminalOperationFactory implements Factory<Option> {

    private SingleNonTerminalOperationFactory() {
    }

    public static SingleNonTerminalOperationFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Operation createOperation(Option option, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = option.getRules().iterator();
        if (rules.hasNext()) {
            Rule onlyRule = rules.next();
            return FactoryFlyweight.getNonTerminalFactory().createOperation(onlyRule, cyclicIterator);
        }
        throw new RuntimeException("Malformed grammar option: " + option.toString());
    }

    private static class SingletonHolder {

        private static final SingleNonTerminalOperationFactory INSTANCE = new SingleNonTerminalOperationFactory();
    }
}
