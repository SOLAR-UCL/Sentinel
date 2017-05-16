package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.AbstractDiscardOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class DiscardMutantsOperation extends AbstractDiscardOperation<Mutant> {

    /**
     *
     */
    public DiscardMutantsOperation() {
        super(TerminalRuleType.DISCARD_MUTANTS);
    }

    /**
     *
     * @param selection
     */
    public DiscardMutantsOperation(SelectionOperation<Mutant> selection) {
        super(TerminalRuleType.DISCARD_MUTANTS, selection);
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    protected List<Mutant> obtainList(Solution solution) {
        return solution.getMutants();
    }

}
