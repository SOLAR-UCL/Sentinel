package br.ufpr.inf.gres.sentinel.base.mutation;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.io.File;
import java.util.Objects;

/**
 * @author Giovani Guizzo
 */
public class Program {

    /**
     *
     */
    protected String fullName;

    /**
     *
     */
    protected File sourceFile;

    /**
     *
     * @param fullName
     * @param sourceFile
     */
    public Program(String fullName, File sourceFile) {
        this.fullName = fullName;
        this.sourceFile = sourceFile;
    }

    /**
     *
     * @param program
     */
    public Program(Program program) {
        this(program.fullName, program.sourceFile);
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
        final Program other = (Program) obj;
        return Objects.equals(this.fullName, other.fullName);
    }

    /**
     *
     * @return
     */
    public String getFullName() {
        return this.fullName;
    }

    /**
     *
     * @param fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     */
    public String getSimpleName() {
        return Iterables.getLast(Splitter.on('.').splitToList(this.fullName));
    }

    /**
     *
     * @return
     */
    public File getSourceFile() {
        return this.sourceFile;
    }

    /**
     *
     * @param sourceFile
     */
    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.fullName);
        return hash;
    }

    @Override
    public String toString() {
        return this.fullName;
    }

}
