package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.AbstractVariableLengthProblem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.AbstractGenericSolution;

/**
 * @author Giovani Guizzo
 */
public abstract class VariableLengthSolution<T> extends AbstractGenericSolution<T, Problem<? extends Solution<T>>> {

    protected List<T> variables;

    /**
     * Constructor
     *
     * @param problem
     */
    public VariableLengthSolution(AbstractVariableLengthProblem<T> problem) {
        super(problem);
        this.variables = new ArrayList<>();
        initializeObjectiveValues();
    }

    public VariableLengthSolution(VariableLengthSolution<T> solution) {
        super(solution.problem);
        this.variables = new ArrayList<>();

        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            addVariable(solution.getVariableValue(i));
        }

        for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
            setObjective(i, solution.getObjective(i));
        }

        attributes = new HashMap<>(solution.attributes);
    }

    @Override
    public String getVariableValueString(int index) {
        return variables.get(index).toString();
    }

    @Override
    public T getVariableValue(int index) {
        return variables.get(index);
    }

    @Override
    public void setVariableValue(int index, T value) {
        variables.set(index, value);
    }

    public void addVariable(T value) {
        variables.add(value);
    }

    public void addAllVariables(List<T> values) {
        variables.addAll(values);
    }

    public void clearVariables() {
        variables.clear();
    }

    public List<T> getVariablesCopy() {
        return new ArrayList<>(variables);
    }

    @Override
    public int getNumberOfVariables() {
        return variables.size();
    }

    public abstract VariableLengthSolution<T> copy();

}
