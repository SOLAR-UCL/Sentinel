package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class AbstractGroupingFunctionTest {

	public AbstractGroupingFunctionTest() {
	}

	@Test
	public void testDoOperation() {
		AbstractGroupingFunction<String> function = new StubGroupingFunction();
		List<List<String>> result = function.doOperation(Collections.emptyList());

		assertEquals(0, result.size());
	}

	@Test
	public void testDoOperation2() {
		AbstractGroupingFunction<String> function = new StubGroupingFunction();
		List<List<String>> result = function.doOperation(Lists.newArrayList("1", "12", "12", ""));

		assertEquals(3, result.size());
		assertEquals(1, result.get(0).size());
		assertEquals("", result.get(0).get(0));
		assertEquals(1, result.get(1).size());
		assertEquals("1", result.get(1).get(0));
		assertEquals(2, result.get(2).size());
		assertEquals("12", result.get(2).get(0));
		assertEquals("12", result.get(2).get(1));
	}

	private static class StubGroupingFunction extends AbstractGroupingFunction<String> {

		public StubGroupingFunction() {
			super("Stub Grouping");
		}

		@Override
		protected Function<String, ?> createGrouperFunction() {
			return String::length;
		}

		@Override
		public boolean isSpecific() {
			return false;
		}
	}

}
