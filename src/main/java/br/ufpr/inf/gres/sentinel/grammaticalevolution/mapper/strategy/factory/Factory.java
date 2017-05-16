package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 * @param <T>
 */
public interface Factory<T> {

    /**
     *
     * @param node
     * @param integerIterator
     * @return
     */
    Operation createOperation(T node, Iterator<Integer> integerIterator);

}
