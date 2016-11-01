package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.math.DoubleMath;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 * @param <T>
 */
public class SelectionOperation<T> extends AbstractSelectionOperation<T> {

    public SelectionOperation() {
        super("Selection", false);
    }

    public SelectionOperation(String name, boolean specific) {
        super(name, specific);
    }

    @Override
    public List<T> doOperation(List<T> source) {
        checkArgument(percentage != 0D || quantity != 0, "No quantity or percentage defined for selection!");
        checkNotNull(selectionType, "No selection type defined for selection!");

        if (sorter != null) {
            if (descendingOrder) {
                Collections.sort(source, sorter.reversed());
            } else {
                Collections.sort(source, sorter);
            }
        }
        int numberToSelect;
        if (percentage != 0D) {
            numberToSelect = DoubleMath.roundToInt(source.size() * percentage, RoundingMode.DOWN);
        } else {
            numberToSelect = quantity;
        }
        return selectionType.selectItems(source, numberToSelect);
    }

}
