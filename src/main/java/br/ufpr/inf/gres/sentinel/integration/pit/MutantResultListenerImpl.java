package br.ufpr.inf.gres.sentinel.integration.pit;

import java.util.Collection;
import org.pitest.mutationtest.ClassMutationResults;
import org.pitest.mutationtest.MutationResult;
import org.pitest.mutationtest.MutationResultListener;

/**
 *
 * @author Giovani Guizzo
 */
public class MutantResultListenerImpl implements MutationResultListener {

    private ClassMutationResults results;

    /**
     *
     * @return
     */
    public Collection<MutationResult> getResults() {
        return this.results.getMutations();
    }

    /**
     *
     * @param results
     */
    @Override
    public void handleMutationResult(ClassMutationResults results) {
        this.results = results;
    }

    /**
     *
     */
    @Override
    public void runEnd() {
    }

    /**
     *
     */
    @Override
    public void runStart() {
    }

}
