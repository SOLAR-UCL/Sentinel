package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.crossover.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public class SinglePointVariableCrossover<T> implements CrossoverOperator<VariableLengthSolution<T>> {

    private double crossoverProbability;
    private JMetalRandom randomGenerator;
    private int maxLength;

    /**
     * Constructor
     *
     * @param crossoverProbability
     */
    public SinglePointVariableCrossover(double crossoverProbability, int maxLength) {
        this.crossoverProbability = crossoverProbability;
        this.randomGenerator = JMetalRandom.getInstance();
        this.maxLength = maxLength;
    }

    /**
     * Perform the crossover operation.
     *
     * @param probability Crossover setProbability
     * @param parent1 The first parent
     * @param parent2 The second parent
     *
     * @return An array containing the two offspring
     */
    public List<VariableLengthSolution<T>> doCrossover(double probability,
            VariableLengthSolution<T> parent1,
            VariableLengthSolution<T> parent2) {
        VariableLengthSolution<T> offspring1 = parent1.copy();

        VariableLengthSolution<T> offspring2 = parent2.copy();

        if (this.randomGenerator.nextDouble() < probability) {
            offspring1.clearVariables();
            offspring2.clearVariables();
            // 1. Get the total number of bits
            int totalNumberOfGenesParent1 = parent1.getNumberOfVariables();
            int totalNumberOfGenesParent2 = parent2.getNumberOfVariables();

            // 2. Calculate the point to make the crossover
            int crossoverPointParent1 = this.randomGenerator.nextInt(0, totalNumberOfGenesParent1 - 1);
            int crossoverPointParent2 = this.randomGenerator.nextInt(0, totalNumberOfGenesParent2 - 1);

            for (int i = 0; i < crossoverPointParent1; i++) {
                offspring1.addVariable(parent1.getVariableValue(i));
            }
            for (int i = 0; i < crossoverPointParent2; i++) {
                offspring2.addVariable(parent2.getVariableValue(i));
            }
            for (int i = crossoverPointParent1; i < totalNumberOfGenesParent1; i++) {
                offspring2.addVariable(parent1.getVariableValue(i));
            }
            for (int i = crossoverPointParent2; i < totalNumberOfGenesParent2; i++) {
                offspring1.addVariable(parent2.getVariableValue(i));
            }

            if (offspring1.getNumberOfVariables() > maxLength) {
                List<T> newVariables = offspring1.getVariablesCopy().subList(0, maxLength);
                offspring1.clearVariables();
                offspring1.addAllVariables(newVariables);
            }

            if (offspring2.getNumberOfVariables() > maxLength) {
                List<T> newVariables = offspring2.getVariablesCopy().subList(0, maxLength);
                offspring2.clearVariables();
                offspring2.addAllVariables(newVariables);
            }
        }

        List<VariableLengthSolution<T>> offspring = new ArrayList<>(2);
        offspring.add(offspring1);
        offspring.add(offspring2);
        return offspring;
    }

    @Override
    public List<VariableLengthSolution<T>> execute(List<VariableLengthSolution<T>> solutions) {
        return this.doCrossover(this.crossoverProbability, solutions.get(0), solutions.get(1));
    }

    @Override
    public int getNumberOfParents() {
        return 2;
    }
}
