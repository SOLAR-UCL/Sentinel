package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant.MutantsOperatorTypeComparator;
import java.util.function.Function;

/**
 *
 * @author Giovani Guizzo
 */
public class GroupMutantsByOperatorType extends AbstractGroupingOperation<Mutant> {

    private MutantsOperatorTypeComparator delegate;

    /**
     *
     */
    public GroupMutantsByOperatorType() {
        super("Group Mutants by " + TerminalRuleType.OPERATOR_TYPE);
        this.delegate = new MutantsOperatorTypeComparator();
    }

    /**
     *
     * @return
     */
    @Override
    public Function<Mutant, String> createGroupingFunction() {
        return this.delegate.createSortingFunction();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        return this.delegate.isSpecific();
    }

}
