package br.ufpr.inf.gres.sentinel.strategy;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public class Strategy {

    private Operation<Solution, List<Mutant>> firstOperation;

    /**
     *
     */
    public Strategy() {
    }

    /**
     *
     * @param firstOperation
     */
    public Strategy(Operation<Solution, List<Mutant>> firstOperation) {
        this.firstOperation = firstOperation;
    }

    /**
     *
     * @return
     */
    public Operation<Solution, List<Mutant>> getFirstOperation() {
        return this.firstOperation;
    }

    /**
     *
     * @param firstOperation
     */
    public void setFirstOperation(Operation<Solution, List<Mutant>> firstOperation) {
        this.firstOperation = firstOperation;
    }

    /**
     *
     * @return
     */
    public List<Mutant> run() {
        if (this.firstOperation != null) {
            return this.firstOperation.doOperation(new Solution());
        }
        return SetUniqueList.setUniqueList(new ArrayList<>());
    }

    @Override
    public String toString() {
        if (this.firstOperation == null) {
            return "Empty Strategy";
        }
        return this.firstOperation.toStringComplete();
    }

}
