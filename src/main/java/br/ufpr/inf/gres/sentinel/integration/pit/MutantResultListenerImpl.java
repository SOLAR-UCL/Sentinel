package br.ufpr.inf.gres.sentinel.integration.pit;

import edu.emory.mathcs.backport.java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.pitest.mutationtest.ClassMutationResults;
import org.pitest.mutationtest.MutationResult;
import org.pitest.mutationtest.MutationResultListener;

/**
 *
 * @author Giovani Guizzo
 */
public class MutantResultListenerImpl implements MutationResultListener {

    private List<ClassMutationResults> results = new ArrayList<>();

    /**
     *
     * @return
     */
    public Collection<MutationResult> getResults() {
        if (results != null) {
            ArrayList<MutationResult> result = new ArrayList<>();
            this.results.stream().forEach(mutationResult -> result.addAll(mutationResult.getMutations()));
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     *
     * @param results
     */
    @Override
    public void handleMutationResult(ClassMutationResults results) {
        this.results.add(results);
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
