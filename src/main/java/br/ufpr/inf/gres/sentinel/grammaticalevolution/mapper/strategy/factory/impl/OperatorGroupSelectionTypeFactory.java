package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingFunction;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.impl.GroupSelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import com.google.common.base.Preconditions;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class OperatorGroupSelectionTypeFactory implements Factory<Option> {

    public static OperatorGroupSelectionTypeFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Operation createOperation(Option node, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = node.getRules().iterator();

        Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
        Rule rule = rules.next();
        GroupSelectionOperation<Operator> mainOperation = new GroupSelectionOperation();
        mainOperation.setGroupingFunction((AbstractGroupingFunction) FactoryFlyweight.getNonTerminalFactory().createOperation(rule, cyclicIterator));

        Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
        rule = rules.next();
        mainOperation.setSelectionType((SelectionType) FactoryFlyweight.getNonTerminalFactory().createOperation(rule, cyclicIterator));

        Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
        rule = rules.next();
        mainOperation.setSorter((AbstractSorterOperation) FactoryFlyweight.getNonTerminalFactory().createOperation(rule, cyclicIterator));

        return mainOperation;
    }

    private static class SingletonHolder {

        private static final OperatorGroupSelectionTypeFactory INSTANCE = new OperatorGroupSelectionTypeFactory();
    }

}
