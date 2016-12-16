package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class MutantsOperatorComparator extends AbstractSorterOperation<Mutant> {

	public MutantsOperatorComparator() {
		super("Sort Mutants by " + TerminalRuleType.OPERATOR);
	}

	@Override
	public boolean isSpecific() {
		return false;
	}

	@Override
	public Function<Mutant, String> createSortingFunction() {
		return mutant -> {
			ArrayList<Operator> operators = new ArrayList<>(mutant.getOperators());
			Function<Operator, String> getName = Operator::getName;
			Comparator<Operator> comparator = Comparator.comparing(getName);
			operators.sort(comparator);
			Operator operator = Iterables.getFirst(operators, new Operator("Unknown", "Unknown"));
			return operator.getName();
		};
	}

}
