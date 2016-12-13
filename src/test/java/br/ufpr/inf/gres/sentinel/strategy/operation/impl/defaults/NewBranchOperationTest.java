package br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults;

import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class NewBranchOperationTest {

    public NewBranchOperationTest() {
    }

    @Test
    public void testIsSpecific() {
        NewBranchOperation operation = new NewBranchOperation();
        assertFalse(operation.isSpecific());
    }

}
