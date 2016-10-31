package br.ufpr.inf.gres.sentinel.strategy.operation.impl;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
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
public class SelectionOperation<T> extends Operation<List<T>, List<T>> {

    private SelectionType<T> selectionType;
    private Integer quantity = 0;
    private Double percentage = 0D;

    private AbstractSorterOperation<T> comparator;
    private boolean descendingOrder = false;

    public SelectionOperation(String name) {
        super("Selection", false);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public SelectionType<T> getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(SelectionType<T> selectionType) {
        this.selectionType = selectionType;
    }

    public boolean isDescendingOrder() {
        return descendingOrder;
    }

    public void setDescendingOrder(boolean descendingOrder) {
        this.descendingOrder = descendingOrder;
    }

    public AbstractSorterOperation<T> getComparator() {
        return comparator;
    }

    public void setComparator(AbstractSorterOperation<T> comparator) {
        this.comparator = comparator;
        this.specific = comparator.isSpecific();
    }

    @Override
    public List<T> doOperation(List<T> source) {
        checkArgument(percentage != 0D || quantity != 0, "No quantity or percentage defined for selection!");
        checkNotNull(selectionType, "No selection type defined for selection!");

        if (comparator != null) {
            if (descendingOrder) {
                Collections.sort(source, comparator.reversed());
            } else {
                Collections.sort(source, comparator);
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
