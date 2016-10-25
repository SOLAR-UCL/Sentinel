package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.NewBranchOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.StoreMutantsOperation;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class DefaultOperationFactory implements Factory<Option> {

    private static final String NEW_BRANCH = "New Branch";
    private static final String STORE_MUTANTS = "Store Mutants";

    private DefaultOperationFactory() {
    }

    public static DefaultOperationFactory getInstance() {
        return DefaultOperationFactoryHolder.INSTANCE;
    }

    @Override
    public Operation createOperation(Option option, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = option.getRules().iterator();
        if (rules.hasNext()) {
            Rule firstRule = rules.next();
            Operation mainOperation;
            switch (firstRule.getName()) {
                case STORE_MUTANTS:
                    return new StoreMutantsOperation();
                case NEW_BRANCH:
                    NewBranchOperation branchOperation = new NewBranchOperation();
                    mainOperation = branchOperation;
                    if (rules.hasNext()) {
                        Rule nextRule = rules.next();
                        if (!nextRule.isTerminal()) {
                            branchOperation.setSecondSuccessor(FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator));
                        } else {
                            throw new RuntimeException("Malformed grammar option: " + option.toString());
                        }
                    }
                    break;
                default:
                    mainOperation = FactoryFlyweight.getNonTerminalFactory().createOperation(firstRule, cyclicIterator);
            }
            if (rules.hasNext()) {
                Rule nextRule = rules.next();
                if (!nextRule.isTerminal()) {
                    mainOperation.setSuccessor(FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator));
                } else {
                    throw new RuntimeException("Malformed grammar option: " + option.toString());
                }
            }
            return mainOperation;
        }
        throw new RuntimeException("Malformed grammar option: " + option.toString());
    }

    private static class DefaultOperationFactoryHolder {

        private static final DefaultOperationFactory INSTANCE = new DefaultOperationFactory();
    }
}
