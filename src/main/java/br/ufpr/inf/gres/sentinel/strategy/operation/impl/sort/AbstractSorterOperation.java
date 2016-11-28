package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public abstract class AbstractSorterOperation<T> extends Operation<List<T>, Integer> implements Comparator<T> {

    public AbstractSorterOperation(String name, boolean specific) {
        super(name, specific);
    }

    @Override
    public Integer doOperation(List<T> input) {
        return compare(input.get(0), input.get(1));
    }

    @Override
    public AbstractSorterOperation<T> reversed() {
        return new ReversedAbstractSorterOperation(this);
    }

    private static class ReversedAbstractSorterOperation<T> extends AbstractSorterOperation<T> {

        private AbstractSorterOperation sorter;

        public ReversedAbstractSorterOperation(AbstractSorterOperation<T> sorter) {
            super(sorter.getName(), sorter.isSpecific());
            this.sorter = sorter;
        }

        @Override
        public int compare(Object o1, Object o2) {
            return sorter.compare(o2, o1);
        }

    }

}
