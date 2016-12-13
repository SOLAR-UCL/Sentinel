package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.GroupOperatorsByMutantQuantity;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.GroupOperatorsByType;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class OperatorGroupingFactoryTest {

    private static Rule testingRule;

    @BeforeClass
    public static void setUpClass() {
        try {
            StrategyMapper strategyMapper = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
            testingRule = strategyMapper.getNonTerminalRule(NonTerminalRuleType.OPERATOR_GROUPING);
        } catch (IOException ex) {
            Logger.getLogger(OperatorGroupingFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OperatorGroupingFactoryTest() {
    }

    /**
     * Testing Type Grouping
     */
    @Test
    public void testCreateOperation() {
        Iterator<Integer> iterator = Lists.newArrayList(0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof GroupOperatorsByType);
    }

    /**
     * Testing Mutant Quantity Grouping
     */
    @Test
    public void testCreateOperation2() {
        Iterator<Integer> iterator = Lists.newArrayList(1).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof GroupOperatorsByMutantQuantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidOperation() {
        Factory factory = OperatorGroupingFactory.getInstance();
        Iterator<Integer> iterator = Lists.newArrayList(0).iterator();
        Option option = new Option(Lists.newArrayList(new Rule("Unknown")));
        factory.createOperation(new Option(Lists.newArrayList(new Rule("Unknown", Lists.newArrayList(option)))), iterator);
    }

}
