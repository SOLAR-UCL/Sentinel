package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import java.util.function.Function;

/**
 *
 * @author Giovani Guizzo
 */
public class GroupMutantsByFOMOrHOM extends AbstractGroupingOperation<Mutant> {

    /**
     *
     */
    public GroupMutantsByFOMOrHOM() {
        super("Group Mutants by " + TerminalRuleType.FOM_OR_HOM);
    }

    /**
     *
     * @return
     */
    @Override
    public Function<Mutant, String> createGroupingFunction() {
        return mutant -> mutant.getConstituentMutants().size() <= 1 ? "FOM" : "HOM";
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
