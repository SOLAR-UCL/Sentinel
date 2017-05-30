package br.ufpr.inf.gres.sentinel.strategy;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest;
import br.ufpr.inf.gres.sentinel.strategy.operation.OperationTest;
import br.ufpr.inf.gres.sentinel.strategy.operation.OperationTest.OperationStub;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.AddAllOperatorsOperation;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class StrategyTest {

    @BeforeClass
    public static void setUp() throws Exception {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeTest.IntegrationFacadeStub());
    }

    public StrategyTest() {
    }

    @Test
    public void testGetAndSetFirstOperation() {
        Strategy strategy = new Strategy();
        strategy.setFirstOperation(new AddAllOperatorsOperation());
        assertEquals(new AddAllOperatorsOperation(), strategy.getFirstOperation());
    }

    @Test
    public void testRun() {
        Strategy instance = new Strategy(OperationTest.getComplexTestOperationChain());
        List<Mutant> expResult = Lists.newArrayList(new Mutant("TestOperation executed!", null, IntegrationFacade.getProgramUnderTest()), new Mutant("TestOperation2 executed!", null, IntegrationFacade
                .getProgramUnderTest()), new Mutant("TestOperation3 executed!", null, IntegrationFacade.getProgramUnderTest()), new Mutant("TestOperation5 executed!", null, IntegrationFacade
                .getProgramUnderTest()), new Mutant("TestOperation6 executed!", null, IntegrationFacade.getProgramUnderTest()), new Mutant("TestOperation4 executed!", null, IntegrationFacade
                .getProgramUnderTest()));
        List<Mutant> result = instance.run();
        Assert.assertArrayEquals(expResult.toArray(), result.toArray());
    }

    public void testRun2() {
        Strategy instance = new Strategy();
        List<Mutant> result = instance.run();
        assertArrayEquals(new Mutant[0], result.toArray());
    }

    public void testRun3() {
        Strategy instance = new Strategy();
        instance.setFirstOperation(new OperationStub("Test"));
        List<Mutant> result = instance.run();
        assertEquals(new Mutant("Test executed!", null, IntegrationFacade.getProgramUnderTest()), result.get(0));
    }

    @Test
    public void testRun4() {
        Strategy strategy = new Strategy();
        List<Mutant> result = strategy.run();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testStrategy() throws Exception {
        StrategyMapper strategyMapper = new StrategyMapper(new File(GrammarFiles.getDefaultGrammarPath()));
        // Executes all operators in reversed order, then selects the 10% first mutants (1) and store them.
        Strategy strategy = strategyMapper.interpret(Lists.newArrayList(0, 2, 1, 0, 0, 0, 0, 1, 9, 1, 0, 1, 0, 0, 1, 0, 3, 1, 2));
        List<Mutant> result = strategy.run();
        assertEquals(1, result.size());
        assertEquals("Operator4_1", result.get(0).getName());
    }

    @Test
    public void testToString() {
        Strategy instance = new Strategy();
        String expResult = "Empty Strategy";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    @Test
    public void testToString2() {
        Strategy instance = new Strategy(OperationTest.getComplexTestOperationChain());
        String expResult = "1.TestOperation - 2.TestOperation2 - 3." + TerminalRuleType.NEW_BRANCH + " - 4.TestOperation3\n" + "\t3.1." + TerminalRuleType.NEW_BRANCH + " - 3.2." + TerminalRuleType.NEW_BRANCH + " - 3.3.TestOperation5\n" + "\t3.2.1.TestOperation6\n" + "\t3.1.1.TestOperation4";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
