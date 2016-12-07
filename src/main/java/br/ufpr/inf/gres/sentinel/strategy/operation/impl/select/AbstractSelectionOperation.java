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

    public AbstractSelectionOperation(String name) {
        super(name);
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

    public AbstractSorterOperation getSorter() {
        return sorter;
    }

    public void setSorter(AbstractSorterOperation sorter) {
        this.sorter = sorter;
    }

    @Override
    public boolean isSpecific() {
        boolean isSpecific = false;
        if (sorter != null) {
            isSpecific = isSpecific || sorter.isSpecific();
        }
        if (selectionType != null) {
            isSpecific = isSpecific || selectionType.isSpecific();
        }
        return isSpecific;
    }

}
