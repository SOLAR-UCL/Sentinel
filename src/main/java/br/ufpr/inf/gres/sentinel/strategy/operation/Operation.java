package br.ufpr.inf.gres.sentinel.strategy.operation;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.NewBranchOperation;
import java.util.Objects;

/**
 * @author Giovani Guizzo
 */
@SuppressWarnings("ALL")
public abstract class Operation<I, O> {

    protected String name;
    protected Operation<I, O> successor;

    public Operation(String name) {
        this.name = name;
    }

    public abstract O doOperation(I input);

    public abstract boolean isSpecific();

    public O next(I input) {
        if (successor != null) {
            return successor.doOperation(input);
        }
        return null;
    }

    public Operation<I, O> getSuccessor() {
        return successor;
    }

    public void setSuccessor(Operation<I, O> successor) {
        this.successor = successor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String toStringComplete() {
        return getOperationChainName(this, "", 1);
    }

    private String getOperationChainName(Operation operation, String tabbing, int index) {
        if (operation.getSuccessor() == null) {
            return tabbing + index + "." + operation.getName();
        } else if (operation instanceof NewBranchOperation) {
            NewBranchOperation newBranch = (NewBranchOperation) operation;
            return tabbing + index + "." + operation.getName() + " - " + getOperationChainName(operation.getSuccessor(), tabbing, index + 1) + "\n\t" + getOperationChainName(newBranch
                    .getSecondSuccessor(), tabbing + index + ".", 1);
        } else {
            return tabbing + index + "." + operation.getName() + " - " + getOperationChainName(operation.getSuccessor(), tabbing, index + 1);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
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
        final Operation<I, O> other = (Operation<I, O>) obj;
        return Objects.equals(this.name, other.name);
    }

}
