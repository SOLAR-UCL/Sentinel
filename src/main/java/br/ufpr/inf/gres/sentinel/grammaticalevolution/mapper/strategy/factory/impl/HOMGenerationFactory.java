package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.AbstractHOMGeneration;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.impl.ConventionalGeneration;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.impl.SingleHOMGeneration;
import com.google.common.base.Preconditions;

import java.util.Iterator;

/**
 * @author Giovani Guizzo
 */
public class HOMGenerationFactory implements Factory<Option> {

	private HOMGenerationFactory() {
	}

	public static HOMGenerationFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public Operation createOperation(Option node, Iterator<Integer> integerIterator) {
		Iterator<Rule> rules = node.getRules().iterator();
		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		Rule rule = rules.next();

		AbstractHOMGeneration mainOperation;
		switch (rule.getName()) {
			case TerminalRuleType.SINGLE_HOM:
				mainOperation = new SingleHOMGeneration();
				break;
			case TerminalRuleType.CONVENTIONAL:
				Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
				rule = rules.next();

				int order = Integer.parseInt(rule.getOption(integerIterator).getRules().get(0).getName());
				mainOperation = new ConventionalGeneration(order);
				break;
			default:
				throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
		}

		return mainOperation;
	}

	private static class SingletonHolder {

		private static final HOMGenerationFactory INSTANCE = new HOMGenerationFactory();
	}

}
