package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.AddAllOperatorsOperation;
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
public class StrategyFactoryTest {

    private static StrategyMapper STRATEGY_MAPPER;

    public StrategyFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            STRATEGY_MAPPER = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
        } catch (IOException ex) {
            Logger.getLogger(StrategyFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test Add All Operators operation
     */
    @Test
    public void testCreateOperation() {
        Rule rule = STRATEGY_MAPPER.getNonTerminalRule(NonTerminalRuleType.STRATEGY);
        Iterator<Integer> iterator = Lists.newArrayList(3).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(rule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof AddAllOperatorsOperation);
    }

}
