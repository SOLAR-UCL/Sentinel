package br.ufpr.inf.gres.sentinel.base.solution;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import java.util.ArrayList;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public class Solution implements Cloneable {

    protected SetUniqueList<Mutant> mutants;
    protected SetUniqueList<Operator> operators;

    protected String mutantGrouping;
    protected String operatorsGrouping;

    public Solution() {
        mutants = SetUniqueList.setUniqueList(new ArrayList<>());
        operators = SetUniqueList.setUniqueList(new ArrayList<>());
    }

    public String getMutantGrouping() {
        return mutantGrouping;
    }

    public void setMutantGrouping(String mutantGrouping) {
        this.mutantGrouping = mutantGrouping;
    }

    public String getOperatorsGrouping() {
        return operatorsGrouping;
    }

    public void setOperatorsGrouping(String operatorsGrouping) {
        this.operatorsGrouping = operatorsGrouping;
    }

    public SetUniqueList<Mutant> getMutants() {
        return mutants;
    }

    public void setMutants(SetUniqueList<Mutant> mutants) {
        this.mutants = mutants;
    }

    public SetUniqueList<Operator> getOperators() {
        return operators;
    }

    public void setOperators(SetUniqueList<Operator> operators) {
        this.operators = operators;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Solution newSolution = new Solution();
        newSolution.getMutants().addAll(mutants);
        newSolution.getOperators().addAll(operators);
        newSolution.setMutantGrouping(mutantGrouping);
        newSolution.setOperatorsGrouping(operatorsGrouping);
        return newSolution;
    }

}
