package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public abstract class AbstractSorterOperation<T> extends Operation<List<T>, Integer> implements Comparator<T> {

	private boolean reversed = false;

	public AbstractSorterOperation(String name) {
		super(name);
	}

	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	@Override
	public Integer doOperation(List<T> input) {
		return compare(input.get(0), input.get(1));
	}

	@Override
	public int compare(T o1, T o2) {
		Comparator<T> comparator = Comparator.comparing(createSortingFunction());
		if (reversed) {
			comparator = comparator.reversed();
		}
		return comparator.compare(o1, o2);
	}

	public abstract Function<T, ? extends Comparable> createSortingFunction();

}
