package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation;

import com.google.common.base.Joiner;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 *
 * @author Giovani Guizzo
 */
public class Rule {

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected List<Option> options;

    /**
     *
     * @param name
     */
    public Rule(String name) {
        this(name, new ArrayList<>());
    }

    /**
     *
     * @param name
     * @param options
     */
    public Rule(String name, List<Option> options) {
        this.name = name;
        this.options = options;
    }

    /**
     *
     * @param object
     * @return
     */
    public boolean addOption(Option object) {
        return this.options.add(object);
    }

    /**
     *
     */
    public void clearOptions() {
        this.options.clear();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Rule other = (Rule) obj;
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
     * @param index
     * @return
     */
    public Option getOption(int index) {
        return this.options.get(index % this.options.size());
    }

    /**
     *
     * @param iterator
     * @return
     */
    public Option getOption(Iterator<Integer> iterator) {
        if (this.getOptions().size() > 1) {
            checkArgument(iterator.hasNext(), "Invalid Iterator. Reached the end of it but I need more integers to consume and interpret the rules.");
            return this.getOption(iterator.next());
        } else {
            return this.getOption(0);
        }
    }

    /**
     *
     * @return
     */
    public List<Option> getOptions() {
        return Collections.unmodifiableList(options);
    }

    /**
     *
     * @param options
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.name);
        return hash;
    }

    /**
     *
     * @return
     */
    public boolean isTerminal() {
        return this.options.isEmpty();
    }

    /**
     *
     * @param object
     * @return
     */
    public boolean removeOption(Option object) {
        return this.options.remove(object);
    }

    /**
     *
     * @return
     */
    public String toCompleteString() {
        return this.isTerminal() ? "\"" + this.name + "\"" : "<" + this.name + "> ::= " + Joiner.on(" | ").join(this.options);
    }

    @Override
    public String toString() {
        return this.isTerminal() ? "\"" + this.name + "\"" : "<" + this.name + ">";
    }

}
