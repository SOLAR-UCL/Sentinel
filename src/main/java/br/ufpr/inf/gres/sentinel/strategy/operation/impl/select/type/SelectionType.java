package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public abstract class SelectionType<T> extends Operation<List<T>, List<T>> {

    public SelectionType(String name) {
        super(name);
    }

    public abstract List<T> selectItems(List<T> items, int numberOfItemsToSelect);

}
