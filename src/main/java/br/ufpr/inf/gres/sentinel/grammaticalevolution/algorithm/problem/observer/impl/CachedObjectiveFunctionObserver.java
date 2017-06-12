package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.observer.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.integration.cache.observer.CacheFacadeObserver;

/**
 *
 * @author Giovani Guizzo
 */
public class CachedObjectiveFunctionObserver extends ConstrainedObjectiveFunctionObserver implements CacheFacadeObserver {

    protected long currentTime;

    @Override
    public void notifyRunStart(int runNumber) {
        this.currentThreadCpuTime = 0;
        this.currentTime = 0;
    }

    @Override
    public void notifyOperatorExecutionInformationRetrieved(Operator operator) {
        this.currentThreadCpuTime += operator.getCpuTime();
        this.currentTime += operator.getExecutionTime();
    }

    @Override
    public void notifyMutantExecutionInformationRetrieved(Mutant mutantToExecute) {
        this.currentThreadCpuTime += mutantToExecute.getCpuTime();
        this.currentTime += mutantToExecute.getExecutionTime();
    }

    @Override
    public void notifyRunEnd() {
        this.nanoTimes.put(program, this.currentTime);
        this.nanoCPUTimes.put(program, this.currentThreadCpuTime);
        this.allMutants.put(program, this.mutants);
    }

}
