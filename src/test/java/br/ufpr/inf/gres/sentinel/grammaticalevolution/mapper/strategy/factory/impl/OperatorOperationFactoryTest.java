package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.DiscardOperatorsOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.ExecuteOperatorsOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.SelectOperatorsOperation;
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
public class OperatorOperationFactoryTest {

    private static Rule testingRule;

    @BeforeClass
    public static void setUpClass() {
        try {
            StrategyMapper strategyMapper = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
            testingRule = strategyMapper.getNonTerminalRule(NonTerminalRuleType.OPERATOR_OPERATION);
        } catch (IOException ex) {
            Logger.getLogger(OperatorOperationFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OperatorOperationFactoryTest() {
    }

    @Test
    public void testCreateOperation() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof SelectOperatorsOperation);
        assertNotNull(((SelectOperatorsOperation) operation).getSelection());
    }

    @Test
    public void testCreateOperation2() {
        Iterator<Integer> iterator = Lists.newArrayList(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof DiscardOperatorsOperation);
        assertNotNull(((DiscardOperatorsOperation) operation).getSelection());
    }

    @Test
    public void testCreateOperation3() {
        Iterator<Integer> iterator = Lists.newArrayList(2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof ExecuteOperatorsOperation);
        assertNotNull(((ExecuteOperatorsOperation) operation).getSelection());
        assertNotNull(((ExecuteOperatorsOperation) operation).getExecutionType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidOperation() {
        Factory factory = OperatorOperationFactory.getInstance();
        Iterator<Integer> iterator = Lists.newArrayList(0).iterator();
        factory.createOperation(new Option(Lists.newArrayList(new Rule("Unknown"))), iterator);
    }

}
