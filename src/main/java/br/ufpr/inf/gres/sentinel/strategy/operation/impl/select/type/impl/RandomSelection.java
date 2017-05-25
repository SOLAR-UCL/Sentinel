package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import edu.emory.mathcs.backport.java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public class RandomSelection<T> extends SelectionType<T> {

    private static final Random RANDOM = new Random();

    /**
     *
     */
    public RandomSelection() {
        super(TerminalRuleType.RANDOM);
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
        if (!items.isEmpty() && numberOfItemsToSelect > 0) {
            newList = new ArrayList<>(items);
            Collections.shuffle(newList);
            if (newList.size() <= numberOfItemsToSelect) {
                return newList;
            } else {
                return newList.subList(0, numberOfItemsToSelect);
            }
        }
        return newList;
    }

}
