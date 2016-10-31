package br.ufpr.inf.gres.sentinel.base.mutation;

import java.io.File;
import java.util.Objects;

/**
 *
 * @author Giovani Guizzo
 */
public class Program {

    protected String name;
    protected File sourceFile;

    public Program(String name, File sourceFile) {
        this.name = name;
        this.sourceFile = sourceFile;
    }

    public Program(Program program) {
        this(program.name, program.sourceFile);
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

}
