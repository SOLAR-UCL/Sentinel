package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class OperatorQuantityInGroupComparatorTest {

	public OperatorQuantityInGroupComparatorTest() {
	}

	@Test
	public void testCompare() {
		OperatorQuantityInGroupComparator comparator = new OperatorQuantityInGroupComparator();

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
	public void testCompare2() {
		OperatorQuantityInGroupComparator comparator = new OperatorQuantityInGroupComparator();

		Operator operator1 = new Operator("Operator1", "Type1");
		Operator operator2 = new Operator("Operator2", "Type1");
		Operator operator3 = new Operator("Operator3", "Type1");

		List<Operator> group1 = new ArrayList<>();
		group1.add(operator1);
		group1.add(operator2);
		List<Operator> group2 = new ArrayList<>();
		group2.add(operator3);

		int result = comparator.compare(group1, group2);
		assertTrue(result > 0);
	}

	@Test
	public void testCompare3() {
		OperatorQuantityInGroupComparator comparator = new OperatorQuantityInGroupComparator();

		Operator operator1 = new Operator("Operator1", "Type1");
		Operator operator3 = new Operator("Operator3", "Type1");
		Operator operator4 = new Operator("Operator4", "Type1");

		List<Operator> group1 = new ArrayList<>();
		group1.add(operator1);
		List<Operator> group2 = new ArrayList<>();
		group2.add(operator3);
		group2.add(operator4);

		int result = comparator.compare(group1, group2);
		assertTrue(result < 0);
	}

	@Test
	public void testCompare4() {
		OperatorQuantityInGroupComparator comparator = new OperatorQuantityInGroupComparator();

		Operator operator1 = new Operator("Operator1", "Type1");

		List<Operator> group1 = new ArrayList<>();
		group1.add(operator1);
		List<Operator> group2 = new ArrayList<>();

		int result = comparator.compare(group1, group2);
		assertTrue(result > 0);
	}

	@Test
	public void testCompare5() {
		OperatorQuantityInGroupComparator comparator = new OperatorQuantityInGroupComparator();

		Operator operator3 = new Operator("Operator3", "Type1");

		List<Operator> group1 = new ArrayList<>();
		List<Operator> group2 = new ArrayList<>();
		group2.add(operator3);

		int result = comparator.compare(group1, group2);
		assertTrue(result < 0);
	}

	@Test
	public void testCompare6() {
		OperatorQuantityInGroupComparator comparator = new OperatorQuantityInGroupComparator();

		List<Operator> group1 = new ArrayList<>();
		List<Operator> group2 = new ArrayList<>();

		int result = comparator.compare(group1, group2);
		assertEquals(0, result);
	}

}
