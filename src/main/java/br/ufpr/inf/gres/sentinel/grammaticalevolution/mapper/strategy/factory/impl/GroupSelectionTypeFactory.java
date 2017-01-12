package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.GroupSelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import com.google.common.base.Preconditions;

import java.util.Iterator;

/**
 * @author Giovani Guizzo
 */
public class GroupSelectionTypeFactory implements Factory<Option> {

	private GroupSelectionTypeFactory() {
	}

	public static GroupSelectionTypeFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public Operation createOperation(Option node, Iterator<Integer> integerIterator) {
		Iterator<Rule> rules = node.getRules().iterator();

		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		Rule rule = rules.next();
		GroupSelectionOperation mainOperation = new GroupSelectionOperation();
		mainOperation.setGroupingFunction((AbstractGroupingOperation) FactoryFlyweight.getNonTerminalFactory()
																					  .createOperation(rule, integerIterator));

		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		rule = rules.next();
		mainOperation.setSelectionType((SelectionType) FactoryFlyweight.getNonTerminalFactory()
																	   .createOperation(rule, integerIterator));

		Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
		rule = rules.next();
		mainOperation.setSorter((AbstractSorterOperation) FactoryFlyweight.getNonTerminalFactory()
																		  .createOperation(rule, integerIterator));

		return mainOperation;
	}

	private static class SingletonHolder {

		private static final GroupSelectionTypeFactory INSTANCE = new GroupSelectionTypeFactory();
	}

}
