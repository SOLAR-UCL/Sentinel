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
public class StoreMutantsOperation extends Operation<Solution, List<Mutant>> {

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
    public List<Mutant> doOperation(Solution solution, Program program) {
        return SetUniqueList.setUniqueList(new ArrayList<>(solution.getMutants()));
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
