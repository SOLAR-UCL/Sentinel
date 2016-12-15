package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.LastToFirstSelection;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.RandomSelection;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import com.google.common.base.Preconditions;

import java.util.Iterator;

/**
 * @author Giovani Guizzo
 */
public class SelectionTypeFactory implements Factory<Option> {

	private SelectionTypeFactory() {
	}

	public static SelectionTypeFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public Operation createOperation(Option node, Iterator<Integer> cyclicIterator) {
		Iterator<Rule> rules = node.getRules().iterator();
		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		Rule rule = rules.next();
		SelectionType mainOperation;
		switch (rule.getName()) {
			case TerminalRuleType.RANDOM:
				mainOperation = new RandomSelection<>();
				break;
			case TerminalRuleType.LAST_TO_FIRST:
				mainOperation = new LastToFirstSelection<>();
				break;
			case TerminalRuleType.SEQUENTIAL:
				mainOperation = new SequentialSelection<>();
				break;
			default:
				throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
		}
		return mainOperation;
	}

	private static class SingletonHolder {

		private static final SelectionTypeFactory INSTANCE = new SelectionTypeFactory();
	}

}
