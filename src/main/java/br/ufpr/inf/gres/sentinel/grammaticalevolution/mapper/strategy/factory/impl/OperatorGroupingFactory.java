package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingFunction;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.GroupOperatorsByMutantQuantity;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.GroupOperatorsByType;
import com.google.common.base.Preconditions;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class OperatorGroupingFactory implements Factory<Option> {

    public static OperatorGroupingFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Operation createOperation(Option node, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = node.getRules().iterator();

        Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
        Rule rule = rules.next();
        rule = rule.getOption(cyclicIterator).getRules().get(0);

        AbstractGroupingFunction mainOperation;
        switch (rule.getName()) {
            case TerminalRuleType.TYPE:
                mainOperation = new GroupOperatorsByType();
                break;
            case TerminalRuleType.MUTANT_QUANTITY:
                mainOperation = new GroupOperatorsByMutantQuantity();
                break;
            default:
                throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
        }

        return mainOperation;
    }

    private static class SingletonHolder {

        private static final OperatorGroupingFactory INSTANCE = new OperatorGroupingFactory();
    }

}
