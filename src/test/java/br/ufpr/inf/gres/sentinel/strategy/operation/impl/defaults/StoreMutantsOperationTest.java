package br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest.IntegrationFacadeStub;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Giovani Guizzo
 */
public class StoreMutantsOperationTest {

    @BeforeClass
    public static void setUpClass() {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeStub());
    }

    public StoreMutantsOperationTest() {
    }

    @Test
    public void testDoOperation() {
        Solution solution = new Solution();
        solution.getMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));
        solution.getMutants().add(new Mutant("Mutant2", null, new Program("Program1", "Program/path")));

        StoreMutantsOperation storeMutantsOperation = new StoreMutantsOperation();
        List<Mutant> result = storeMutantsOperation.doOperation(solution, null);

        assertEquals(2, result.size());
    }

    @Test
    public void testIsSpecific() {
        StoreMutantsOperation storeMutantsOperation = new StoreMutantsOperation();
        assertFalse(storeMutantsOperation.isSpecific());
    }

}
