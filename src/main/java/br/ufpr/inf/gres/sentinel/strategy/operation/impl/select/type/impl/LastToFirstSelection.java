package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import com.google.common.collect.Lists;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public class LastToFirstSelection<T> extends SelectionType<T> {

    /**
     *
     */
    public LastToFirstSelection() {
        super(TerminalRuleType.LAST_TO_FIRST);
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
    public Collection<T> selectItems(Collection<T> items, int numberOfItemsToSelect) {
        if (items.size() > 1 && numberOfItemsToSelect > 0) {
            List<T> newList = new ArrayList<>(items);
            List<List<T>> partition = new ArrayList<>(Lists.partition(newList, IntMath.divide(items.size(), 2, RoundingMode.UP)));
            newList = new ArrayList<>();
            partition.set(1, Lists.reverse(partition.get(1)));
            for (int i = 0; i < numberOfItemsToSelect && i < items.size(); i++) {
                int indexToGet = i / 2;
                List<T> sourceList = partition.get(i % 2);
                newList.add(sourceList.get(indexToGet % sourceList.size()));
            }
            return newList;
        } else if (items.size() == 1) {
            return new ArrayList<>(items);
        } else {
            return new ArrayList<>();
        }
    }

}
