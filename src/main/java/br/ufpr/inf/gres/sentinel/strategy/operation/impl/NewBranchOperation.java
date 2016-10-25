package br.ufpr.inf.gres.sentinel.strategy.operation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.ArrayList;
import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.lang3.ObjectUtils;

/**
 *
 * @author Giovani Guizzo
 */
public class NewBranchOperation extends Operation {

    private Operation secondSuccessor;

    public Operation getSecondSuccessor() {
        return secondSuccessor;
    }

    public void setSecondSuccessor(Operation secondSuccessor) {
        this.secondSuccessor = secondSuccessor;
    }

    @Override
    public SetUniqueList<Mutant> doOperation(Solution solution) {
        Solution clonedSolution = ObjectUtils.clone(solution);

        SetUniqueList<Mutant> result = next(solution);
        SetUniqueList<Mutant> newList = SetUniqueList.setUniqueList(new ArrayList<>(result));

        if (secondSuccessor != null) {
            newList.addAll(secondSuccessor.doOperation(clonedSolution));
        }
        return newList;
    }

}
