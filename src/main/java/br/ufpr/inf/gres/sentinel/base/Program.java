package br.ufpr.inf.gres.sentinel.base;

import java.io.File;
import java.util.Objects;

/**
 *
 * @author Giovani Guizzo
 */
public class Program {

    protected String name;
    protected File sourceFile;

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Program other = (Program) obj;
        return Objects.equals(this.name, other.name);
    }

}
