package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class RandomSelectionTest {

	public RandomSelectionTest() {
	}

	@Test
	public void testSelectItems() {
		List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
		RandomSelection<Integer> operation = new RandomSelection<>();
		List<Integer> result = operation.selectItems(input, 0);
		assertEquals(0, result.size());
	}

	@Test
	public void testSelectItems2() {
		List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
		RandomSelection<Integer> operation = new RandomSelection<>();
		List<Integer> result = operation.selectItems(input, 1);
		assertEquals(1, result.size());
	}

	@Test
	public void testSelectItems3() {
		List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
		RandomSelection<Integer> operation = new RandomSelection<>();
		List<Integer> result = operation.selectItems(input, -1);
		assertEquals(0, result.size());
	}

	@Test
	public void testSelectItems4() {
		List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
		RandomSelection<Integer> operation = new RandomSelection<>();
		List<Integer> result = operation.selectItems(input, 6);
		assertEquals(6, result.size());
	}

	@Test
	public void testSelectItems5() {
		List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
		RandomSelection<Integer> operation = new RandomSelection<>();
		List<Integer> result = operation.selectItems(input, 7);
		assertEquals(7, result.size());
	}

	@Test
	public void testSelectItems6() {
		List<Integer> input = Lists.newArrayList(1);
		RandomSelection<Integer> operation = new RandomSelection<>();
		List<Integer> result = operation.selectItems(input, 3);
		assertEquals(3, result.size());
		assertEquals((int) 1, (int) result.get(0));
		assertEquals((int) 1, (int) result.get(1));
		assertEquals((int) 1, (int) result.get(2));
	}

	@Test
	public void testSelectItems7() {
		List<Integer> input = new ArrayList<>();
		RandomSelection<Integer> operation = new RandomSelection<>();
		List<Integer> result = operation.selectItems(input, 3);
		assertEquals(0, result.size());
	}

	@Test
	public void testIsSpecific() {
		RandomSelection<Integer> operation = new RandomSelection<>();
		assertFalse(operation.isSpecific());
	}

}
