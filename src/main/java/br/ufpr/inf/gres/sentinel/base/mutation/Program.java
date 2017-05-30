package br.ufpr.inf.gres.sentinel.base.mutation;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Giovani Guizzo
 */
public class Program {

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected File sourceFile;

    protected HashMap<String, Object> attributes;

    /**
     *
     * @param name
     * @param sourceFile
     */
    public Program(String name, File sourceFile) {
        this.name = name;
        this.sourceFile = sourceFile;
        attributes = new HashMap<>();
    }

    /**
     *
     * @param name
     * @param sourceFilePath
     */
    public Program(String name, String sourceFilePath) {
        this(name, new File(sourceFilePath));
    }

    /**
     *
     * @param program
     */
    public Program(Program program) {
        this(program.name, program.sourceFile);
        attributes.putAll(program.attributes);
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
        hash = 31 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Object putAttribute(String key, Object value) {
        return attributes.put(key, value);
    }

    public Object removeAttribute(String key) {
        return attributes.remove(key);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

}
