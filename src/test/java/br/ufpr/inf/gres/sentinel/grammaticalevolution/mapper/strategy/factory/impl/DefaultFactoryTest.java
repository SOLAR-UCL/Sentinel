package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.NewBranchOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.StoreMutantsOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.AbstractSelectOperation;
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
public class DefaultFactoryTest {

    private static Rule testingRule;

    public DefaultFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            StrategyMapper strategyMapper = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
            testingRule = strategyMapper.getNonTerminalRule(NonTerminalRuleType.DEFAULT_OPERATION);
        } catch (IOException ex) {
            Logger.getLogger(DefaultFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test Store Mutants creation
     */
    @Test
    public void testCreateOperation() {
        Iterator<Integer> iterator = Lists.newArrayList(3).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof StoreMutantsOperation);
    }

    /**
     * Test New Branch creation
     */
    @Test
    public void testCreateOperation2() {
        Iterator<Integer> iterator = Lists.newArrayList(2, 2, 3, 3, 3).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);

        assertTrue(operation instanceof NewBranchOperation);

        Operation successor = operation.getSuccessor();
        Operation secondSuccessor = ((NewBranchOperation) operation).getSecondSuccessor();

        assertTrue(successor instanceof NewBranchOperation);
        assertTrue(secondSuccessor instanceof StoreMutantsOperation);

        assertTrue(successor.getSuccessor() instanceof StoreMutantsOperation);
        assertTrue(((NewBranchOperation) successor).getSecondSuccessor() instanceof StoreMutantsOperation);
    }

    /**
     * Test Operators Operation creation
     */
    @Test
    public void testCreateOperation3() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0, 0, 0, 1, 0, 3).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof AbstractSelectOperation);
    }

    //TODO test mutant part
    /**
     * Test Mutants Operation creation
     */
    @Test
    public void testCreateOperation4() {
//        Rule rule = STRATEGY_MAPPER.getNonTerminalRule(NonTerminalRuleType.DEFAULT_OPERATION);
//        Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0, 0, 0, 1, 0, 3).iterator();
//        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(rule, iterator);
//        assertNotNull(operation);
//        assertTrue(operation instanceof AbstractSelectOperation);
    }
}
