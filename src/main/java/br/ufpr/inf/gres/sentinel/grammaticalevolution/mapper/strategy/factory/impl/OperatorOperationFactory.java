package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.impl.DiscardOperatorsOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.ExecuteOperatorsOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.OperatorExecutionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.impl.RetainOperatorsOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Giovani Guizzo
 */
public class OperatorOperationFactory implements Factory<Option> {

    /**
     *
     * @return
     */
    public static OperatorOperationFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private OperatorOperationFactory() {
    }

    /**
     *
     * @param node
     * @param integerIterator
     * @return
     */
    @Override
    public Operation createOperation(Option node, Iterator<Integer> integerIterator) {
        Iterator<Rule> rules = node.getRules().iterator();
        Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
        Rule firstRule = rules.next();
        Operation<Solution, Collection<Mutant>> mainOperation;
        switch (firstRule.getName()) {
            case TerminalRuleType.RETAIN_OPERATORS: {
                Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
                Rule nextRule = rules.next();
                SelectionOperation<Operator> selectionOperation = (SelectionOperation<Operator>) FactoryFlyweight.getNonTerminalFactory()
                        .createOperation(nextRule, integerIterator);
                mainOperation = new RetainOperatorsOperation(selectionOperation);
                break;
            }
            case TerminalRuleType.DISCARD_OPERATORS: {
                Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
                Rule nextRule = rules.next();
                SelectionOperation<Operator> selectionOperation = (SelectionOperation<Operator>) FactoryFlyweight.getNonTerminalFactory()
                        .createOperation(nextRule, integerIterator);
                mainOperation = new DiscardOperatorsOperation(selectionOperation);
                break;
            }
            case TerminalRuleType.EXECUTE_OPERATORS: {
                Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
                Rule nextRule = rules.next();
                OperatorExecutionType executionType = (OperatorExecutionType) FactoryFlyweight.getNonTerminalFactory()
                        .createOperation(nextRule, integerIterator);

                Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
                nextRule = rules.next();
                SelectionOperation<Operator> selectionOperation = (SelectionOperation<Operator>) FactoryFlyweight.getNonTerminalFactory()
                        .createOperation(nextRule, integerIterator);

                mainOperation = new ExecuteOperatorsOperation(selectionOperation, executionType);
                break;
            }
            default:
                throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
        }
        return mainOperation;
    }

    private static class SingletonHolder {

        private static final OperatorOperationFactory INSTANCE = new OperatorOperationFactory();
    }

}
