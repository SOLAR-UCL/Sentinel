package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.GroupingFunction;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 * @param <T>
 */
public class GroupSelectionOperation<T> extends AbstractSelectionOperation<T> {

    private GroupingFunction<T> groupingFunction;
    private AbstractSelectionOperation selectionOperation;

    public GroupSelectionOperation() {
        super("Group Selection", false);
    }

    public GroupSelectionOperation(String name, boolean specific) {
        super(name, specific);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
