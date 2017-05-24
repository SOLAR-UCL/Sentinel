package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import com.google.common.collect.Multimap;
import java.util.Collection;
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
                Collection<Collection<Mutant>> mutants = allMutants.get(program);
                Collection<Mutant> conventionalMutants = allConventionalMutants.get(program);
                Double averageScore = mutants.stream()
                        .mapToLong(mutantsList -> {
                            return conventionalMutants.stream().filter(conventionalMutant -> conventionalMutant.getKillingTestCases().stream().anyMatch(testCase -> {
                                return mutantsList.stream().anyMatch(mutant -> mutant.getKillingTestCases().contains(testCase));
                            })).count();
                        })
                        .average()
                        .orElse(0.0);

                long deadCount = conventionalMutants.stream().filter(Mutant::isDead).count();

                sumScore += averageScore / deadCount;
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
