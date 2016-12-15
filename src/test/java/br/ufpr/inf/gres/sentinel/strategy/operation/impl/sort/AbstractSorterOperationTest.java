package br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class AbstractSorterOperationTest {

	public AbstractSorterOperationTest() {
	}

	@Test
	public void testDoOperation() {
		AbstractSorterOperationStub stub = new AbstractSorterOperationStub();
		int result = stub.doOperation(Lists.newArrayList(1, 1));
		assertEquals(0, result);
	}

	@Test
	public void testDoOperation2() {
		AbstractSorterOperationStub stub = new AbstractSorterOperationStub();
		int result = stub.doOperation(Lists.newArrayList(1, 2));
		assertTrue(result < 0);
	}

	@Test
	public void testDoOperation3() {
		AbstractSorterOperationStub stub = new AbstractSorterOperationStub();
		int result = stub.doOperation(Lists.newArrayList(2, 1));
		assertTrue(result > 0);
	}

	@Test
	public void testReversed() {
		AbstractSorterOperation<Integer> stub = new AbstractSorterOperationStub();
		stub.setReversed(true);
		int result = stub.doOperation(Lists.newArrayList(1, 1));
		assertEquals(0, result);
	}

	@Test
	public void testReversed2() {
		AbstractSorterOperation<Integer> stub = new AbstractSorterOperationStub();
		stub.setReversed(true);
		int result = stub.doOperation(Lists.newArrayList(1, 2));
		assertTrue(result > 0);
	}

	@Test
	public void testReversed3() {
		AbstractSorterOperation<Integer> stub = new AbstractSorterOperationStub();
		stub.setReversed(true);
		int result = stub.doOperation(Lists.newArrayList(2, 1));
		assertTrue(result < 0);
	}

	@Test
	public void testGetAndSetReversed() throws Exception {
		AbstractSorterOperation<Integer> stub = new AbstractSorterOperationStub();
		stub.setReversed(true);
		assertTrue(stub.isReversed());
	}

	public class AbstractSorterOperationStub extends AbstractSorterOperation<Integer> {

		public AbstractSorterOperationStub() {
			super("");
		}

		@Override
		protected Function<Integer, Integer> createSortingFunction() {
			return integer -> integer;
		}

		@Override
		public boolean isSpecific() {
			return false;
		}
	}

}
