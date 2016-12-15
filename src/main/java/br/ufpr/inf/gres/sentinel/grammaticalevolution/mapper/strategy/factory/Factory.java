package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;

import java.util.Iterator;

public interface Factory<T> {

	Operation createOperation(T node, Iterator<Integer> cyclicIterator);

}
