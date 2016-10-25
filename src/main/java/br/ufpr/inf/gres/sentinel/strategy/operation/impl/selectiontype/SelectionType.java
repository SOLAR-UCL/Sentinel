package br.ufpr.inf.gres.sentinel.strategy.operation.impl.selectiontype;

import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public interface SelectionType<T> {

    public List<T> selectItems(List<T> items, int numberOfItemsToSelect);

}
