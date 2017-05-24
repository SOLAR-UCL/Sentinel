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
public class AverageQuantity<T> extends ObjectiveFunction<T> {

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public Double computeFitness(Solution<T> solution) {
        if (solution.getAttribute("Mutants") != null && solution.getAttribute("ConventionalMutants") != null) {
            Multimap<Program, Mutant> allConventionalMutants = (Multimap<Program, Mutant>) solution.getAttribute("ConventionalMutants");
            Multimap<Program, Collection<Mutant>> allMutants = (Multimap<Program, Collection<Mutant>>) solution.getAttribute("Mutants");
            double sumQuantity = 0.0;
            Set<Program> allPrograms = allConventionalMutants.keySet();
            for (Program program : allPrograms) {
                Collection<Collection<Mutant>> mutants = allMutants.get(program);
                Double averageQuantity = mutants.stream()
                        .mapToInt(mutantsList -> mutantsList.size())
                        .average()
                        .orElse(0.0);

                int conventionalQuantity = allConventionalMutants.get(program).size();

                sumQuantity += averageQuantity / conventionalQuantity;
            }
            return sumQuantity / allPrograms.size();
        }
        return this.getWorstValue();
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return AVERAGE_QUANTITY;
    }

}
