package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.Iterator;

/**
 * Created by Giovani Guizzo on 24/10/2016.
 */
public interface Factory<T> {

    Operation createOperation(T node, Iterator<Integer> cyclicIterator);

}
