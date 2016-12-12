package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.AbstractGrammarMapperTest;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class StrategyMapperTest {

    private static StrategyMapper DEFAULT_MAPPER;

    public StrategyMapperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            DEFAULT_MAPPER = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
        } catch (IOException ex) {
            Logger.getLogger(AbstractGrammarMapperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testHookInterpret() {
        StrategyMapper strategyMapper = new StrategyMapper();
        Strategy strategy = strategyMapper.interpret(Lists.newArrayList(1, 2, 3, 4, 5, 6));
        assertNotNull(strategy);
    }

    @Test
    public void testCreate() throws IOException {
        StrategyMapper strategyMapper = new StrategyMapper(new File(GrammarFiles.getDefaultGrammarPath()));
        Strategy strategy = strategyMapper.interpret(Lists.newArrayList(3));
        assertNotNull(strategy);
    }

    //TODO test strategy generation
}
