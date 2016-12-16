package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class GroupOperatorsByTypeTest {

	public GroupOperatorsByTypeTest() {
	}

	@Test
	public void testCreateGrouperFunction() {
		GroupOperatorsByType operation = new GroupOperatorsByType();

		Operator operator1 = new Operator("Operator1", "Type1");
		Operator operator2 = new Operator("Operator2", "Type1");
		Operator operator3 = new Operator("Operator3", "Type2");
		Operator operator4 = new Operator("Operator4", "Type3");

		Function<Operator, String> grouperFunction = operation.createGroupingFunction();
		assertEquals("Type1", grouperFunction.apply(operator1));
		assertEquals("Type1", grouperFunction.apply(operator2));
		assertEquals("Type2", grouperFunction.apply(operator3));
		assertEquals("Type3", grouperFunction.apply(operator4));
	}

}
