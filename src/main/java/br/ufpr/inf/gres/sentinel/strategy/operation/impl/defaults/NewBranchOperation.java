package br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author Giovani Guizzo
 */
public class NewBranchOperation extends Operation<Solution, Collection<Mutant>> {

    private Operation<Solution, Collection<Mutant>> secondSuccessor;

    /**
     *
     */
    public NewBranchOperation() {
        super(TerminalRuleType.NEW_BRANCH);
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public Collection<Mutant> doOperation(Solution solution, Program program) {
        Solution clonedSolution = new Solution(solution);
        Collection<Mutant> result = this.next(solution, program);
        LinkedHashSet<Mutant> newList = new LinkedHashSet<>(result);
        if (this.secondSuccessor != null) {
            newList.addAll(this.secondSuccessor.doOperation(clonedSolution, program));
        }
        return newList;
    }

    /**
     *
     * @return
     */
    public Operation<Solution, Collection<Mutant>> getSecondSuccessor() {
        return this.secondSuccessor;
    }

    /**
     *
     * @param secondSuccessor
     */
    public void setSecondSuccessor(Operation<Solution, Collection<Mutant>> secondSuccessor) {
        this.secondSuccessor = secondSuccessor;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        return false;
    }

}
