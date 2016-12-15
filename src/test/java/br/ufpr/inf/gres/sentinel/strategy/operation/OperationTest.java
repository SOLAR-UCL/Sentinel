package br.ufpr.inf.gres.sentinel.strategy.operation;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.NewBranchOperation;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class OperationTest {

	public OperationTest() {
	}

	public static Operation getDualOperationChain() {
		Operation instance = new OperationStub("TestOperation");
		instance.setSuccessor(new OperationStub("TestOperation2"));
		return instance;
	}

	public static Operation getComplexTestOperationChain() {
		Operation instance = new OperationStub("TestOperation");
		Operation instance2 = new OperationStub("TestOperation2");
		instance.setSuccessor(instance2);
		NewBranchOperation newBranch = new NewBranchOperation();
		instance2.setSuccessor(newBranch);
		newBranch.setSuccessor(new OperationStub("TestOperation3"));
		NewBranchOperation newBranch2 = new NewBranchOperation();
		newBranch.setSecondSuccessor(newBranch2);
		NewBranchOperation newBranch3 = new NewBranchOperation();
		newBranch2.setSuccessor(newBranch3);
		newBranch2.setSecondSuccessor(new OperationStub("TestOperation4"));
		newBranch3.setSuccessor(new OperationStub("TestOperation5"));
		newBranch3.setSecondSuccessor(new OperationStub("TestOperation6"));
		return instance;
	}

	@Test
	public void testToString() {
		Operation instance = getDualOperationChain();
		String expResult = "TestOperation";
		String result = instance.toString();
		assertEquals(expResult, result);
	}

	@Test
	public void testToStringComplete() {
		Operation instance = getDualOperationChain();
		String expResult = "1.TestOperation - 2.TestOperation2";
		String result = instance.toStringComplete();
		assertEquals(expResult, result);
	}

	@Test
	public void testToStringCompleteSingleOperation() {
		Operation instance = new OperationStub("TestOperation");
		String expResult = "1.TestOperation";
		String result = instance.toStringComplete();
		assertEquals(expResult, result);
	}

	@Test
	public void testToStringCompleteWithNewBranch() {
		Operation instance = getComplexTestOperationChain();

		String expResult = "1.TestOperation - 2.TestOperation2 - 3." + TerminalRuleType.NEW_BRANCH + " - 4.TestOperation3\n" + "\t3.1." + TerminalRuleType.NEW_BRANCH + " - 3.2." + TerminalRuleType.NEW_BRANCH + " - 3.3.TestOperation5\n" + "\t3.2.1.TestOperation6\n" + "\t3.1.1.TestOperation4";
		String result = instance.toStringComplete();
		assertEquals(expResult, result);
	}

	@Test
	public void testHashCode() {
		Operation instance = new OperationStub("TestOperation");
		Operation instance2 = new OperationStub("TestOperation");
		int result = instance.hashCode();
		int result2 = instance2.hashCode();
		assertEquals(result, result2);
	}

	@Test
	public void testHashCode2() {
		Operation instance = new OperationStub("TestOperation");
		Operation instance2 = new OperationStub("TestOperation2");
		int result = instance.hashCode();
		int result2 = instance2.hashCode();
		assertNotEquals(result, result2);
	}

	@Test
	public void testHashCode3() {
		Operation instance = new OperationStub("TestOperation");
		Operation instance2 = instance;
		int result = instance.hashCode();
		int result2 = instance2.hashCode();
		assertEquals(result, result2);
	}

	@Test
	public void testEquals() {
		Operation instance = new OperationStub("TestOperation");
		Operation instance2 = new OperationStub("TestOperation");
		assertEquals(instance, instance2);
	}

	@Test
	public void testEquals2() {
		Operation instance = new OperationStub("TestOperation");
		assertEquals(instance, instance);
	}

	@Test
	public void testEquals3() {
		Operation instance = new OperationStub("TestOperation");
		Operation instance2 = new OperationStub("TestOperation2");
		assertNotEquals(instance, instance2);
	}

	@Test
	public void testEquals4() {
		Operation instance = new OperationStub("TestOperation");
		Operation instance2 = null;
		assertNotEquals(instance, instance2);
	}

	@Test
	public void testEquals5() {
		Operation instance = new OperationStub("TestOperation");
		Object instance2 = new Object();
		assertNotEquals(instance, instance2);
	}

	@Test
	public void testGetAndSetName() {
		Operation instance = new OperationStub("TestOperation");
		instance.setName("TestOperationAgain");
		assertEquals("TestOperationAgain", instance.getName());
	}

	public static class OperationStub extends Operation<Solution, List<Mutant>> {

		public OperationStub(String name) {
			super(name);
		}

		@Override
		public List<Mutant> doOperation(Solution input) {
			input.getMutants().add(new Mutant(name + " executed!", null, IntegrationFacade.getProgramUnderTest()));
			List<Mutant> result = next(input);
			return result == null ? input.getMutants() : result;
		}

		@Override
		public boolean isSpecific() {
			return false;
		}
	}

}
