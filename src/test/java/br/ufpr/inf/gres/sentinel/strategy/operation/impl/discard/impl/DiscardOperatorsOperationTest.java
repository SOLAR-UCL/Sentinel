package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class DiscardOperatorsOperationTest {

    public DiscardOperatorsOperationTest() {
    }

    @Test
    public void testConstructor() {
        SelectionOperation<Operator> selectionOperation = new SelectionOperation<>();
        DiscardOperatorsOperation operation = new DiscardOperatorsOperation(selectionOperation);
        assertEquals(selectionOperation, operation.getSelection());
    }

    @Test
    public void testObtainList() {
        Solution solution = new Solution();
        DiscardOperatorsOperation operation = new DiscardOperatorsOperation();
        assertEquals(solution.getOperators(), operation.obtainList(solution));
    }

}
