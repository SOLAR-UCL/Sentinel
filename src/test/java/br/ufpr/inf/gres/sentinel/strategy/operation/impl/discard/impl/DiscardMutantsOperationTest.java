package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class DiscardMutantsOperationTest {

    public DiscardMutantsOperationTest() {
    }

    @Test
    public void testConstructor() {
        SelectionOperation<Mutant> selectionOperation = new SelectionOperation<>();
        DiscardMutantsOperation operation = new DiscardMutantsOperation(selectionOperation);
        assertEquals(selectionOperation, operation.getSelection());
    }

    @Test
    public void testObtainList() {
        Solution solution = new Solution();
        DiscardMutantsOperation operation = new DiscardMutantsOperation();
        assertEquals(solution.getMutants(), operation.obtainList(solution));
    }

}
