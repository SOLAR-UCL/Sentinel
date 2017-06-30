package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 * @param <T>
 */
public class AverageScore<T> extends ObjectiveFunction<T> {

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public Double computeFitness(Solution<T> solution) {
        if (solution.getAttribute("ConventionalMutants") != null && solution.getAttribute("Mutants") != null) {
            Multimap<Program, Mutant> allConventionalMutants = (Multimap<Program, Mutant>) solution.getAttribute("ConventionalMutants");
            Multimap<Program, Collection<Mutant>> allMutants = (Multimap<Program, Collection<Mutant>>) solution.getAttribute("Mutants");

            double sumScore = 0.0;
            Set<Program> allPrograms = allConventionalMutants.keySet();
            for (Program program : allPrograms) {
                Collection<Mutant> conventionalMutants = allConventionalMutants.get(program);

                HashMap<TestCase, HashSet<Mutant>> testCaseMap = new HashMap<>();
                for (Mutant conventionalMutant : conventionalMutants) {
                    for (TestCase killingTestCase : conventionalMutant.getKillingTestCases()) {
                        HashSet<Mutant> mutantsKilled = testCaseMap.computeIfAbsent(killingTestCase, temp -> new HashSet<>());
                        mutantsKilled.add(conventionalMutant);
                    }
                }

                Double averageNumberOfKilledMutants = 0D;
                for (Collection<Mutant> evaluatingMutants : allMutants.get(program)) {
                    HashSet<Mutant> killedConventionalMutants = new HashSet();
                    for (Mutant evaluatingMutant : evaluatingMutants) {
                        for (TestCase evaluatingTestCase : evaluatingMutant.getKillingTestCases()) {
                            killedConventionalMutants.addAll(testCaseMap.get(evaluatingTestCase));
                        }
                    }
                    averageNumberOfKilledMutants += killedConventionalMutants.size();
                }
                averageNumberOfKilledMutants /= allMutants.get(program).size();

                long deadCount = conventionalMutants.stream().filter(Mutant::isDead).count();

                sumScore += averageNumberOfKilledMutants / deadCount;
            }
            return -1 * sumScore / allPrograms.size();
        }
        return this.getWorstValue();
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return AVERAGE_SCORE;
    }

}
