package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.AddAllOperatorsOperation;
import com.google.common.base.Preconditions;

import java.util.Iterator;

/**
 * Created by Giovani Guizzo on 24/10/2016.
 */
@SuppressWarnings("ALL")
public class StrategyFactory implements Factory<Option> {

	private StrategyFactory() {
	}

	public static StrategyFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public Operation createOperation(Option option, Iterator<Integer> cyclicIterator) {
		Iterator<Rule> rules = option.getRules().iterator();

		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + option.toString());
		Rule firstRule = rules.next();
		Operation mainOperation;
		switch (firstRule.getName()) {
			case TerminalRuleType.ALL_OPERATORS:
				mainOperation = new AddAllOperatorsOperation();

				Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + option.toString());
				Rule nextRule = rules.next();
				mainOperation.setSuccessor(FactoryFlyweight.getNonTerminalFactory()
														   .createOperation(nextRule, cyclicIterator));
				break;
			default:
				throw new IllegalArgumentException("Malformed grammar option: " + option.toString());
		}
		return mainOperation;
	}

	private static class SingletonHolder {

		private static final StrategyFactory INSTANCE = new StrategyFactory();
	}
}
