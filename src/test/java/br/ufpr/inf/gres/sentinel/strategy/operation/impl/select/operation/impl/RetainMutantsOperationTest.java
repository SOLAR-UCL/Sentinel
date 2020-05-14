package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Giovani Guizzo
 */
public class RetainMutantsOperationTest {

    public RetainMutantsOperationTest() {
    }

    @Test
    public void testConstructor() {
        SelectionOperation<Mutant> selectionOperation = new SelectionOperation<>();
        RetainMutantsOperation operation = new RetainMutantsOperation(selectionOperation);
        assertEquals(selectionOperation, operation.getSelection());
    }

    @Test
    public void testObtainList() {
        Solution solution = new Solution();
        RetainMutantsOperation operation = new RetainMutantsOperation();
        assertEquals(solution.getMutants(), operation.obtainList(solution));
    }

}
