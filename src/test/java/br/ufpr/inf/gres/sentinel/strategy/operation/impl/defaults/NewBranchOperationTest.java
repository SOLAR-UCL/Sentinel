package br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
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
