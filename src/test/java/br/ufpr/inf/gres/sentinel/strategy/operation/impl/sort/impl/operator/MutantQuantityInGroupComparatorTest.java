package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class MutantQuantityInGroupComparatorTest {

	public MutantQuantityInGroupComparatorTest() {
	}

	@Test
	public void testCompare() {
		MutantQuantityInGroupComparator comparator = new MutantQuantityInGroupComparator();

		Operator operator1 = new Operator("Operator1", "Type1");
		operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));
		operator1.getGeneratedMutants().add(new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest()));

		Operator operator2 = new Operator("Operator2", "Type1");
		operator2.getGeneratedMutants().add(new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest()));

		Operator operator3 = new Operator("Operator3", "Type1");
		operator3.getGeneratedMutants().add(new Mutant("Mutant4", null, IntegrationFacade.getProgramUnderTest()));

		Operator operator4 = new Operator("Operator4", "Type1");
		operator4.getGeneratedMutants().add(new Mutant("Mutant5", null, IntegrationFacade.getProgramUnderTest()));
		operator4.getGeneratedMutants().add(new Mutant("Mutant6", null, IntegrationFacade.getProgramUnderTest()));

		List<Operator> group1 = new ArrayList<>();
		group1.add(operator1);
		group1.add(operator2);
		List<Operator> group2 = new ArrayList<>();
		group2.add(operator3);
		group2.add(operator4);

		int result = comparator.compare(group1, group2);
		assertEquals(0, result);
	}

	@Test
	public void testCompare2() {
		MutantQuantityInGroupComparator comparator = new MutantQuantityInGroupComparator();

		Operator operator1 = new Operator("Operator1", "Type1");
		operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));
		operator1.getGeneratedMutants().add(new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest()));

		Operator operator2 = new Operator("Operator2", "Type1");
		operator2.getGeneratedMutants().add(new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest()));

		Operator operator3 = new Operator("Operator3", "Type1");
		operator3.getGeneratedMutants().add(new Mutant("Mutant4", null, IntegrationFacade.getProgramUnderTest()));

		Operator operator4 = new Operator("Operator4", "Type1");
		operator4.getGeneratedMutants().add(new Mutant("Mutant5", null, IntegrationFacade.getProgramUnderTest()));

		List<Operator> group1 = new ArrayList<>();
		group1.add(operator1);
		group1.add(operator2);
		List<Operator> group2 = new ArrayList<>();
		group2.add(operator3);
		group2.add(operator4);

		int result = comparator.compare(group1, group2);
		assertTrue(result > 0);
	}

	@Test
	public void testCompare3() {
		MutantQuantityInGroupComparator comparator = new MutantQuantityInGroupComparator();

		Operator operator1 = new Operator("Operator1", "Type1");
		operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));

		Operator operator2 = new Operator("Operator2", "Type1");
		operator2.getGeneratedMutants().add(new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest()));

		Operator operator3 = new Operator("Operator3", "Type1");
		operator3.getGeneratedMutants().add(new Mutant("Mutant4", null, IntegrationFacade.getProgramUnderTest()));

		Operator operator4 = new Operator("Operator4", "Type1");
		operator4.getGeneratedMutants().add(new Mutant("Mutant5", null, IntegrationFacade.getProgramUnderTest()));
		operator4.getGeneratedMutants().add(new Mutant("Mutant6", null, IntegrationFacade.getProgramUnderTest()));

		List<Operator> group1 = new ArrayList<>();
		group1.add(operator1);
		group1.add(operator2);
		List<Operator> group2 = new ArrayList<>();
		group2.add(operator3);
		group2.add(operator4);

		int result = comparator.compare(group1, group2);
		assertTrue(result < 0);
	}

	@Test
	public void testCompare4() {
		MutantQuantityInGroupComparator comparator = new MutantQuantityInGroupComparator();

		Operator operator1 = new Operator("Operator1", "Type1");

		Operator operator2 = new Operator("Operator2", "Type1");

		Operator operator3 = new Operator("Operator3", "Type1");
		operator3.getGeneratedMutants().add(new Mutant("Mutant4", null, IntegrationFacade.getProgramUnderTest()));

		Operator operator4 = new Operator("Operator4", "Type1");
		operator4.getGeneratedMutants().add(new Mutant("Mutant5", null, IntegrationFacade.getProgramUnderTest()));
		operator4.getGeneratedMutants().add(new Mutant("Mutant6", null, IntegrationFacade.getProgramUnderTest()));

		List<Operator> group1 = new ArrayList<>();
		group1.add(operator1);
		group1.add(operator2);
		List<Operator> group2 = new ArrayList<>();
		group2.add(operator3);
		group2.add(operator4);

		int result = comparator.compare(group1, group2);
		assertTrue(result < 0);
	}

	@Test
	public void testCompare5() {
		MutantQuantityInGroupComparator comparator = new MutantQuantityInGroupComparator();

		Operator operator1 = new Operator("Operator1", "Type1");

		Operator operator2 = new Operator("Operator2", "Type1");

		Operator operator3 = new Operator("Operator3", "Type1");

		Operator operator4 = new Operator("Operator4", "Type1");

		List<Operator> group1 = new ArrayList<>();
		group1.add(operator1);
		group1.add(operator2);
		List<Operator> group2 = new ArrayList<>();
		group2.add(operator3);
		group2.add(operator4);

		int result = comparator.compare(group1, group2);
		assertEquals(0, result);
	}

	@Test
	public void testIsSpecific() {
		MutantQuantityInGroupComparator comparator = new MutantQuantityInGroupComparator();
		assertFalse(comparator.isSpecific());
	}

}
