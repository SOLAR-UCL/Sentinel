package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Option {

    protected int index;
    protected List<Rule> rules;

    public Option() {
        this.rules = new ArrayList<>();
    }

    public Option(int index) {
        this();
        this.index = index;
    }

    public Option(int index, List<Rule> rules) {
        this.index = index;
        this.rules = rules;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public boolean addRule(Rule object) {
        return rules.add(object);
    }

    public boolean removeRule(Rule object) {
        return rules.remove(object);
    }

    public void clearRules() {
        rules.clear();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.rules);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Option other = (Option) obj;
        return Iterables.elementsEqual(rules, other.getRules());
    }

    @Override
    public String toString() {
        return Joiner.on(" ").join(rules);
    }

}
