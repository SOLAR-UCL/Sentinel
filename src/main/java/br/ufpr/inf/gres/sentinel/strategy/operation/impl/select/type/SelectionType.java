package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public abstract class SelectionType<T> extends Operation<Collection<T>, Collection<T>> {

    private static final Random RANDOM = new Random();

    /**
     *
     * @param name
     */
    public SelectionType(String name) {
        super(name);
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public Collection<T> doOperation(Collection<T> input, Program program) {
        if (!input.isEmpty()) {
            return this.selectItems(input, RANDOM.nextInt(input.size()) + 1);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param items
     * @param numberOfItemsToSelect
     * @return
     */
    public abstract Collection<T> selectItems(Collection<T> items, int numberOfItemsToSelect);

}
