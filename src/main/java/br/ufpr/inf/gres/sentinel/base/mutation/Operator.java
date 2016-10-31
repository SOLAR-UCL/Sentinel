package br.ufpr.inf.gres.sentinel.base.mutation;

import java.util.ArrayList;
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

    public void execute() {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
