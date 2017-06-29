package br.ufpr.inf.gres.sentinel.strategy;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 *
 * @author Giovani Guizzo
 */
public class Strategy {

    private Operation<Solution, Collection<Mutant>> firstOperation;

    /**
     *
     */
    public Strategy() {
    }

    /**
     *
     * @param firstOperation
     */
    public Strategy(Operation<Solution, Collection<Mutant>> firstOperation) {
        this.firstOperation = firstOperation;
    }

    /**
     *
     * @return
     */
    public Operation<Solution, Collection<Mutant>> getFirstOperation() {
        return this.firstOperation;
    }

    /**
     *
     * @param firstOperation
     */
    public void setFirstOperation(Operation<Solution, Collection<Mutant>> firstOperation) {
        this.firstOperation = firstOperation;
    }

    /**
     *
     * @return
     */
    public Collection<Mutant> run(Program program) {
        if (this.firstOperation != null) {
            return this.firstOperation.doOperation(new Solution(), program);
        }
        return new LinkedHashSet<>();
    }

    @Override
    public String toString() {
        if (this.firstOperation == null) {
            return "Empty Strategy";
        }
        return this.firstOperation.toStringComplete();
    }

}
