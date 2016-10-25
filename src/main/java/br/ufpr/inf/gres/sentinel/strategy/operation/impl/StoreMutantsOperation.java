package br.ufpr.inf.gres.sentinel.strategy.operation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.ArrayList;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public class StoreMutantsOperation extends Operation {

    public StoreMutantsOperation() {
        super(TerminalRuleType.STORE_MUTANTS, false);
    }

    @Override
    public SetUniqueList<Mutant> doOperation(Solution solution) {
        return SetUniqueList.setUniqueList(new ArrayList<>(solution.getMutants()));
    }

}
