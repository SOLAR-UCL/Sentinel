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
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant.MutantsOperatorComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.mutant.MutantsOperatorTypeComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.MutantQuantityComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.OperatorTypeComparator;
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
public class SortingFactoryTest {

    private static Rule testingRuleMutant;
    private static Rule testingRuleOperator;

    @BeforeClass
    public static void setUpClass() {
        try {
            StrategyMapper strategyMapper = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
            testingRuleOperator = strategyMapper.getNonTerminalRule(NonTerminalRuleType.OPERATOR_SORTING);
            testingRuleMutant = strategyMapper.getNonTerminalRule(NonTerminalRuleType.MUTANT_SORTING);
        } catch (IOException ex) {
            Logger.getLogger(SortingFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SortingFactoryTest() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidOperation() {
        Factory factory = SortingFactory.getInstance();
        Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0).iterator();
        factory.createOperation(new Option(Lists.newArrayList(new Rule("Unknown", Lists.newArrayList(new Option(Lists.newArrayList(new Rule("Unknown"))))))), iterator);
    }

    /**
     * Testing Sort by Type
     */
    @Test
    public void testCreateOperation() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRuleOperator, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof OperatorTypeComparator);
    }

    /**
     * Testing Sort by Mutant Quantity
     */
    @Test
    public void testCreateOperation2() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 1, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRuleOperator, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof MutantQuantityComparator);
    }

    /**
     * Testing Reversed
     */
    @Test
    public void testCreateOperation3() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 1, 1).iterator();
        AbstractSorterOperation operation = (AbstractSorterOperation) FactoryFlyweight.getNonTerminalFactory()
                .createOperation(testingRuleOperator, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof AbstractSorterOperation);
        assertTrue(operation.isReversed());
    }

    /**
     * Testing Reversed
     */
    @Test
    public void testCreateOperation4() {
        Iterator<Integer> iterator = Lists.newArrayList(1).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRuleOperator, iterator);
        assertNull(operation);
    }

    @Test
    public void testCreateOperation5() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRuleMutant, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof MutantsOperatorTypeComparator);
    }

    @Test
    public void testCreateOperation6() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 1, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRuleMutant, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof MutantsOperatorComparator);
    }

}
