package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
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
public class OperatorSelectionTypeFactory implements Factory<Option> {

	private OperatorSelectionTypeFactory() {
	}

	public static OperatorSelectionTypeFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public Operation createOperation(Option node, Iterator<Integer> cyclicIterator) {
		Iterator<Rule> rules = node.getRules().iterator();
		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		Rule rule = rules.next();
		SelectionOperation<Operator> mainOperation;
		switch (rule.getName()) {
			case TerminalRuleType.SELECT_OPERATORS:
				mainOperation = new SelectionOperation();

				Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
				rule = rules.next();
				mainOperation.setSelectionType((SelectionType) FactoryFlyweight.getNonTerminalFactory()
																			   .createOperation(rule, cyclicIterator));

				Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
				rule = rules.next();
				mainOperation.setSorter((AbstractSorterOperation) FactoryFlyweight.getNonTerminalFactory()
																				  .createOperation(rule, cyclicIterator));
				break;
			case TerminalRuleType.SELECT_OPERATORS_BY_GROUPS:
				Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
				rule = rules.next();
				GroupSelectionOperation<Operator> groupOperation = (GroupSelectionOperation<Operator>) FactoryFlyweight.getNonTerminalFactory()
																													   .createOperation(rule, cyclicIterator);

				Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
				rule = rules.next();
				groupOperation.setSelectionOperation((SelectionOperation) FactoryFlyweight.getNonTerminalFactory()
																						  .createOperation(rule, cyclicIterator));

				mainOperation = groupOperation;
				break;
			default:
				throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
		}
		return mainOperation;
	}

	private static class SingletonHolder {

		private static final OperatorSelectionTypeFactory INSTANCE = new OperatorSelectionTypeFactory();
	}

}
