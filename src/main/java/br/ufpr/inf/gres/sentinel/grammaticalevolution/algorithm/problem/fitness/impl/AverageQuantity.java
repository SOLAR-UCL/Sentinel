package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import com.google.common.collect.Multimap;
import java.util.Collection;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 */
public class AverageQuantity<T> extends ObjectiveFunction<T> {

    @Override
    public String getName() {
        return AVERAGE_QUANTITY;
    }

    @Override
    public Double computeFitness(Solution<T> solution) {
        if (solution.getAttribute("Mutants") != null && solution.getAttribute("ConventionalMutants") != null) {
            Multimap<Program, Mutant> allConventionalMutants = (Multimap<Program, Mutant>) solution.getAttribute("ConventionalMutants");
            Multimap<Program, Collection<Mutant>> allMutants = (Multimap<Program, Collection<Mutant>>) solution.getAttribute("Mutants");
            if (!allMutants.isEmpty()) {
                for (Program program : allMutants.keySet()) {
                    Collection<Collection<Mutant>> mutants = allMutants.get(program);
                    if (!mutants.isEmpty() && mutants.stream().noneMatch(mutantList -> mutantList.isEmpty())) {
                        Double averageQuantity = mutants.stream()
                                .mapToInt(mutantsList -> mutantsList.size())
                                .average()
                                .getAsDouble();

                        return averageQuantity / allConventionalMutants.get(program).size();
                    }
                }
            }
        }
        return getWorstValue();
    }
}
