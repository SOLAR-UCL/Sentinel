package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class NonTerminalFactory implements Factory<Rule> {

    /**
     *
     * @return
     */
    public static NonTerminalFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private NonTerminalFactory() {
    }

    /**
     *
     * @param rule
     * @param integerIterator
     * @return
     */
    @Override
    public Operation createOperation(Rule rule, Iterator<Integer> integerIterator) {
        Factory factory = FactoryFlyweight.getFactory(rule.getName());
        return factory.createOperation(rule.getOption(integerIterator), integerIterator);
    }

    private static class SingletonHolder {

        private static final NonTerminalFactory INSTANCE = new NonTerminalFactory();
    }

}
