package br.ufpr.inf.gres.sentinel.strategy.operation;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 * @author Giovani Guizzo
 */
public abstract class Operation {

    protected Operation successor;
    protected String name;
    protected boolean specific = false;

    public Operation(String name, boolean specific) {
        this.name = name;
        this.specific = specific;
    }

    public abstract SetUniqueList<Mutant> doOperation(Solution solution);

    public SetUniqueList<Mutant> next(Solution solution) {
        if (successor != null) {
            return successor.doOperation(solution);
        }
        return null;
    }

    public Operation getSuccessor() {
        return successor;
    }

    public void setSuccessor(Operation successor) {
        this.successor = successor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSpecific() {
        return specific;
    }

    public void setSpecific(boolean specific) {
        this.specific = specific;
    }

    @Override
    public String toString() {
        return name;
    }

}
