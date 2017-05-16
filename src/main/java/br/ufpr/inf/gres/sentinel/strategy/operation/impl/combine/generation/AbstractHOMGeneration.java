package br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public abstract class AbstractHOMGeneration extends Operation<List<Mutant>, List<Mutant>> {

    /**
     *
     * @param name
     */
    public AbstractHOMGeneration(String name) {
        super(name);
    }

}
