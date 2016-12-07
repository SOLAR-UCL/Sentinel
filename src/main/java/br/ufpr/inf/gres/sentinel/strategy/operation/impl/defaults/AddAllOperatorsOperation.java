package br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class AddAllOperatorsOperation extends Operation<Solution, List<Mutant>> {

    public AddAllOperatorsOperation() {
        super(TerminalRuleType.ALL_OPERATORS, false);
    }

    @Override
    public List<Mutant> doOperation(Solution solution) {
        solution.getOperators().addAll(IntegrationFacade.getFacade().getAllOperators());
        return next(solution);
    }

}
