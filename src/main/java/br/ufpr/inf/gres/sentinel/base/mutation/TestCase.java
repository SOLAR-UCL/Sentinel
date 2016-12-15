package br.ufpr.inf.gres.sentinel.base.mutation;

import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Giovani Guizzo
 */
public class TestCase {

	protected String name;
	protected SetUniqueList<Mutant> killingMutants;

	public TestCase(String name) {
		this.name = name;
		this.killingMutants = SetUniqueList.setUniqueList(new ArrayList<>());
	}

	public TestCase(TestCase testCase) {
		this(testCase.name);
		this.killingMutants.addAll(testCase.getKillingMutants());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SetUniqueList<Mutant> getKillingMutants() {
		return killingMutants;
	}

	public void setKillingMutants(SetUniqueList<Mutant> killingMutants) {
		this.killingMutants = killingMutants;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + Objects.hashCode(this.name);
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
		final TestCase other = (TestCase) obj;
		return Objects.equals(this.name, other.name);
	}

	@Override
	public String toString() {
		return name;
	}

}
