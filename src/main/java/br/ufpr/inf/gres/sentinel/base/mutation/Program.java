package br.ufpr.inf.gres.sentinel.base.mutation;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.io.File;
import java.util.Objects;

/**
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getSimpleName() {
		return Iterables.getLast(Splitter.on('.').splitToList(name));
	}

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.name);
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

    @Override
    public String toString() {
        return name;
    }

}
