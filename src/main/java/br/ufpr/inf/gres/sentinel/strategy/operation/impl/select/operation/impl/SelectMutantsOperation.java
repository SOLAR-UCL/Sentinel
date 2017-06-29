package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.AbstractSelectOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.Collection;

/**
 * @author Giovani Guizzo
 */
public class SelectMutantsOperation extends AbstractSelectOperation<Mutant> {

    /**
     *
     */
    public SelectMutantsOperation() {
        super(TerminalRuleType.SELECT_MUTANTS);
    }

    /**
     *
     * @param selection
     */
    public SelectMutantsOperation(SelectionOperation<Mutant> selection) {
        super(TerminalRuleType.SELECT_MUTANTS, selection);
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public Collection<Mutant> obtainList(Solution solution) {
        return solution.getMutants();
    }

}
