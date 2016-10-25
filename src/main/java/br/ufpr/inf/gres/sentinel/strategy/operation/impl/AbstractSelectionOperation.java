package br.ufpr.inf.gres.sentinel.strategy.operation.impl;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.selectiontype.SelectionType;
import java.util.Comparator;

/**
 *
 * @author Giovani Guizzo
 */
public abstract class AbstractSelectionOperation<T> extends Operation {

    private SelectionType<T> selectionType;
    private Integer quantity;
    private Double percentage;

    private Comparator<T> comparator;
    private boolean descendingOrder;

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

    public Comparator<T> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

}
