package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;

import java.util.List;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class QuantityInGroupComparator<T> extends AbstractSorterOperation<List<T>> {

	public QuantityInGroupComparator() {
		super("Sort Groups by Quantity in Group");
	}

	@Override
	public boolean isSpecific() {
		return false;
	}

	@Override
	public Function<List<T>, Integer> createSortingFunction() {
		return List::size;
	}
}
