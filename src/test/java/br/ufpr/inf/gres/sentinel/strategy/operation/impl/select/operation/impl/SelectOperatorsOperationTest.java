package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Giovani Guizzo
 */
public class SelectOperatorsOperationTest {

    public SelectOperatorsOperationTest() {
    }

    @Test
    public void testConstructor() {
        SelectionOperation<Operator> selectionOperation = new SelectionOperation<>();
        RetainOperatorsOperation operation = new RetainOperatorsOperation(selectionOperation);
        assertEquals(selectionOperation, operation.getSelection());
    }

    @Test
    public void testObtainList() {
        Solution solution = new Solution();
        RetainOperatorsOperation operation = new RetainOperatorsOperation();
        assertEquals(solution.getOperators(), operation.obtainList(solution));
    }

}
