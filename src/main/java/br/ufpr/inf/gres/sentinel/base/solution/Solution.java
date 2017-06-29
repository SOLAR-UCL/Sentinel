package br.ufpr.inf.gres.sentinel.base.solution;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * @author Giovani Guizzo
 */
public class Solution {

    /**
     *
     */
    protected LinkedHashSet<Mutant> mutants;

    /**
     *
     */
    protected LinkedHashSet<Operator> operators;

    /**
     *
     */
    public Solution() {
        this.mutants = new LinkedHashSet();
        this.operators = new LinkedHashSet();
    }

    /**
     *
     * @param solution
     */
    public Solution(Solution solution) {
        this();

        LinkedHashMap<String, Operator> operatorHashMap = new LinkedHashMap<>();
        LinkedHashMap<String, Mutant> mutantHashMap = new LinkedHashMap<>();

        for (Mutant mutant : solution.mutants) {
            Mutant newMutant = new Mutant(mutant);
            mutantHashMap.put(newMutant.getName(), newMutant);
        }
        for (Operator operator : solution.operators) {
            Operator newOperator = new Operator(operator);
            operatorHashMap.put(newOperator.getName(), newOperator);

            ArrayList<Mutant> generatedMutants = new ArrayList<>(newOperator.getGeneratedMutants());
            newOperator.getGeneratedMutants().clear();
            for (Mutant generatedMutant : generatedMutants) {
                Mutant tempMutant = mutantHashMap.get(generatedMutant.getName());
                generatedMutant = tempMutant;
                generatedMutant.setOperator(newOperator);
                newOperator.getGeneratedMutants().add(generatedMutant);
            }
        }

        this.operators.addAll(operatorHashMap.values());
        this.mutants.addAll(mutantHashMap.values());
    }

    /**
     *
     * @return
     */
    public LinkedHashSet<Mutant> getMutants() {
        return this.mutants;
    }

    /**
     *
     * @param mutants
     */
    public void setMutants(LinkedHashSet<Mutant> mutants) {
        this.mutants = mutants;
    }

    /**
     *
     * @return
     */
    public LinkedHashSet<Operator> getOperators() {
        return this.operators;
    }

    /**
     *
     * @param operators
     */
    public void setOperators(LinkedHashSet<Operator> operators) {
        this.operators = operators;
    }
}
