package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.GroupingFunction;
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

        GroupingFunction mainOperation;
        switch (rule.getName()) {
            case TerminalRuleType.TYPE:
                mainOperation = new GroupingFunction<>("Group by Type", false, Operator::getType);
                break;
            case TerminalRuleType.MUTANT_QUANTITY:
                mainOperation = new GroupingFunction<>("Group by Mutant Quantity", false, (Operator operator) -> operator.getGeneratedMutants().size());
                break;
            default:
                throw new AssertionError();
        }

        return mainOperation;
    }

    private static class SingletonHolder {

        private static final OperatorGroupingFactory INSTANCE = new OperatorGroupingFactory();
    }

}
