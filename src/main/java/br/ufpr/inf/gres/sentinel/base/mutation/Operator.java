package br.ufpr.inf.gres.sentinel.base.mutation;

import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 * @author Giovani Guizzo
 */
public class Operator {

    /**
     *
     */
    protected SetUniqueList<Mutant> generatedMutants;

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected String type;

    protected double executionTime = 0;

    protected double cpuTime = 0;

    /**
     *
     * @param name
     * @param type
     */
    public Operator(String name, String type) {
        this.name = name;
        this.type = type;
        this.generatedMutants = SetUniqueList.setUniqueList(new ArrayList<>());
    }

    /**
     *
     * @param operator
     */
    public Operator(Operator operator) {
        this(operator.name, operator.type);
        this.generatedMutants.addAll(operator.getGeneratedMutants());
        this.executionTime = operator.executionTime;
        this.cpuTime = operator.cpuTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Operator other = (Operator) obj;
        return Objects.equals(this.name, other.name);
    }

    /**
     *
     * @return
     */
    public SetUniqueList<Mutant> getGeneratedMutants() {
        return this.generatedMutants;
    }

    /**
     *
     * @param generatedMutants
     */
    public void setGeneratedMutants(SetUniqueList<Mutant> generatedMutants) {
        this.generatedMutants = generatedMutants;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    public double getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(double cpuTime) {
        this.cpuTime = cpuTime;
    }

}
