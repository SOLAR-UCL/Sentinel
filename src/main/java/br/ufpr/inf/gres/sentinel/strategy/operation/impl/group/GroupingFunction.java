package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Giovani Guizzo
 */
public class GroupingFunction<T> extends Operation<List<T>, List<List<T>>> {

    private final Function<T, String> grouperFunction;

    public GroupingFunction(String name, boolean specific, Function<T, String> grouperFunction) {
        super(name, specific);
        this.grouperFunction = grouperFunction;
    }

    @Override
    public List<List<T>> doOperation(List<T> input) {
        Map<String, List<T>> collect = input
                .stream()
                .collect(Collectors.groupingBy(grouperFunction));
        return new ArrayList<>(collect.values());
    }

}
