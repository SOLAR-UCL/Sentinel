package br.ufpr.inf.gres.sentinel.base.mutation;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 * This class represents a mutant. A mutant is a {@link Program}, but mutated.
 *
 * @author Giovani Guizzo
 */
public class Mutant extends Program {

    /**
     * If this mutant is equivalent.
     */
    protected boolean equivalent = false;
    /**
     * The test cases that kill this mutant.
     */
    protected SetUniqueList<TestCase> killingTestCases;
    /**
     * The operator that generated this mutant.
     */
    protected transient Operator operator;
    /**
     * The original program from which this mutant was derived.
     */
    protected Program originalProgram;

    /**
     * The clock time this particular mutant took to be executed. Default value
     * is 0. If the value 0 is here, it means either that the mutant was not yet
     * executed, or that the Integration Facade was not able to store the
     * individual time for this mutant.
     *
     * @see #cpuTime
     */
    protected double executionTime = 0;

    /**
     * The CPU time this particular mutant took to be executed. Default value is
     * 0. If the value 0 is here, it means either that the mutant was not yet
     * executed, or that the Integration Facade was not able to store the
     * individual time for this mutant.
     *
     * @see #executionTime
     */
    protected double cpuTime = 0;

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
        this.killingTestCases = SetUniqueList.setUniqueList(new ArrayList<>());
    }

    /**
     * A copying constructor. Note that it does not perform a deep clone.
     *
     * @param mutant the mutant to be copied
     */
    public Mutant(Mutant mutant) {
        this(mutant.name, mutant.sourceFile, mutant.originalProgram);
        this.equivalent = mutant.equivalent;
        this.killingTestCases.addAll(mutant.killingTestCases);
        this.operator = mutant.operator;
        this.executionTime = mutant.executionTime;
        this.cpuTime = mutant.cpuTime;
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
        return Objects.equals(this.originalProgram, other.originalProgram) && Objects.equals(this.name, other.name);
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
     * Gets the operator that generated this mutant.
     *
     * @return the operator that generated this mutant
     */
    public Operator getOperator() {
        return this.operator;
    }

    /**
     * Sets the operator that generated this mutant.
     *
     * @param operator the operator that generated this mutant
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
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
        hash = 11 * hash + Objects.hashCode(this.name);
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
     * Gets the clock execution time this particular mutant took to be executed.
     *
     * @return the clock execution time of this particular mutant. Default value
     * is 0. If the value 0 is here, it means either that the mutant was not yet
     * executed, or that the Integration Facade was not able to store the
     * individual time for this mutant.
     *
     * @see #getCpuTime()
     */
    public double getExecutionTime() {
        return executionTime;
    }

    /**
     * Sets the clock execution time of this particular mutant
     *
     * @param executionTime the clock execution time of this particular mutant.
     *
     * @see #setCpuTime(double)
     */
    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * Gets the CPU execution time this particular mutant took to be executed.
     *
     * @return the CPU execution time of this particular mutant. Default value
     * is 0. If the value 0 is here, it means either that the mutant was not yet
     * executed, or that the Integration Facade was not able to store the
     * individual time for this mutant.
     *
     * @see #getExecutionTime()
     */
    public double getCpuTime() {
        return cpuTime;
    }

    /**
     * Sets the CPU time of this particular mutant
     *
     * @param cpuTime the CPU execution time of this particular mutant.
     *
     * @see #setExecutionTime(double)
     */
    public void setCpuTime(double cpuTime) {
        this.cpuTime = cpuTime;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
