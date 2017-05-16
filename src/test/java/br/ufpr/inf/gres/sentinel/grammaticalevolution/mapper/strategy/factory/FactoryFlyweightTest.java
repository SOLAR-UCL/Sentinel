package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Giovani Guizzo
 */
public class FactoryFlyweightTest {

    public FactoryFlyweightTest() {
    }

    @Test(expected = NullPointerException.class)
    public void getFactory() {
        FactoryFlyweight.getFactory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFactory2() {
        FactoryFlyweight.getFactory("invalid");
    }

    @Test
    public void getFactory3() {
        Factory factory = FactoryFlyweight.getFactory(NonTerminalRuleType.UNKNOWN_NON_TERMINAL);
        assertNotNull(factory);
    }

    @Test
    public void getNonTerminalFactory() {
        Factory nonTerminalFacotry = FactoryFlyweight.getNonTerminalFactory();
        assertNotNull(nonTerminalFacotry);
    }

}
