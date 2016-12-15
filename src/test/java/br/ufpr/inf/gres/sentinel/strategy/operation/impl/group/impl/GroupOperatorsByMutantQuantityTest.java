package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class GroupOperatorsByMutantQuantityTest {

	public GroupOperatorsByMutantQuantityTest() {
	}

	@Test
	public void testCreateGrouperFunction() {
		GroupOperatorsByMutantQuantity operation = new GroupOperatorsByMutantQuantity();

		Operator operator1 = new Operator("Operator1", "Type1");
		operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));
		Operator operator2 = new Operator("Operator2", "Type2");
		operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));
		operator2.getGeneratedMutants().add(new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest()));
		Operator operator3 = new Operator("Operator3", "Type1");

		Function<Operator, Integer> grouperFunction = operation.createGroupingFunction();
		assertEquals((int) 1, (int) grouperFunction.apply(operator1));
		assertEquals((int) 2, (int) grouperFunction.apply(operator2));
		assertEquals((int) 0, (int) grouperFunction.apply(operator3));
	}

	@Test
	public void testIsSpecific() {
		GroupOperatorsByMutantQuantity operation = new GroupOperatorsByMutantQuantity();
		assertFalse(operation.isSpecific());
	}
}
