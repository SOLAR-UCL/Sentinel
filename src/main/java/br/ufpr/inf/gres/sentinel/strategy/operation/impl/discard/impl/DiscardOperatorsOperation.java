package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.AbstractDiscardOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.list.SetUniqueList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Giovani Guizzo
 */
public class DiscardOperatorsOperation extends AbstractDiscardOperation<Operator> {

    /**
     *
     */
    public DiscardOperatorsOperation() {
        super(TerminalRuleType.DISCARD_OPERATORS);
    }

    /**
     *
     * @param selection
     */
    public DiscardOperatorsOperation(SelectionOperation<Operator> selection) {
        super(TerminalRuleType.DISCARD_OPERATORS, selection);
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public List<Mutant> doOperation(Solution solution) {
        checkNotNull(this.selection, "No selection operation!");
        List<Operator> listToDiscard = this.obtainList(solution);
        listToDiscard = this.selection.doOperation(new ArrayList<>(listToDiscard));
        solution.getOperators().removeAll(listToDiscard);
        SetUniqueList<Mutant> mutantsToRemove = listToDiscard.stream()
                .map(Operator::getGeneratedMutants)
                .reduce((mutants1, mutants2) -> {
                    SetUniqueList<Mutant> reducedMutants = SetUniqueList.setUniqueList(new ArrayList<>());
                    reducedMutants.addAll(mutants1);
                    reducedMutants.addAll(mutants2);
                    return reducedMutants;
                })
                .orElse(SetUniqueList.setUniqueList(new ArrayList<>()));
        solution.getMutants().removeAll(mutantsToRemove);
        return this.next(solution);
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    protected List<Operator> obtainList(Solution solution) {
        return solution.getOperators();
    }

}
