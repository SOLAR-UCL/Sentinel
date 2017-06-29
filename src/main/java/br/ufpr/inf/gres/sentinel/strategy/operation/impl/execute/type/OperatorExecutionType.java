package br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.Collection;

/**
 * @author Giovani Guizzo
 */
public abstract class OperatorExecutionType extends Operation<Collection<Operator>, Collection<Mutant>> {

    /**
     *
     * @param name
     */
    public OperatorExecutionType(String name) {
        super(name);
    }

}
