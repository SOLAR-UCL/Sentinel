package br.ufpr.inf.gres.sentinel.strategy.operation;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.NewBranchOperation;

/**
 * @author Giovani Guizzo
 */
public abstract class Operation<I, O> {

    protected Operation<I, O> successor;
    protected String name;
    protected boolean specific = false;

    public Operation(String name, boolean specific) {
        this.name = name;
        this.specific = specific;
    }

    public abstract O doOperation(I input);

    public O next(I input) {
        if (successor != null) {
            return successor.doOperation(input);
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

    public String toStringComplete() {
        return getOperationChainName(this, "", 1);
    }

    private String getOperationChainName(Operation operation, String tabbing, int index) {
        if (operation == null) {
            return "Empty Operation";
        } else if (operation.getSuccessor() == null) {
            return tabbing + index + "." + operation.getName();
        } else if (operation instanceof NewBranchOperation) {
            NewBranchOperation newBranch = (NewBranchOperation) operation;
            return tabbing + index + "." + operation.getName() + " - " + getOperationChainName(operation.getSuccessor(), tabbing, index + 1) + "\n\t" + getOperationChainName(newBranch.getSecondSuccessor(), tabbing + index + ".", 1);
        } else {
            return tabbing + index + "." + operation.getName() + " - " + getOperationChainName(operation.getSuccessor(), tabbing, index + 1);
        }
    }

}
