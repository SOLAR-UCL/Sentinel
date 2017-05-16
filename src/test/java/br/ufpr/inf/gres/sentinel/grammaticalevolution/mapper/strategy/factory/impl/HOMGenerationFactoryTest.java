package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.impl.ConventionalGeneration;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.impl.SingleHOMGeneration;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class HOMGenerationFactoryTest {

    private static Rule testingRule;

    @BeforeClass
    public static void setUpClass() {
        try {
            StrategyMapper strategyMapper = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
            testingRule = strategyMapper.getNonTerminalRule(NonTerminalRuleType.HOM_GENERATION);
        } catch (IOException ex) {
            Logger.getLogger(HOMGenerationFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HOMGenerationFactoryTest() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidOperation() {
        Factory factory = HOMGenerationFactory.getInstance();
        Iterator<Integer> iterator = Lists.newArrayList(0).iterator();
        factory.createOperation(new Option(Lists.newArrayList(new Rule("Unknown"))), iterator);
    }

    /**
     * Testing Single HOM
     */
    @Test
    public void testCreateOperation1() {
        Iterator<Integer> iterator = Lists.newArrayList(0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof SingleHOMGeneration);
    }

    /**
     * Testing Conventional Generation
     */
    @Test
    public void testCreateOperation2() {
        Iterator<Integer> iterator = Lists.newArrayList(1, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof ConventionalGeneration);
        ConventionalGeneration generation = (ConventionalGeneration) operation;
        assertEquals(2, generation.getOrder());
    }

}
