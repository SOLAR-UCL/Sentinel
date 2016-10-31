package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import com.google.common.collect.Lists;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class LastToFirst<T> implements SelectionType<T> {

    @Override
    public List<T> selectItems(List<T> items, int numberOfItemsToSelect) {
        List<T> newList = new ArrayList<>();
        List<List<T>> partition = new ArrayList<>(Lists.partition(items, IntMath.divide(items.size(), 2, RoundingMode.UP)));
        partition.set(1, Lists.reverse(partition.get(1)));
        for (int i = 0; i < numberOfItemsToSelect; i++) {
            int indexToGet = i / 2;
            List<T> sourceList = partition.get(i % 2);
            newList.add(sourceList.get(indexToGet % sourceList.size()));
        }
        return newList;
    }

}
