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
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.SelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.DiscardOperatorsOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.ExecuteOperatorsOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.GroupOperators;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.SelectOperatorsOperation;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class OperatorOperationFactory implements Factory<Option> {

    private OperatorOperationFactory() {
    }

    public static OperatorOperationFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Operation createOperation(Option node, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = node.getRules().iterator();
        if (rules.hasNext()) {
            Rule firstRule = rules.next();
            Operation<Solution, List<Mutant>> mainOperation;
            switch (firstRule.getName()) {
                case TerminalRuleType.SELECT_OPERATORS: {
                    Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
                    Rule nextRule = rules.next();
                    SelectionOperation<Operator> selectOperatorOperation = (SelectionOperation<Operator>) FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator);
                    mainOperation = new SelectOperatorsOperation(selectOperatorOperation);
                    break;
                }
                case TerminalRuleType.DISCARD_OPERATORS: {
                    Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
                    Rule nextRule = rules.next();
                    SelectionOperation<Operator> selectOperatorOperation = (SelectionOperation<Operator>) FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator);
                    mainOperation = new DiscardOperatorsOperation(selectOperatorOperation);
                    break;
                }
                case TerminalRuleType.EXECUTE_OPERATORS: {
                    Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
                    Rule nextRule = rules.next();
                    SelectionOperation<Operator> selectOperatorOperation = (SelectionOperation<Operator>) FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator);
                    mainOperation = new ExecuteOperatorsOperation(selectOperatorOperation);
                    break;
                }
                case TerminalRuleType.GROUP_OPERATORS: {
                    //TODO solve this design problem
                    mainOperation = new GroupOperators("", false);
                    Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
                    Rule nextRule = rules.next();
                    break;
                }
                default:
                    mainOperation = FactoryFlyweight.getNonTerminalFactory().createOperation(firstRule, cyclicIterator);
                    break;
            }
            return mainOperation;
        }
        throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
    }

    private static class SingletonHolder {

        private static final OperatorOperationFactory INSTANCE = new OperatorOperationFactory();
    }

}
