package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl.*;

import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Giovani Guizzo
 */
public class FactoryFlyweight {

	private FactoryFlyweight() {
	}

	public static Factory getFactory(String name) {
		checkNotNull(name, "Unidentified grammar rule.");
		return FactoryFlyweightHolder.FLYWEIGHT.computeIfAbsent(name, FactoryFlyweight::createNew);
	}

	public static NonTerminalFactory getNonTerminalFactory() {
		return (NonTerminalFactory) getFactory(NonTerminalRuleType.UNKNOWN_NON_TERMINAL);
	}

	private static Factory createNew(String name) throws RuntimeException {
		switch (name) {
			// Defaults
			case NonTerminalRuleType.UNKNOWN_NON_TERMINAL:
				return NonTerminalFactory.getInstance();
			case NonTerminalRuleType.STRATEGY:
				return StrategyFactory.getInstance();
			case NonTerminalRuleType.DEFAULT_OPERATION:
				return DefaultFactory.getInstance();
			case NonTerminalRuleType.SELECTION_TYPE:
				return SelectionTypeFactory.getInstance();
			// Specific
			case NonTerminalRuleType.OPERATOR_OPERATION:
				return OperatorOperationFactory.getInstance();
			case NonTerminalRuleType.MUTANT_OPERATION:
				return MutantOperationFactory.getInstance();
			// Shared
			case NonTerminalRuleType.SELECT_OPERATORS:
			case NonTerminalRuleType.SELECT_MUTANTS:
				return SelectFactory.getInstance();
			case NonTerminalRuleType.OPERATOR_SELECTION_TYPE:
			case NonTerminalRuleType.MUTANT_SELECTION_TYPE:
				return SpecificSelectionTypeFactory.getInstance();
			case NonTerminalRuleType.OPERATOR_GROUP_SELECTION_TYPE:
			case NonTerminalRuleType.MUTANT_GROUP_SELECTION_TYPE:
				return GroupSelectionTypeFactory.getInstance();
			case NonTerminalRuleType.OPERATOR_SORTING:
			case NonTerminalRuleType.MUTANT_SORTING:
				return SortingFactory.getInstance();
			case NonTerminalRuleType.OPERATOR_GROUPING:
			case NonTerminalRuleType.MUTANT_GROUPING:
				return GroupingFactory.getInstance();
			case NonTerminalRuleType.OPERATOR_GROUP_SORTING:
			case NonTerminalRuleType.MUTANT_GROUP_SORTING:
				return GroupSortingFactory.getInstance();
			// Specific
			case NonTerminalRuleType.OPERATOR_EXECUTION_TYPE:
				return OperatorExecutionTypeFactory.getInstance();
			case NonTerminalRuleType.HOM_GENERATION:
				return HOMGenerationFactory.getInstance();
			default:
				throw new IllegalArgumentException("Unidentified grammar rule: " + name);
		}
	}

	private static class FactoryFlyweightHolder {

		private static final HashMap<String, Factory> FLYWEIGHT = new HashMap<>();
	}
}
