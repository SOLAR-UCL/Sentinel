package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.MutantQuantityComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.OperatorTypeComparator;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class OperatorSortingFactoryTest {

    private static Rule testingRule;

    @BeforeClass
    public static void setUpClass() {
        try {
            StrategyMapper strategyMapper = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
            testingRule = strategyMapper.getNonTerminalRule(NonTerminalRuleType.OPERATOR_SORTING);
        } catch (IOException ex) {
            Logger.getLogger(OperatorSortingFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OperatorSortingFactoryTest() {
    }

    /**
     * Testing Sort by Type
     */
    @Test
    public void testCreateOperation() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof OperatorTypeComparator);
    }

    /**
     * Testing Sort by Mutant Quantity
     */
    @Test
    public void testCreateOperation2() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 1, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof MutantQuantityComparator);
    }

    /**
     * Testing Reversed
     */
    @Test
    public void testCreateOperation3() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 1, 1).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof AbstractSorterOperation);
        assertFalse(operation instanceof MutantQuantityComparator);
        assertFalse(operation instanceof OperatorTypeComparator);
    }

    /**
     * Testing Reversed
     */
    @Test
    public void testCreateOperation4() {
        Iterator<Integer> iterator = Lists.newArrayList(1).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNull(operation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidOperation() {
        Factory factory = OperatorSortingFactory.getInstance();
        Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0).iterator();
        factory.createOperation(new Option(Lists.newArrayList(new Rule("Unknown", Lists.newArrayList(new Option(Lists.newArrayList(new Rule("Unknown"))))))), iterator);
    }

}
