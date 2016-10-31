package br.ufpr.inf.gres.sentinel.base.mutation;

import java.io.File;
import java.util.ArrayList;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public class Mutant extends Program {

    protected Program originalProgram;
    protected SetUniqueList<Operator> operators;
    protected boolean equivalent = false;
    protected SetUniqueList<Mutant> constituentMutants;
    protected SetUniqueList<TestCase> killingTestCases;

    public Mutant(String name, File sourceFile, Program originalProgram) {
        super(name, sourceFile);
        this.originalProgram = originalProgram;
        this.constituentMutants = SetUniqueList.setUniqueList(new ArrayList<>());
        this.killingTestCases = SetUniqueList.setUniqueList(new ArrayList<>());
        this.operators = SetUniqueList.setUniqueList(new ArrayList<>());
    }

    public Mutant(Mutant mutant) {
        this(mutant.name, mutant.sourceFile, mutant.originalProgram);
        this.equivalent = mutant.equivalent;
        this.constituentMutants.addAll(mutant.constituentMutants);
        this.killingTestCases.addAll(mutant.killingTestCases);
        this.operators.addAll(mutant.operators);
    }

    public Program getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(Program originalProgram) {
        this.originalProgram = originalProgram;
    }

    public SetUniqueList<Operator> getOperators() {
        return operators;
    }

    public void setOperators(SetUniqueList<Operator> operators) {
        this.operators = operators;
    }

    public boolean isEquivalent() {
        return equivalent;
    }

    public void setEquivalent(boolean equivalent) {
        this.equivalent = equivalent;
    }

    public SetUniqueList<Mutant> getConstituentMutants() {
        return constituentMutants;
    }

    public void setConstituentMutants(SetUniqueList<Mutant> constituentMutants) {
        this.constituentMutants = constituentMutants;
    }

    public SetUniqueList<TestCase> getKillingTestCases() {
        return killingTestCases;
    }

    public void setKillingTestCases(SetUniqueList<TestCase> killingTestCases) {
        this.killingTestCases = killingTestCases;
    }

}
