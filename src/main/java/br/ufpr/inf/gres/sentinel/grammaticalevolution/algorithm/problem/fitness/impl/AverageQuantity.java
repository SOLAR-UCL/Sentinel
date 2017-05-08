package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import java.util.List;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 */
public class AverageQuantity implements ObjectiveFunction<Integer> {

    @Override
    public String getName() {
        return AVERAGE_QUANTITY;
    }

    @Override
    public Double computeFitness(Solution<Integer> solution) {
        List<List<Mutant>> mutants = (List<List<Mutant>>) solution.getAttribute("Mutants");
        Double averageQuantity = mutants.stream()
                .mapToInt(mutantsList -> mutantsList.size())
                .average()
                .orElse(getWorstValue());

        List<Mutant> conventionalMutants = (List<Mutant>) solution.getAttribute("ConventionalMutants");

        return averageQuantity / conventionalMutants.size();
    }

}
