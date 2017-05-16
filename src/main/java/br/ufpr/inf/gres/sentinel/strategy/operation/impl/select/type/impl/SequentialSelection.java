package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public class SequentialSelection<T> extends SelectionType<T> {

    /**
     *
     */
    public SequentialSelection() {
        super(TerminalRuleType.SEQUENTIAL);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        return false;
    }

    /**
     *
     * @param items
     * @param numberOfItemsToSelect
     * @return
     */
    @Override
    public List<T> selectItems(List<T> items, int numberOfItemsToSelect) {
        List<T> newList = new ArrayList<>();
        if (!items.isEmpty()) {
            Iterator<T> cycle = Iterators.cycle(items);
            for (int i = 0; i < numberOfItemsToSelect; i++) {
                newList.add(cycle.next());
            }
        }
        return newList;
    }

}
