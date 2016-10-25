package br.ufpr.inf.gres.sentinel.strategy.operation.impl.selectiontype.impl;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.selectiontype.SelectionType;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class SequentialSelection<T> implements SelectionType<T> {

    @Override
    public List<T> selectItems(List<T> items, int numberOfItemsToSelect) {
        List<T> newList = new ArrayList<>();
        Iterator<T> cycle = Iterators.cycle(items);
        for (int i = 0; i < numberOfItemsToSelect; i++) {
            newList.add(cycle.next());
        }
        return newList;
    }

}
