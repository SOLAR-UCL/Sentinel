package br.ufpr.inf.gres.sentinel.base.mutation;

import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public class Operator {

    protected String name;
    protected String type;
    protected SetUniqueList<Mutant> generatedMutants;

    public Operator(String name, String type) {
        this.name = name;
        this.type = type;
        this.generatedMutants = SetUniqueList.setUniqueList(new ArrayList<>());
    }

    public Operator(Operator operator) {
        this(operator.name, operator.type);
        this.generatedMutants.addAll(operator.getGeneratedMutants());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SetUniqueList<Mutant> getGeneratedMutants() {
        return generatedMutants;
    }

    public void setGeneratedMutants(SetUniqueList<Mutant> generatedMutants) {
        this.generatedMutants = generatedMutants;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Operator other = (Operator) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
