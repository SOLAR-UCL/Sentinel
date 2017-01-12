package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import com.google.common.base.Preconditions;

import java.util.Iterator;

/**
 * @author Giovani Guizzo
 */
public class SelectFactory implements Factory<Option> {

	private SelectFactory() {
	}

	public static SelectFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public Operation createOperation(Option node, Iterator<Integer> integerIterator) {
		Iterator<Rule> rules = node.getRules().iterator();
		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		Rule rule = rules.next();
		SelectionOperation mainOperation;
		mainOperation = (SelectionOperation) FactoryFlyweight.getNonTerminalFactory()
															 .createOperation(rule, integerIterator);
		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		rule = rules.next();
		switch (rule.getName()) {
			case NonTerminalRuleType.QUANTITY:
				String quantity = rule.getOption(integerIterator).getRules().get(0).getName();
				mainOperation.setQuantity(Integer.parseInt(quantity));
				break;
			case NonTerminalRuleType.PERCENTAGE:
				String percentage = rule.getOption(integerIterator).getRules().get(0).getName();
				mainOperation.setPercentage(Double.parseDouble(percentage));
				break;
			default:
				throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
		}
		return mainOperation;
	}

	private static class SingletonHolder {

		private static final SelectFactory INSTANCE = new SelectFactory();
	}

}
