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
public class StoreMutantsOperation extends Operation<Solution, Collection<Mutant>> {

    /**
     *
     */
    public StoreMutantsOperation() {
        super(TerminalRuleType.STORE_MUTANTS);
    }

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public Collection<Mutant> doOperation(Solution solution, Program program) {
        return new LinkedHashSet(solution.getMutants());
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
