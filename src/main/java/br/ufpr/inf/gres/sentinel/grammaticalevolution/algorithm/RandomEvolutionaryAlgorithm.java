/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.VariableLengthProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

/**
 *
 * @author Giovani
 */
public class RandomEvolutionaryAlgorithm<T> extends NSGAII<VariableLengthSolution<T>> {

    public RandomEvolutionaryAlgorithm(VariableLengthProblem<T> problem,
            int maxEvaluations,
            int populationSize,
            SolutionListEvaluator<VariableLengthSolution<T>> evaluator) {
        super(problem, maxEvaluations, populationSize, null, null, null, evaluator);
    }

    @Override
    protected List<VariableLengthSolution<T>> selection(List<VariableLengthSolution<T>> population) {
        return new ArrayList<>();
    }

    @Override
    protected List<VariableLengthSolution<T>> reproduction(List<VariableLengthSolution<T>> population) {
        return this.createInitialPopulation();
    }

    @Override
    public String getName() {
        return "Random Search Algorithm";
    }

    @Override
    public String getDescription() {
        return "Random Search Algorithm with the NSGA-II mechanism of pruning the population";
    }

}
