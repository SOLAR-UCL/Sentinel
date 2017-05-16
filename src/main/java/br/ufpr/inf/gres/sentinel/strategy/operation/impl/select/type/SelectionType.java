package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public abstract class SelectionType<T> extends Operation<List<T>, List<T>> {

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
    public List<T> doOperation(List<T> input) {
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
    public abstract List<T> selectItems(List<T> items, int numberOfItemsToSelect);

}
