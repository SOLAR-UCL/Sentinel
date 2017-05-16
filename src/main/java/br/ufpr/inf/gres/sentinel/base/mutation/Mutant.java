package br.ufpr.inf.gres.sentinel.base.mutation;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 * This class represents a mutant. A mutant is a {@link Program}, but mutated.
 * This class can also represent
 * <a href="http://dl.acm.org/citation.cfm?id=1570728">Higher Order Mutants
 * (HOM)</a> by having several mutants in its {@link #constituentMutants} list.
 * Please, refer to <a href="http://dl.acm.org/citation.cfm?id=1570728">Higher
 * Order Mutant</a> for more information about HOMs.
 *
 * @author Giovani Guizzo
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
     * The operators that generated this mutant. It is a list because it can be
     * a <a href="http://dl.acm.org/citation.cfm?id=1570728">Higher Order
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
     * Returns the constituent mutants of this mutant. The constituent mutants
     * are the mutants that are combined to generate a
     * <a href="http://dl.acm.org/citation.cfm?id=1570728">Higher Order
     * Mutant</a>.
     *
     * @return the constituent mutants of this mutant. Empty list if it is a
     * first order mutant.
     *
     * @see #isHigherOrder()
     * @see #getOrder()
     */
    public SetUniqueList<Mutant> getConstituentMutants() {
        return this.constituentMutants;
    }

    /**
     *
     * Sets the constituent mutants of this mutant.
     *
     * @param constituentMutants the constituent mutants of this mutant
     */
    public void setConstituentMutants(SetUniqueList<Mutant> constituentMutants) {
        this.constituentMutants = constituentMutants;
    }

    /**
     * Gets the test cases that kill this mutant.
     *
     * @return the test cases that kill this mutant
     * @see #isAlive()
     * @see #isDead()
     */
    public SetUniqueList<TestCase> getKillingTestCases() {
        return this.killingTestCases;
    }

    /**
     * Sets the test cases that kill this mutant.
     *
     * @param killingTestCases the test cases that kill this mutant
     */
    public void setKillingTestCases(SetUniqueList<TestCase> killingTestCases) {
        this.killingTestCases = killingTestCases;
    }

    /**
     * Gets the operators that generated this mutant. If this list contains more
     * than one operator, then this mutant is a
     * <a href="http://dl.acm.org/citation.cfm?id=1570728">Higher Order
     * Mutant</a>.
     *
     * @return the operators that generated this mutant
     */
    public SetUniqueList<Operator> getOperators() {
        return this.operators;
    }

    /**
     * Sets the operators that generated this mutant.
     *
     * @param operators the operators that generated this mutant
     */
    public void setOperators(SetUniqueList<Operator> operators) {
        this.operators = operators;
    }

    /**
     * Gets the order of this mutant. The order is computed by the size of the
     * {@link #constituentMutants} attribute. If the list is empty, then this is
     * a first order mutant. If the list has {@literal x > 1 } mutants, then
     * this is a
     * <a href="http://dl.acm.org/citation.cfm?id=1570728">Higher Order
     * Mutant</a> of order {@literal x}.
     *
     * @return the order of the mutant
     *
     * @see #isHigherOrder()
     */
    public int getOrder() {
        int order = this.getConstituentMutants().size();
        return order == 0 ? 1 : order;
    }

    /**
     * Gets the original program that was used to derive this mutant.
     *
     * @return the original program that was used to derive this mutant
     */
    public Program getOriginalProgram() {
        return this.originalProgram;
    }

    /**
     * Sets the original program that was used to derive this mutant
     *
     * @param originalProgram the original program that was used to derive this
     * mutant
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
     * Checks if this mutant is alive. This is computed by checking if the list
     * of {@link #killingTestCases} is empty.
     *
     * @return {@code true} if {@link #killingTestCases} is empty, {@code false}
     * otherwise
     *
     * @see #isDead()
     */
    public boolean isAlive() {
        return this.killingTestCases.isEmpty();
    }

    /**
     * Checks if this mutant is dead. This is computed by checking if the list
     * of {@link #killingTestCases} is not empty.
     *
     * @return {@code true} if {@link #killingTestCases} is not empty,
     * {@code false} otherwise
     *
     * @see #isAlive()
     */
    public boolean isDead() {
        return !this.isAlive();
    }

    /**
     * Checks if the mutant is equivalent. Note that it does not actually
     * compute the mutant equivalence, but only checks the state of
     * {@link #equivalent}.
     *
     * @return boolean representing the equivalence of this mutant according to
     * the state of {@link #equivalent}
     */
    public boolean isEquivalent() {
        return this.equivalent;
    }

    /**
     * Sets the mutant equivalence state of this mutant.
     *
     * @param equivalent the equivalence state of this mutant. {@code true} if
     * it is equivalent, {@code false} otherwise
     */
    public void setEquivalent(boolean equivalent) {
        this.equivalent = equivalent;
    }

    /**
     * Checks if this mutant is a HOM. This is computed by the size of the
     * {@link #constituentMutants} list.
     *
     * @return if the mutant is of higher order. {@code true} if
     * {@code order > 1}, {@code false} otherwise.
     *
     * @see #getOrder()
     */
    public boolean isHigherOrder() {
        return this.getOrder() > 1;
    }

    @Override
    public String toString() {
        return this.fullName;
    }
}
