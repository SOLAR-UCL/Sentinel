package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Giovani Guizzo
 */
public class Option {

    /**
     *
     */
    protected List<Rule> rules;

    /**
     *
     */
    public Option() {
        this.rules = new ArrayList<>();
    }

    /**
     *
     * @param rules
     */
    public Option(List<Rule> rules) {
        this.rules = rules;
    }

    /**
     *
     * @param object
     * @return
     */
    public boolean addRule(Rule object) {
        return this.rules.add(object);
    }

    /**
     *
     */
    public void clearRules() {
        this.rules.clear();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Option other = (Option) obj;
        return Iterables.elementsEqual(this.rules, other.getRules());
    }

    /**
     *
     * @return
     */
    public List<Rule> getRules() {
        return Collections.unmodifiableList(rules);
    }

    /**
     *
     * @param rules
     */
    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.rules);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    public boolean removeRule(Rule object) {
        return this.rules.remove(object);
    }

    @Override
    public String toString() {
        return Joiner.on(" ").join(this.rules);
    }

}
