package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public abstract class AbstractSorterOperation<T> extends Operation<List<T>, Integer> implements Comparator<T> {

    private boolean reversed = false;

    /**
     *
     * @param name
     */
    public AbstractSorterOperation(String name) {
        super(name);
    }

    @Override
    public int compare(T o1, T o2) {
        Comparator<T> comparator = Comparator.comparing(this.createSortingFunction());
        if (this.reversed) {
            comparator = comparator.reversed();
        }
        return comparator.compare(o1, o2);
    }

    /**
     *
     * @return
     */
    public abstract Function<T, ? extends Comparable> createSortingFunction();

    /**
     *
     * @param input
     * @return
     */
    @Override
    public Integer doOperation(List<T> input, Program program) {
        return this.compare(input.get(0), input.get(1));
    }

    /**
     *
     * @return
     */
    public boolean isReversed() {
        return this.reversed;
    }

    /**
     *
     * @param reversed
     */
    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

}
