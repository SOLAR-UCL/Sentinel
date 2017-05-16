package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Giovani Guizzo
 */
public class AbstractSelectOperationTest {

    public AbstractSelectOperationTest() {
    }

    @Test
    public void testDoOperation() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setQuantity(1);

        AbstractSelectOperation operation = new AbstractSelectOperationStub();
        operation.setSelection(selectionOp);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");

        Solution solution = new Solution();
        solution.getOperators().add(operator1);
        solution.getOperators().add(operator2);

        operation.doOperation(solution);

        assertEquals(1, solution.getOperators().size());
        assertEquals(operator1, solution.getOperators().get(0));
    }

    @Test
    public void testGetAndSetSelection() {
        SelectionOperation selection = new SelectionOperation();
        AbstractSelectOperation operation = new AbstractSelectOperationStub();
        operation.setSelection(selection);
        assertEquals(selection, operation.getSelection());
    }

    @Test
    public void testIsSpecific() {
        AbstractSelectOperation operation = new AbstractSelectOperationStub();
        assertFalse(operation.isSpecific());
    }

    public static class AbstractSelectOperationStub extends AbstractSelectOperation<Operator> {

        public AbstractSelectOperationStub() {
            super("Stub");
        }

        @Override
        protected List<Operator> obtainList(Solution solution) {
            return solution.getOperators();
        }
    }

}
