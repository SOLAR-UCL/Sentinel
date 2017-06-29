package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import java.util.Collection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Giovani Guizzo
 */
public class AbstractDiscardOperationTest {

    public AbstractDiscardOperationTest() {
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

        operation.doOperation(solution, null);

        assertEquals(1, solution.getOperators().size());
        assertEquals(operator2, solution.getOperators().iterator().next());
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

    public static class AbstractDiscardOperationStub extends AbstractDiscardOperation<Operator> {

        public AbstractDiscardOperationStub() {
            super("Stub");
        }

        @Override
        protected Collection<Operator> obtainList(Solution solution) {
            return solution.getOperators();
        }
    }

}
