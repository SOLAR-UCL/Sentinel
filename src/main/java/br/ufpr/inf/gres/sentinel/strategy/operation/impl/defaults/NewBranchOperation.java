package br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public class NewBranchOperation extends Operation<Solution, List<Mutant>> {

    private Operation<Solution, List<Mutant>> secondSuccessor;

    public NewBranchOperation() {
        super(TerminalRuleType.NEW_BRANCH, false);
    }

    public Operation<Solution, List<Mutant>> getSecondSuccessor() {
        return secondSuccessor;
    }

    public void setSecondSuccessor(Operation<Solution, List<Mutant>> secondSuccessor) {
        this.secondSuccessor = secondSuccessor;
    }

    @Override
    public List<Mutant> doOperation(Solution solution) {
        Solution clonedSolution = new Solution(solution);

        List<Mutant> result = next(solution);
        SetUniqueList<Mutant> newList = SetUniqueList.setUniqueList(new ArrayList<>(result));

        if (secondSuccessor != null) {
            newList.addAll(secondSuccessor.doOperation(clonedSolution));
        }
        return newList;
    }

}
