package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant.GroupMutantsByFOMOrHOM;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant.GroupMutantsByOperator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant.GroupMutantsByOperatorType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant.GroupMutantsByOrder;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator.GroupOperatorsByMutantQuantity;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator.GroupOperatorsByType;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Giovani Guizzo
 */
public class GroupingFactoryTest {

    private static Rule mutantRule;
    private static Rule operatorRule;

    @BeforeClass
    public static void setUpClass() {
        try {
            StrategyMapper strategyMapper = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
            operatorRule = strategyMapper.getNonTerminalRule(NonTerminalRuleType.OPERATOR_GROUPING);
            mutantRule = strategyMapper.getNonTerminalRule(NonTerminalRuleType.MUTANT_GROUPING);
        } catch (IOException ex) {
            Logger.getLogger(GroupingFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GroupingFactoryTest() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidOperation() {
        Factory factory = GroupingFactory.getInstance();
        Iterator<Integer> iterator = Lists.newArrayList(0).iterator();
        Option option = new Option(Lists.newArrayList(new Rule("Unknown")));
        factory.createOperation(new Option(Lists.newArrayList(new Rule("Unknown", Lists.newArrayList(option)))), iterator);
    }

    /**
     * Testing Type Grouping
     */
    @Test
    public void testCreateOperation() {
        Iterator<Integer> iterator = Lists.newArrayList(0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(operatorRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof GroupOperatorsByType);
    }

    /**
     * Testing Mutant Quantity Grouping
     */
    @Test
    public void testCreateOperation2() {
        Iterator<Integer> iterator = Lists.newArrayList(1).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(operatorRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof GroupOperatorsByMutantQuantity);
    }

    @Test
    public void testCreateOperation3() {
        Iterator<Integer> iterator = Lists.newArrayList(0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(mutantRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof GroupMutantsByOperatorType);
    }

    @Test
    public void testCreateOperation4() {
        Iterator<Integer> iterator = Lists.newArrayList(1).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(mutantRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof GroupMutantsByOperator);
    }

    @Test
    public void testCreateOperation5() {
        Iterator<Integer> iterator = Lists.newArrayList(2).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(mutantRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof GroupMutantsByFOMOrHOM);
    }

    @Test
    public void testCreateOperation6() {
        Iterator<Integer> iterator = Lists.newArrayList(3).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(mutantRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof GroupMutantsByOrder);
    }

}
