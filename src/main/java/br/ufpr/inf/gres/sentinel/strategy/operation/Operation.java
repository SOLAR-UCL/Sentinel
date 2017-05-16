package br.ufpr.inf.gres.sentinel.strategy.operation;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.NewBranchOperation;
import java.util.Objects;

/**
 * @author Giovani Guizzo
 * @param <I>
 * @param <O>
 */
@SuppressWarnings("ALL")
public abstract class Operation<I, O> {

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected Operation<I, O> successor;

    /**
     *
     * @param name
     */
    public Operation(String name) {
        this.name = name;
    }

    /**
     *
     * @param input
     * @return
     */
    public abstract O doOperation(I input);

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
        final Operation<I, O> other = (Operation<I, O>) obj;
        return Objects.equals(this.name, other.name);
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

    private String getOperationChainName(Operation operation, String tabbing, int index) {
        if (operation.getSuccessor() == null) {
            return tabbing + index + "." + operation.getName();
        } else if (operation instanceof NewBranchOperation) {
            NewBranchOperation newBranch = (NewBranchOperation) operation;
            return tabbing + index + "." + operation.getName() + " - " + this.getOperationChainName(operation.getSuccessor(), tabbing, index + 1) + "\n\t" + this.getOperationChainName(newBranch
                    .getSecondSuccessor(), tabbing + index + ".", 1);
        } else {
            return tabbing + index + "." + operation.getName() + " - " + this.getOperationChainName(operation.getSuccessor(), tabbing, index + 1);
        }
    }

    /**
     *
     * @return
     */
    public Operation<I, O> getSuccessor() {
        return this.successor;
    }

    /**
     *
     * @param successor
     */
    public void setSuccessor(Operation<I, O> successor) {
        this.successor = successor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }

    /**
     *
     * @return
     */
    public abstract boolean isSpecific();

    /**
     *
     * @param input
     * @return
     */
    public O next(I input) {
        if (this.successor != null) {
            return this.successor.doOperation(input);
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     *
     * @return
     */
    public String toStringComplete() {
        return this.getOperationChainName(this, "", 1);
    }

}
