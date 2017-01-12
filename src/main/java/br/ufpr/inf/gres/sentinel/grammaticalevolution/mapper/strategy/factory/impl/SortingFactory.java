package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant.MutantsOperatorComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant.MutantsOperatorTypeComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant.OrderComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.MutantQuantityComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.OperatorTypeComparator;
import com.google.common.base.Preconditions;

import java.util.Iterator;

/**
 * @author Giovani Guizzo
 */
public class SortingFactory implements Factory<Option> {

	private SortingFactory() {
	}

	public static SortingFactory getInstance() {
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
			case TerminalRuleType.TYPE:
				mainOperation = new OperatorTypeComparator();
				break;
			case TerminalRuleType.MUTANT_QUANTITY:
				mainOperation = new MutantQuantityComparator();
				break;
			case TerminalRuleType.OPERATOR_TYPE:
				mainOperation = new MutantsOperatorTypeComparator();
				break;
			case TerminalRuleType.OPERATOR:
				mainOperation = new MutantsOperatorComparator();
				break;
			case TerminalRuleType.FOM_OR_HOM:
			case TerminalRuleType.ORDER:
				mainOperation = new OrderComparator();
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

		private static final SortingFactory INSTANCE = new SortingFactory();
	}

}
