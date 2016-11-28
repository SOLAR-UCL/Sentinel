package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import com.google.common.base.Preconditions;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class SelectOperatorGroupsFactory implements Factory<Option> {

    public static SelectOperatorGroupsFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Operation createOperation(Option node, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = node.getRules().iterator();
        Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
        Rule rule = rules.next();
        //TODO
        return mainOperation;
    }

    private static class SingletonHolder {

        private static final SelectOperatorGroupsFactory INSTANCE = new SelectOperatorGroupsFactory();
    }

}
