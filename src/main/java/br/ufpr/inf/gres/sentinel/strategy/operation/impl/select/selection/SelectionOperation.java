package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.SelectionType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import com.google.common.math.DoubleMath;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @param <T>
 *
 * @author Giovani Guizzo
 */
public class SelectionOperation<T> extends Operation<List<T>, List<T>> {

    /**
     *
     */
    protected Double percentage = 0D;

    /**
     *
     */
    protected Integer quantity = 0;
    /**
     *
     */
    protected SelectionType selectionType;

    /**
     *
     */
    protected AbstractSorterOperation sorter;

    /**
     *
     */
    public SelectionOperation() {
        super("Selection");
    }

    /**
     *
     * @param name
     */
    public SelectionOperation(String name) {
        super(name);
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public List<T> doOperation(List<T> input, Program program) {
        if (input.size() > 0) {
            checkNotNull(this.selectionType, "No selection type defined for selection!");
            checkArgument(this.percentage != 0D || this.quantity != 0, "No quantity or percentage defined for selection! "
                    + "Percentage: " + this.percentage + ". Quantity: " + this.quantity + ".");
            int numberToSelect;
            if (this.percentage != 0D) {
                numberToSelect = DoubleMath.roundToInt(input.size() * this.percentage, RoundingMode.DOWN);
            } else {
                numberToSelect = this.quantity;
            }
            if (this.sorter != null) {
                input.sort(this.sorter);
            }
            return this.selectionType.selectItems(input, numberToSelect);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     *
     * @return
     */
    public Double getPercentage() {
        return this.percentage;
    }

    /**
     *
     * @param percentage
     */
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    /**
     *
     * @return
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public SelectionType getSelectionType() {
        return this.selectionType;
    }

    /**
     *
     * @param selectionType
     */
    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    /**
     *
     * @return
     */
    public AbstractSorterOperation getSorter() {
        return this.sorter;
    }

    /**
     *
     * @param sorter
     */
    public void setSorter(AbstractSorterOperation sorter) {
        this.sorter = sorter;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        boolean isSpecific = false;
        if (this.sorter != null) {
            isSpecific = isSpecific || this.sorter.isSpecific();
        }
        if (this.selectionType != null) {
            isSpecific = isSpecific || this.selectionType.isSpecific();
        }
        return isSpecific;
    }

}
