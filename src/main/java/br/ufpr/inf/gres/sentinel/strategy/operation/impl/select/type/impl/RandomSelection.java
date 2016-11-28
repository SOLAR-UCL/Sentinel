package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Giovani Guizzo
 */
public class RandomSelection<T> extends Operation<List<T>, List<T>> implements SelectionType<T> {

    private static final Random RANDOM = new Random();

    public RandomSelection() {
        super(TerminalRuleType.RANDOM, false);
    }

    @Override
    public List<T> selectItems(List<T> items, int numberOfItemsToSelect) {
        List<T> newList = new ArrayList<>();
        int size = items.size();
        for (int i = 0; i < numberOfItemsToSelect; i++) {
            newList.add(items.get(RANDOM.nextInt(size)));
        }
        return newList;
    }

    @Override
    public List<T> doOperation(List<T> input) {
        return selectItems(input, RANDOM.nextInt(input.size()));
    }

}
