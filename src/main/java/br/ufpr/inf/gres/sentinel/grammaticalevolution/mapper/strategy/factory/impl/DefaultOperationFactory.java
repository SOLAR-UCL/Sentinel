package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.NewBranchOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.StoreMutantsOperation;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class DefaultOperationFactory implements Factory<Option> {

    private DefaultOperationFactory() {
    }

    public static DefaultOperationFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Operation createOperation(Option option, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = option.getRules().iterator();
        if (rules.hasNext()) {
            Rule firstRule = rules.next();
            Operation mainOperation;
            switch (firstRule.getName()) {
                case TerminalRuleType.STORE_MUTANTS:
                    return new StoreMutantsOperation();
                case TerminalRuleType.NEW_BRANCH:
                    NewBranchOperation branchOperation = new NewBranchOperation();
                    mainOperation = branchOperation;
                    if (rules.hasNext()) {
                        Rule nextRule = rules.next();
                        mainOperation.setSuccessor(FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator));
                        if (rules.hasNext()) {
                            nextRule = rules.next();
                            branchOperation.setSecondSuccessor(FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator));
                        }
                    }
                    break;
                default:
                    mainOperation = FactoryFlyweight.getNonTerminalFactory().createOperation(firstRule, cyclicIterator);
                    if (rules.hasNext()) {
                        Rule nextRule = rules.next();
                        mainOperation.setSuccessor(FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator));
                    }
                    break;
            }
            return mainOperation;
        }
        throw new RuntimeException("Malformed grammar option: " + option.toString());
    }

    private static class SingletonHolder {

        private static final DefaultOperationFactory INSTANCE = new DefaultOperationFactory();
    }
}
