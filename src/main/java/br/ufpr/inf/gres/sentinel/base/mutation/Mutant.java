package br.ufpr.inf.gres.sentinel.base.mutation;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 * @author Giovani Guizzo
 *
 * This class represents a mutant. A mutant is a {@link Program}, but mutated.
 */
public class Mutant extends Program {

    /**
     * The constituent mutants of this mutant. Constituent mutants are the
     * mutants used to create a
     * <a href="http://dl.acm.org/citation.cfm?id=1570728">Higher Order
     * Mutant</a>.
     */
    protected SetUniqueList<Mutant> constituentMutants;
    /**
     * If this mutant is equivalent.
     */
    protected boolean equivalent = false;
    /**
     * The test cases that kill this mutant.
     */
    protected SetUniqueList<TestCase> killingTestCases;
    /**
     * The operators that generated this mutant. It is a list because if it is a
     * <a href="http://dl.acm.org/citation.cfm?id=1570728">Higher Order
     * Mutant</a>.
     */
    protected SetUniqueList<Operator> operators;
    /**
     * The original program from which this mutant was derived.
     */
    protected Program originalProgram;

    /**
     * The standard constructor for a mutant.
     *
     * @param name the name of this mutant
     * @param sourceFile the source file of this mutant (if any)
     * @param originalProgram the original program that was used to derive this
     * mutant
     */
    public Mutant(String name, File sourceFile, Program originalProgram) {
        super(name, sourceFile);
        this.originalProgram = originalProgram;
        this.constituentMutants = SetUniqueList.setUniqueList(new ArrayList<>());
        this.killingTestCases = SetUniqueList.setUniqueList(new ArrayList<>());
        this.operators = SetUniqueList.setUniqueList(new ArrayList<>());
    }

    /**
     * A copying constructor. Note that it does not perform a deep clone.
     *
     * @param mutant the mutant to be copied
     */
    public Mutant(Mutant mutant) {
        this(mutant.fullName, mutant.sourceFile, mutant.originalProgram);
        this.equivalent = mutant.equivalent;
        this.constituentMutants.addAll(mutant.constituentMutants);
        this.killingTestCases.addAll(mutant.killingTestCases);
        this.operators.addAll(mutant.operators);
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
        final Mutant other = (Mutant) obj;
        return Objects.equals(this.originalProgram, other.originalProgram) && Objects.equals(this.fullName, other.fullName);
    }

    /**
     *
     * @return
     */
    public SetUniqueList<Mutant> getConstituentMutants() {
        return this.constituentMutants;
    }

    /**
     *
     * @param constituentMutants
     */
    public void setConstituentMutants(SetUniqueList<Mutant> constituentMutants) {
        this.constituentMutants = constituentMutants;
    }

    /**
     *
     * @return
     */
    public SetUniqueList<TestCase> getKillingTestCases() {
        return this.killingTestCases;
    }

    /**
     *
     * @param killingTestCases
     */
    public void setKillingTestCases(SetUniqueList<TestCase> killingTestCases) {
        this.killingTestCases = killingTestCases;
    }

    /**
     *
     * @return
     */
    public SetUniqueList<Operator> getOperators() {
        return this.operators;
    }

    /**
     *
     * @param operators
     */
    public void setOperators(SetUniqueList<Operator> operators) {
        this.operators = operators;
    }

    /**
     *
     * @return
     */
    public int getOrder() {
        int order = this.getConstituentMutants().size();
        return order == 0 ? 1 : order;
    }

    /**
     * Gets the original program that was used to derive this mutant.
     *
     * @return the original program of this mutant
     *
     * @see #originalProgram
     */
    public Program getOriginalProgram() {
        return this.originalProgram;
    }

    /**
     *
     * @param originalProgram
     */
    public void setOriginalProgram(Program originalProgram) {
        this.originalProgram = originalProgram;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.originalProgram);
        hash = 11 * hash + Objects.hashCode(this.fullName);
        return hash;
    }

    /**
     *
     * @return
     */
    public boolean isAlive() {
        return this.killingTestCases.isEmpty();
    }

    /**
     *
     * @return
     */
    public boolean isDead() {
        return !this.isAlive();
    }

    /**
     *
     * @return
     */
    public boolean isEquivalent() {
        return this.equivalent;
    }

    /**
     *
     * @param equivalent
     */
    public void setEquivalent(boolean equivalent) {
        this.equivalent = equivalent;
    }

    /**
     *
     * @return
     */
    public boolean isHigherOrder() {
        return this.getOrder() > 1;
    }

    @Override
    public String toString() {
        return this.fullName;
    }
}
