package br.ufpr.inf.gres.sentinel.strategy.operation;

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

}
