package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public abstract class AbstractSelectionOperation<T> extends Operation<List<T>, List<T>> {

    protected SelectionType selectionType;
    protected Integer quantity = 0;
    protected Double percentage = 0D;

    protected AbstractSorterOperation sorter;
    protected boolean descendingOrder = false;

    public AbstractSelectionOperation(String name, boolean specific) {
        super(name, specific);
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

    public SelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    public boolean isDescendingOrder() {
        return descendingOrder;
    }

    public void setDescendingOrder(boolean descendingOrder) {
        this.descendingOrder = descendingOrder;
    }

    public AbstractSorterOperation getSorter() {
        return sorter;
    }

    public void setSorter(AbstractSorterOperation sorter) {
        this.sorter = sorter;
    }

    @Override
    public boolean isSpecific() {
        return sorter != null ? sorter.isSpecific() : specific;
    }

}
