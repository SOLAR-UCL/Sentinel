package br.ufpr.inf.gres.sentinel.base.mutation;

import java.util.Objects;

/**
 * @author Giovani Guizzo
 */
public class TestCase {

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
    }

    /**
     *
     * @param testCase
     */
    public TestCase(TestCase testCase) {
        this(testCase.name);
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
