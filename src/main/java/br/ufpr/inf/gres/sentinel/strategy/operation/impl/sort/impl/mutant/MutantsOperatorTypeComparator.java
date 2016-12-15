package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import com.google.common.collect.Iterables;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.Comparator;

/**
 * @author Giovani Guizzo
 */
public class MutantsOperatorTypeComparator extends AbstractSorterOperation<Mutant> {

	public MutantsOperatorTypeComparator() {
		super("Sort Mutants by " + TerminalRuleType.OPERATOR_TYPE);
	}

	@Override
	public int compare(Mutant mutant1, Mutant mutant2) {
		SetUniqueList<Operator> operators1 = mutant1.getOperators();
		SetUniqueList<Operator> operators2 = mutant2.getOperators();
		Comparator<Operator> comparator = Comparator.comparing(Operator::getType);
		operators1.sort(comparator);
		operators2.sort(comparator);
		Operator operator1 = Iterables.getFirst(operators1, new Operator("Unknown", "Unknown"));
		Operator operator2 = Iterables.getFirst(operators2, new Operator("Unknown", "Unknown"));
		return operator1.getType().compareToIgnoreCase(operator2.getType());
	}

	@Override
	public boolean isSpecific() {
		return false;
	}
}
