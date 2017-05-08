package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import java.util.List;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 */
public class AverageScore implements ObjectiveFunction<Integer> {

    @Override
    public String getName() {
        return AVERAGE_SCORE;
    }

    @Override
    public Double computeFitness(Solution<Integer> solution) {
        List<Mutant> conventionalMutants = (List<Mutant>) solution.getAttribute("ConventionalMutants");

        List<List<Mutant>> mutants = (List<List<Mutant>>) solution.getAttribute("Mutants");

        Double averageScore = mutants.stream()
                .mapToLong(mutantsList -> {
                    return conventionalMutants.stream().filter(conventionalMutant -> conventionalMutant.getKillingTestCases().stream().anyMatch(testCase -> {
                        return mutantsList.stream().anyMatch(mutant -> mutant.getKillingTestCases().contains(testCase));
                    })).count();
                })
                .average()
                .orElse(getWorstValue());

        return averageScore;
    }

}
