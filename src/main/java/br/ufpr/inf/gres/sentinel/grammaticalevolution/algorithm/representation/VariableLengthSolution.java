package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.VariableLengthProblem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.AbstractGenericSolution;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public abstract class VariableLengthSolution<T> extends AbstractGenericSolution<T, Problem<? extends Solution<T>>> {

    /**
     *
     */
    protected List<T> variables;

    /**
     * Constructor
     *
     * @param problem
     */
    public VariableLengthSolution(VariableLengthProblem<T> problem) {
        super(problem);
        this.variables = new ArrayList<>();
        this.initializeObjectiveValues();
    }

    /**
     *
     * @param solution
     */
    public VariableLengthSolution(VariableLengthSolution<T> solution) {
        super(solution.problem);
        this.variables = new ArrayList<>();

        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            this.addVariable(solution.getVariableValue(i));
        }

        for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
            this.setObjective(i, solution.getObjective(i));
        }

        this.attributes = new HashMap<>(solution.attributes);
    }

    /**
     *
     * @param values
     */
    public void addAllVariables(List<T> values) {
        this.variables.addAll(values);
    }

    /**
     *
     * @param value
     */
    public void addVariable(T value) {
        this.variables.add(value);
    }

    /**
     *
     */
    public void clearVariables() {
        this.variables.clear();
    }

    /**
     *
     * @return
     */
    @Override
    public abstract VariableLengthSolution<T> copy();

    /**
     *
     * @return
     */
    @Override
    public int getNumberOfVariables() {
        return this.variables.size();
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public T getVariableValue(int index) {
        return this.variables.get(index);
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public String getVariableValueString(int index) {
        return this.variables.get(index).toString();
    }

    /**
     *
     * @return
     */
    public List<T> getVariablesCopy() {
        return new ArrayList<>(this.variables);
    }

    /**
     *
     * @param index
     * @param value
     */
    @Override
    public void setVariableValue(int index, T value) {
        this.variables.set(index, value);
    }

}
