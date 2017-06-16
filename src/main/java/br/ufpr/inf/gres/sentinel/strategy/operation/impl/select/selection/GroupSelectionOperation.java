package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.list.SetUniqueList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @param <T>
 *
 * @author Giovani Guizzo
 */
public class GroupSelectionOperation<T> extends SelectionOperation<T> {

    private AbstractGroupingOperation<T> groupingFunction;
    private SelectionOperation<T> selectionOperation;

    /**
     *
     */
    public GroupSelectionOperation() {
        super("Group Selection");
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public List<T> doOperation(List<T> input, Program program) {
        checkNotNull(this.groupingFunction, "No grouping function defined for group selection!");
        checkNotNull(this.selectionOperation, "No operator selection type defined for group selection!");
        List<T> result = SetUniqueList.setUniqueList(new ArrayList<>());
        List<List<T>> groups = this.groupingFunction.doOperation(input, program);
        if (groups.size() > 0) {
            groups = this.doSelection(groups);
            for (List<T> group : groups) {
                result.addAll(this.selectionOperation.doOperation(group, program));
            }
        }
        return result;
    }

    /**
     *
     * @return
     */
    public AbstractGroupingOperation<T> getGroupingFunction() {
        return this.groupingFunction;
    }

    /**
     *
     * @param groupingFunction
     */
    public void setGroupingFunction(AbstractGroupingOperation<T> groupingFunction) {
        this.groupingFunction = groupingFunction;
    }

    /**
     *
     * @return
     */
    public SelectionOperation getSelectionOperation() {
        return this.selectionOperation;
    }

    /**
     *
     * @param selectionOperation
     */
    public void setSelectionOperation(SelectionOperation selectionOperation) {
        this.selectionOperation = selectionOperation;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        boolean isSpecific = super.isSpecific();
        if (this.groupingFunction != null) {
            isSpecific = isSpecific || this.groupingFunction.isSpecific();
        }
        if (this.selectionOperation != null) {
            isSpecific = isSpecific || this.selectionOperation.isSpecific();
        }
        return isSpecific;
    }

}
