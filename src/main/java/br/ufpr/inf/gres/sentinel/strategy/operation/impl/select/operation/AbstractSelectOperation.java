package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public abstract class AbstractSelectOperation<T> extends Operation<Solution, List<Mutant>> {

    private SelectionOperation<T> selection;

    /**
     *
     * @param name
     * @param selection
     */
    public AbstractSelectOperation(String name, SelectionOperation<T> selection) {
        super(name);
        this.selection = selection;
    }

    /**
     *
     * @param name
     */
    public AbstractSelectOperation(String name) {
        super(name);
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public List<Mutant> doOperation(Solution solution) {
        checkNotNull(this.selection, "No selection operation!");
        List<T> listToRetain = this.obtainList(solution);
        listToRetain.retainAll(this.selection.doOperation(new ArrayList<>(listToRetain)));
        return this.next(solution);
    }

    /**
     *
     * @return
     */
    public SelectionOperation<T> getSelection() {
        return this.selection;
    }

    /**
     *
     * @param selection
     */
    public void setSelection(SelectionOperation<T> selection) {
        this.selection = selection;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        return this.selection != null && this.selection.isSpecific();
    }

    /**
     *
     * @param solution
     * @return
     */
    protected abstract List<T> obtainList(Solution solution);

}
