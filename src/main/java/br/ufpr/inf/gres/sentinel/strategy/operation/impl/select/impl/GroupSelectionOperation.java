package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.impl;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.GroupingFunction;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.AbstractSelectionOperation;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.math.DoubleMath;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 * @param <T>
 */
public class GroupSelectionOperation<T> extends AbstractSelectionOperation<T> {

    private GroupingFunction<T> groupingFunction;
    private AbstractSelectionOperation<T> selectionOperation;

    public GroupSelectionOperation() {
        super("Group Selection", false);
    }

    public GroupingFunction<T> getGroupingFunction() {
        return groupingFunction;
    }

    public void setGroupingFunction(GroupingFunction<T> groupingFunction) {
        this.groupingFunction = groupingFunction;
    }

    public AbstractSelectionOperation getSelectionOperation() {
        return selectionOperation;
    }

    public void setSelectionOperation(AbstractSelectionOperation selectionOperation) {
        this.selectionOperation = selectionOperation;
    }

    @Override
    public List<T> doOperation(List<T> input) {
        checkArgument(percentage != 0D || quantity != 0, "No quantity or percentage defined for group selection!");
        checkNotNull(selectionType, "No group selection type defined for group selection!");
        checkNotNull(groupingFunction, "No grouping function defined for group selection!");
        checkNotNull(selectionOperation, "No operator selection type defined for group selection!");

        List<List<T>> groups = groupingFunction.doOperation(input);

        if (sorter != null) {
            Collections.sort(groups, sorter);
        }

        int numberToSelect;
        if (percentage != 0D) {
            numberToSelect = DoubleMath.roundToInt(groups.size() * percentage, RoundingMode.DOWN);
        } else {
            numberToSelect = quantity;
        }

        groups = selectionType.selectItems(groups, numberToSelect);

        List<T> result = new ArrayList<>();
        for (List<T> group : groups) {
            result.addAll(selectionOperation.doOperation(group));
        }
        return result;
    }

    @Override
    public boolean isSpecific() {
        boolean isSpecific = super.isSpecific();
        if (groupingFunction != null) {
            isSpecific = isSpecific || groupingFunction.isSpecific();
        }
        if (selectionOperation != null) {
            isSpecific = isSpecific || selectionOperation.isSpecific();
        }
        return isSpecific;
    }

}
