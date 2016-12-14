package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard;

import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public abstract class AbstractDiscardOperation<T> extends Operation<Solution, List<T>> {

    private SelectionOperation<T> selection;

    public AbstractDiscardOperation(String name) {
        super(name);
    }

    public AbstractDiscardOperation(String name, SelectionOperation<T> selection) {
        super(name);
        this.selection = selection;
    }

    public SelectionOperation<T> getSelection() {
        return selection;
    }

    public void setSelection(SelectionOperation<T> selection) {
        this.selection = selection;
    }

    @Override
    public boolean isSpecific() {
        return selection != null ? selection.isSpecific() : false;
    }

    @Override
    public List<T> doOperation(Solution solution) {
        List<T> listToDiscard = obtainList(solution);
        if (selection != null) {
            listToDiscard.removeAll(selection.doOperation(new ArrayList<>(listToDiscard)));
        }
        return next(solution);
    }

    protected abstract List<T> obtainList(Solution solution);

}
