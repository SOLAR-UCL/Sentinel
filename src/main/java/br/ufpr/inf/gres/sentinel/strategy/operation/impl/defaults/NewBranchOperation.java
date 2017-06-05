package br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 * @author Giovani Guizzo
 */
public class NewBranchOperation extends Operation<Solution, List<Mutant>> {

    private Operation<Solution, List<Mutant>> secondSuccessor;

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
    public List<Mutant> doOperation(Solution solution, Program program) {
        Solution clonedSolution = new Solution(solution);
        List<Mutant> result = this.next(solution, program);
        SetUniqueList<Mutant> newList = SetUniqueList.setUniqueList(new ArrayList<>(result));
        if (this.secondSuccessor != null) {
            newList.addAll(this.secondSuccessor.doOperation(clonedSolution, program));
        }
        return newList;
    }

    /**
     *
     * @return
     */
    public Operation<Solution, List<Mutant>> getSecondSuccessor() {
        return this.secondSuccessor;
    }

    /**
     *
     * @param secondSuccessor
     */
    public void setSecondSuccessor(Operation<Solution, List<Mutant>> secondSuccessor) {
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
