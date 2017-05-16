package br.ufpr.inf.gres.sentinel.util.subsuming;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Giovani Guizzo
 */
public class MutantsFilter {

    /**
     *
     * @param mutants
     * @return
     */
    public List<Mutant> getMinimalMutantSet(List<Mutant> mutants) {
        List<Mutant> reducedMutants = mutants.stream().filter(Mutant::isDead).collect(Collectors.toList());
        outerFor:
        for (int i = 0; i < reducedMutants.size() - 1; i++) {
            Mutant mutant1 = reducedMutants.get(i);
            for (int j = i + 1; j < reducedMutants.size(); j++) {
                Mutant mutant2 = reducedMutants.get(j);
                if (mutant2.getKillingTestCases().containsAll(mutant1.getKillingTestCases())) {
                    reducedMutants.remove(j);
                    j--;
                } else if (mutant1.getKillingTestCases().containsAll(mutant2.getKillingTestCases())) {
                    reducedMutants.remove(i);
                    i--;
                    continue outerFor;
                }
            }
        }
        return reducedMutants;
    }

}
