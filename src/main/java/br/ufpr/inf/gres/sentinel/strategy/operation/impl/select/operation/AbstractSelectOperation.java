package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public abstract class AbstractSelectOperation<T> extends Operation<Solution, Collection<Mutant>> {

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
    public Collection<Mutant> doOperation(Solution solution, Program program) {
        checkNotNull(this.selection, "No selection operation!");
        Collection<T> listToRetain = this.obtainList(solution);
        List<T> copyList = new ArrayList<>(listToRetain);
        listToRetain.clear();
        listToRetain.addAll(this.selection.doOperation(copyList, program));
        return this.next(solution, program);
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
    protected abstract Collection<T> obtainList(Solution solution);

}
