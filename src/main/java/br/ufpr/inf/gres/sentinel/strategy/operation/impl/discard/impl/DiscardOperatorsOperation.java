package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.AbstractDiscardOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

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
    public Collection<Mutant> doOperation(Solution solution, Program program) {
        checkNotNull(this.selection, "No selection operation!");
        Collection<Operator> listToDiscard = this.obtainList(solution);
        listToDiscard = this.selection.doOperation(new ArrayList<>(listToDiscard), program);
        solution.getOperators().removeAll(listToDiscard);
        LinkedHashSet<Mutant> mutantsToRemove = listToDiscard.stream()
                .map(Operator::getGeneratedMutants)
                .reduce((mutants1, mutants2) -> {
                    LinkedHashSet<Mutant> reducedMutants = new LinkedHashSet<>();
                    reducedMutants.addAll(mutants1);
                    reducedMutants.addAll(mutants2);
                    return reducedMutants;
                })
                .orElse(new LinkedHashSet<>());
        solution.getMutants().removeAll(mutantsToRemove);
        return this.next(solution, program);
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    protected Collection<Operator> obtainList(Solution solution) {
        return solution.getOperators();
    }

}
