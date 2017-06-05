package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.impl.DiscardMutantsOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.impl.SelectMutantsOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class MutantOperationFactory implements Factory<Option> {

    /**
     *
     * @return
     */
    public static MutantOperationFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private MutantOperationFactory() {
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
        Operation<Solution, List<Mutant>> mainOperation;
        switch (firstRule.getName()) {
            case TerminalRuleType.SELECT_MUTANTS: {
                Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
                Rule nextRule = rules.next();
                SelectionOperation<Mutant> selectionOperation = (SelectionOperation<Mutant>) FactoryFlyweight.getNonTerminalFactory()
                        .createOperation(nextRule, integerIterator);
                mainOperation = new SelectMutantsOperation(selectionOperation);
                break;
            }
            case TerminalRuleType.DISCARD_MUTANTS: {
                Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
                Rule nextRule = rules.next();
                SelectionOperation<Mutant> selectionOperation = (SelectionOperation<Mutant>) FactoryFlyweight.getNonTerminalFactory()
                        .createOperation(nextRule, integerIterator);
                mainOperation = new DiscardMutantsOperation(selectionOperation);
                break;
            }
            default:
                throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
        }
        return mainOperation;
    }

    private static class SingletonHolder {

        private static final MutantOperationFactory INSTANCE = new MutantOperationFactory();
    }

}
