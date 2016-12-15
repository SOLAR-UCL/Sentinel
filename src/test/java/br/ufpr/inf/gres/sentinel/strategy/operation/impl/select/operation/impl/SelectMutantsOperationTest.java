package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class SelectMutantsOperationTest {

	public SelectMutantsOperationTest() {
	}

	@Test
	public void testConstructor() {
		SelectionOperation<Mutant> selectionOperation = new SelectionOperation<>();
		SelectMutantsOperation operation = new SelectMutantsOperation(selectionOperation);
		assertEquals(selectionOperation, operation.getSelection());
	}

	@Test
	public void testObtainList() {
		Solution solution = new Solution();
		SelectMutantsOperation operation = new SelectMutantsOperation();
		assertEquals(solution.getMutants(), operation.obtainList(solution));
	}

}
