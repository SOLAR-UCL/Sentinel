package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;

/**
 * @author Giovani Guizzo
 */
public class MutantsOperatorTypeComparator extends AbstractSorterOperation<Mutant> {

    /**
     *
     */
    public MutantsOperatorTypeComparator() {
        super("Sort Mutants by " + TerminalRuleType.OPERATOR_TYPE);
    }

    /**
     *
     * @return
     */
    @Override
    public Function<Mutant, String> createSortingFunction() {
        return mutant -> {
            ArrayList<Operator> operators = new ArrayList<>(mutant.getOperators());
            Comparator<Operator> comparator = Comparator.comparing(Operator::getType);
            operators.sort(comparator);
            Operator operator = Iterables.getFirst(operators, new Operator("Unknown", "Unknown"));
            return operator.getType();
        };
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
