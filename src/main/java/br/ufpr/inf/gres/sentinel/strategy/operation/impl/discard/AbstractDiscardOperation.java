package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Giovani Guizzo
 */
public abstract class AbstractDiscardOperation<T> extends Operation<Solution, List<Mutant>> {

    protected SelectionOperation<T> selection;

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
        return selection != null && selection.isSpecific();
    }

    @Override
    public List<Mutant> doOperation(Solution solution) {
        checkNotNull(selection, "No selection operation!");
        List<T> listToDiscard = obtainList(solution);
        listToDiscard.removeAll(selection.doOperation(new ArrayList<>(listToDiscard)));
        return next(solution);
    }

    protected abstract List<T> obtainList(Solution solution);

}
