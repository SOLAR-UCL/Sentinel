package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.impl.SelectionOperation;
import com.google.common.base.Preconditions;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class SelectOperatorsFactory implements Factory<Option> {

    private SelectOperatorsFactory() {
    }

    public static SelectOperatorsFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Operation createOperation(Option node, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = node.getRules().iterator();
        Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
        Rule rule = rules.next();
        SelectionOperation<Operator> mainOperation;
        mainOperation = (SelectionOperation<Operator>) FactoryFlyweight.getNonTerminalFactory().createOperation(rule, cyclicIterator);
        Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
        rule = rules.next();
        switch (rule.getName()) {
            case NonTerminalRuleType.QUANTITY:
                String quantity = rule.getOption(cyclicIterator).getRules().get(0).getName();
                mainOperation.setQuantity(Integer.parseInt(quantity));
                break;
            case NonTerminalRuleType.PERCENTAGE:
                String percentage = rule.getOption(cyclicIterator).getRules().get(0).getName();
                mainOperation.setPercentage(Double.parseDouble(percentage));
                break;
            default:
                throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
        }
        return mainOperation;
    }

    private static class SingletonHolder {

        private static final SelectOperatorsFactory INSTANCE = new SelectOperatorsFactory();
    }

}
