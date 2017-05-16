package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.OperatorExecutionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.impl.ConventionalExecution;
import com.google.common.base.Preconditions;
import java.util.Iterator;

/**
 * @author Giovani Guizzo
 */
public class OperatorExecutionTypeFactory implements Factory<Option> {

    /**
     *
     * @return
     */
    public static OperatorExecutionTypeFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private OperatorExecutionTypeFactory() {
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
        Rule rule = rules.next();
        OperatorExecutionType mainOperation;
        switch (rule.getName()) {
            case TerminalRuleType.CONVENTIONAL:
                mainOperation = new ConventionalExecution();
                break;
            default:
                throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
        }
        return mainOperation;
    }

    private static class SingletonHolder {

        private static final OperatorExecutionTypeFactory INSTANCE = new OperatorExecutionTypeFactory();
    }

}
