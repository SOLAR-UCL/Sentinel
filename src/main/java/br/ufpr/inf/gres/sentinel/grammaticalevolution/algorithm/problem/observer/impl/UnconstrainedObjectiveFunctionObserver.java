package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.impl;

/**
 *
 * @author Giovani Guizzo
 */
public class UnconstrainedObjectiveFunctionObserver extends ConstrainedObjectiveFunctionObserver {

    @Override
    public boolean shouldStopEvaluation() {
        return false;
    }

}
