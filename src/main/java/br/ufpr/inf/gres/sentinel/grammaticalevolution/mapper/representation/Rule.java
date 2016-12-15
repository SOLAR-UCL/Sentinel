package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Rule {

	protected String name;
	protected List<Option> options;

	public Rule(String name) {
		this(name, new ArrayList<>());
	}

	public Rule(String name, List<Option> options) {
		this.name = name;
		this.options = options;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public boolean addOption(Option object) {
		return options.add(object);
	}

	public boolean removeOption(Option object) {
		return options.remove(object);
	}

	public void clearOptions() {
		options.clear();
	}

	public Option getOption(int index) {
		return options.get(index % options.size());
	}

	public Option getOption(Iterator<Integer> iterator) {
		return getOptions().size() > 1 ? getOption(iterator.next()) : getOption(0);
	}

	public boolean isTerminal() {
		return options.isEmpty();
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 53 * hash + Objects.hashCode(this.name);
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
		final Rule other = (Rule) obj;
		return Objects.equals(this.name, other.name);
	}

	@Override
	public String toString() {
		return isTerminal() ? "\"" + name + "\"" : "<" + name + ">";
	}

	public String toCompleteString() {
		return isTerminal() ? "\"" + name + "\"" : "<" + name + "> ::= " + Joiner.on(" | ").join(options);
	}

}
