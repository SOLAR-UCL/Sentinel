package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public abstract class AbstractDiscardOperation<T> extends Operation<Solution, Collection<Mutant>> {

    /**
     *
     */
    protected SelectionOperation<T> selection;

    /**
     *
     * @param name
     */
    public AbstractDiscardOperation(String name) {
        super(name);
    }

    /**
     *
     * @param name
     * @param selection
     */
    public AbstractDiscardOperation(String name, SelectionOperation<T> selection) {
        super(name);
        this.selection = selection;
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public Collection<Mutant> doOperation(Solution solution, Program program) {
        checkNotNull(this.selection, "No selection operation!");
        Collection<T> listToDiscard = this.obtainList(solution);
        listToDiscard.removeAll(new HashSet<>(this.selection.doOperation(listToDiscard, program)));
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
