package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class AbstractDiscardOperationTest {

	public AbstractDiscardOperationTest() {
	}

	@Test
	public void testGetAndSetSelection() {
		SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
		AbstractDiscardOperation operation = new AbstractDiscardOperationStub();
		operation.setSelection(selectionOp);
		assertEquals(selectionOp, operation.getSelection());
	}

	@Test
	public void testIsSpecific() {
		AbstractDiscardOperation operation = new AbstractDiscardOperationStub();
		assertFalse(operation.isSpecific());
	}

	@Test
	public void testDoOperation() {
		SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
		selectionOp.setSelectionType(new SequentialSelection());
		selectionOp.setQuantity(1);

		AbstractDiscardOperation operation = new AbstractDiscardOperationStub();
		operation.setSelection(selectionOp);

		Operator operator1 = new Operator("Operator1", "Type1");
		Operator operator2 = new Operator("Operator2", "Type2");

		Solution solution = new Solution();
		solution.getOperators().add(operator1);
		solution.getOperators().add(operator2);

		operation.doOperation(solution);

		assertEquals(1, solution.getOperators().size());
		assertEquals(operator2, solution.getOperators().get(0));
	}

	public static class AbstractDiscardOperationStub extends AbstractDiscardOperation<Operator> {

		public AbstractDiscardOperationStub() {
			super("Stub");
		}

		@Override
		protected List<Operator> obtainList(Solution solution) {
			return solution.getOperators();
		}
	}

}
