package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Giovani Guizzo
 */
public class SequentialSelection<T> extends SelectionType<T> {

    private static final Random RANDOM = new Random();

    public SequentialSelection() {
        super(TerminalRuleType.SEQUENTIAL, false);
    }

    @Override
    public List<T> selectItems(List<T> items, int numberOfItemsToSelect) {
        List<T> newList = new ArrayList<>();
        Iterator<T> cycle = Iterators.cycle(items);
        for (int i = 0; i < numberOfItemsToSelect; i++) {
            newList.add(cycle.next());
        }
        return newList;
    }

    @Override
    public List<T> doOperation(List<T> input) {
        return selectItems(input, RANDOM.nextInt(input.size()));
    }

}
