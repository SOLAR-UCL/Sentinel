package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class GroupMutants extends Operation<Solution, List<Mutant>> {

    public GroupMutants(String name, boolean specific) {
        super(name, specific);
    }

    @Override
    public List<Mutant> doOperation(Solution solution) {
        solution.setMutantGrouping(name);
        return next(solution);
    }

}
