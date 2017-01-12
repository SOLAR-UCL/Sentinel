package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.GroupSelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import com.google.common.base.Preconditions;

import java.util.Iterator;

/**
 * @author Giovani Guizzo
 */
public class SpecificSelectionTypeFactory implements Factory<Option> {

	private SpecificSelectionTypeFactory() {
	}

	public static SpecificSelectionTypeFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public Operation createOperation(Option node, Iterator<Integer> integerIterator) {
		Iterator<Rule> rules = node.getRules().iterator();
		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		Rule rule = rules.next();
		SelectionOperation mainOperation;
		switch (rule.getName()) {
			case TerminalRuleType.SELECT_OPERATORS:
			case TerminalRuleType.SELECT_MUTANTS:
				mainOperation = new SelectionOperation();

				Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
				rule = rules.next();
				mainOperation.setSelectionType((SelectionType) FactoryFlyweight.getNonTerminalFactory()
																			   .createOperation(rule, integerIterator));

				Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
				rule = rules.next();
				mainOperation.setSorter((AbstractSorterOperation) FactoryFlyweight.getNonTerminalFactory()
																				  .createOperation(rule, integerIterator));
				break;
			case TerminalRuleType.SELECT_OPERATORS_BY_GROUPS:
			case TerminalRuleType.SELECT_MUTANTS_BY_GROUPS:
				Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
				rule = rules.next();
				GroupSelectionOperation groupOperation = (GroupSelectionOperation) FactoryFlyweight.getNonTerminalFactory()
																								   .createOperation(rule, integerIterator);

				Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
				rule = rules.next();
				groupOperation.setSelectionOperation((SelectionOperation) FactoryFlyweight.getNonTerminalFactory()
																						  .createOperation(rule, integerIterator));

				mainOperation = groupOperation;
				break;
			default:
				throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
		}
		return mainOperation;
	}

	private static class SingletonHolder {

		private static final SpecificSelectionTypeFactory INSTANCE = new SpecificSelectionTypeFactory();
	}

}
