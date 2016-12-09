package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class NonTerminalFactoryTest {

    public NonTerminalFactoryTest() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateOperation() {
        NonTerminalFactory instance = NonTerminalFactory.getInstance();
        instance.createOperation(new Rule("invalid"), null);
    }

}
