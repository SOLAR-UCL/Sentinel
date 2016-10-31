package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Giovani Guizzo
 */
public class RandomSelection<T> implements SelectionType<T> {

    private static final Random RANDOM = new Random();

    @Override
    public List<T> selectItems(List<T> items, int numberOfItemsToSelect) {
        List<T> newList = new ArrayList<>();
        int size = items.size();
        for (int i = 0; i < numberOfItemsToSelect; i++) {
            newList.add(items.get(RANDOM.nextInt(size)));
        }
        return newList;
    }

}
