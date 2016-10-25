package br.ufpr.inf.gres.sentinel.strategy.operation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.integration.Facade;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public class AddAllOperatorsOperation extends Operation {

    public AddAllOperatorsOperation() {
        super(TerminalRuleType.ALL_OPERATORS, false);
    }

    @Override
    public SetUniqueList<Mutant> doOperation(Solution solution) {
        solution.getOperators().addAll(Facade.getFacade().getAllOperators());
        return next(solution);
    }

}
