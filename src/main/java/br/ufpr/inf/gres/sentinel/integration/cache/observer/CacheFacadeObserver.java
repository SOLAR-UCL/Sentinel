package br.ufpr.inf.gres.sentinel.integration.cache.observer;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;

/**
 *
 * @author Giovani Guizzo
 */
public interface CacheFacadeObserver {

    public default void notifyMutantExecutionInformationRetrieved(Mutant mutantToExecute) {
    }

    public default void notifyOperatorExecutionInformationRetrieved(Operator operator) {
    }

}
