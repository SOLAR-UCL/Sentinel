package br.ufpr.inf.gres.sentinel.base.solution;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import java.util.ArrayList;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 * @author Giovani Guizzo
 */
public class Solution {

    /**
     *
     */
    protected SetUniqueList<Mutant> mutants;

    /**
     *
     */
    protected SetUniqueList<Operator> operators;

    /**
     *
     */
    public Solution() {
        this.mutants = SetUniqueList.setUniqueList(new ArrayList<>());
        this.operators = SetUniqueList.setUniqueList(new ArrayList<>());
    }

    /**
     *
     * @param solution
     */
    public Solution(Solution solution) {
        this();
        for (Mutant mutant : solution.mutants) {
            Mutant newMutant = new Mutant(mutant);
            this.mutants.add(newMutant);
        }
        for (Operator operator : solution.operators) {
            Operator newOperator = new Operator(operator);
            this.operators.add(newOperator);

            ArrayList<Mutant> generatedMutants = new ArrayList<>(newOperator.getGeneratedMutants());
            newOperator.getGeneratedMutants().clear();
            for (Mutant generatedMutant : generatedMutants) {
                for (Mutant tempMutant : this.mutants) {
                    if (tempMutant.equals(generatedMutant)) {
                        generatedMutant = tempMutant;
                        break;
                    }
                }
                generatedMutant.getOperators().remove(newOperator);
                generatedMutant.getOperators().add(newOperator);
                newOperator.getGeneratedMutants().add(generatedMutant);
            }
        }
    }

    /**
     *
     * @return
     */
    public SetUniqueList<Mutant> getMutants() {
        return this.mutants;
    }

    /**
     *
     * @param mutants
     */
    public void setMutants(SetUniqueList<Mutant> mutants) {
        this.mutants = mutants;
    }

    /**
     *
     * @return
     */
    public SetUniqueList<Operator> getOperators() {
        return this.operators;
    }

    /**
     *
     * @param operators
     */
    public void setOperators(SetUniqueList<Operator> operators) {
        this.operators = operators;
    }
}
