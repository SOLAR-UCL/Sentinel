package br.ufpr.inf.gres.sentinel.base.mutation;

import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 * @author Giovani Guizzo
 */
public class TestCase {

    /**
     *
     */
    protected SetUniqueList<Mutant> killingMutants;
    /**
     *
     */
    protected String name;

    /**
     *
     * @param name
     */
    public TestCase(String name) {
        this.name = name;
        this.killingMutants = SetUniqueList.setUniqueList(new ArrayList<>());
    }

    /**
     *
     * @param testCase
     */
    public TestCase(TestCase testCase) {
        this(testCase.name);
        this.killingMutants.addAll(testCase.getKillingMutants());
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
        final TestCase other = (TestCase) obj;
        return Objects.equals(this.name, other.name);
    }

    /**
     *
     * @return
     */
    public SetUniqueList<Mutant> getKillingMutants() {
        return this.killingMutants;
    }

    /**
     *
     * @param killingMutants
     */
    public void setKillingMutants(SetUniqueList<Mutant> killingMutants) {
        this.killingMutants = killingMutants;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
