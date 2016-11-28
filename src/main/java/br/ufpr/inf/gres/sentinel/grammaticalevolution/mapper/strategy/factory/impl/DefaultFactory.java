package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.NewBranchOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.StoreMutantsOperation;
import com.google.common.base.Preconditions;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class DefaultFactory implements Factory<Option> {

    private DefaultFactory() {
    }

    public static DefaultFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Operation createOperation(Option option, Iterator<Integer> cyclicIterator) {
        Iterator<Rule> rules = option.getRules().iterator();
        Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + option.toString());
        Rule firstRule = rules.next();
        Operation mainOperation;
        switch (firstRule.getName()) {
            case TerminalRuleType.STORE_MUTANTS:
                mainOperation = new StoreMutantsOperation();
                break;
            case TerminalRuleType.NEW_BRANCH: {
                NewBranchOperation branchOperation = new NewBranchOperation();
                mainOperation = branchOperation;

                Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + option.toString());
                Rule nextRule = rules.next();
                mainOperation.setSuccessor(FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator));

                Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + option.toString());
                nextRule = rules.next();
                branchOperation.setSecondSuccessor(FactoryFlyweight.getNonTerminalFactory().createOperation(nextRule, cyclicIterator));
                break;
            }
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

    private static class SingletonHolder {

        private static final DefaultFactory INSTANCE = new DefaultFactory();
    }
}
