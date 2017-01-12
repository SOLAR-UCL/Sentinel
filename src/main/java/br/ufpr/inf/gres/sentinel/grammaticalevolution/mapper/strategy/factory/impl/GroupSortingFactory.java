package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.QuantityInGroupComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.MutantQuantityInGroupComparator;
import com.google.common.base.Preconditions;

import java.util.Iterator;

/**
 * @author Giovani Guizzo
 */
public class GroupSortingFactory implements Factory<Option> {

	private GroupSortingFactory() {
	}

	public static GroupSortingFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public Operation createOperation(Option node, Iterator<Integer> integerIterator) {
		Iterator<Rule> rules = node.getRules().iterator();
		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		Rule rule = rules.next();
		if (rule.getName().isEmpty()) {
			return null;
		}

		rule = rule.getOption(integerIterator).getRules().get(0);
		AbstractSorterOperation mainOperation;
		switch (rule.getName()) {
			case TerminalRuleType.MUTANT_QUANTITY_IN_GROUP:
				mainOperation = new MutantQuantityInGroupComparator();
				break;
			case TerminalRuleType.QUANTITY_IN_GROUP:
				mainOperation = new QuantityInGroupComparator();
				break;
			default:
				throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
		}

		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		rule = rules.next().getOption(integerIterator).getRules().get(0);
		if (rule.getName().equals(TerminalRuleType.DESCENDING)) {
			mainOperation.setReversed(true);
		}

		return mainOperation;
	}

	private static class SingletonHolder {

		private static final GroupSortingFactory INSTANCE = new GroupSortingFactory();
	}

}
