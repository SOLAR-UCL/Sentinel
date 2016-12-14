package br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest.IntegrationFacadeStub;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.impl.ConventionalExecution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class ExecuteOperatorsOperationTest {

    public ExecuteOperatorsOperationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeStub());
    }

    @Test
    public void testDoOperation() {
        Solution solution = new Solution();
        ExecuteOperatorsOperation operation = new ExecuteOperatorsOperation();
        operation.setExecutionType(new ConventionalExecution());

        SelectionOperation<Operator> selectionOperation = new SelectionOperation<>();
        selectionOperation.setQuantity(4);
        selectionOperation.setSelectionType(new SequentialSelection());
        operation.setSelection(selectionOperation);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        operator2.getGeneratedMutants().add(new Mutant("Operator1_1", null, IntegrationFacade.getProgramUnderTest()));
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        solution.getOperators().add(operator1);
        solution.getOperators().add(operator2);
        solution.getOperators().add(operator3);
        solution.getOperators().add(operator4);

        operation.doOperation(solution);

        assertEquals(16, solution.getMutants().size());
        assertEquals(4, operator1.getGeneratedMutants().size());
        assertEquals(5, operator2.getGeneratedMutants().size());
        assertEquals(4, operator3.getGeneratedMutants().size());
        assertEquals(4, operator4.getGeneratedMutants().size());
    }

    @Test
    public void testDoOperation2() {
        Solution solution = new Solution();
        ExecuteOperatorsOperation operation = new ExecuteOperatorsOperation();
        operation.setExecutionType(new ConventionalExecution());

        SelectionOperation<Operator> selectionOperation = new SelectionOperation<>();
        selectionOperation.setQuantity(4);
        selectionOperation.setSelectionType(new SequentialSelection());
        operation.setSelection(selectionOperation);

        operation.doOperation(solution);

        assertEquals(0, solution.getMutants().size());
    }

    @Test
    public void testIsSpecific() {
        ExecuteOperatorsOperation operation = new ExecuteOperatorsOperation();
        operation.setExecutionType(new ConventionalExecution());

        SelectionOperation<Operator> selectionOperation = new SelectionOperation<>();
        selectionOperation.setQuantity(4);
        selectionOperation.setSelectionType(new SequentialSelection());
        operation.setSelection(selectionOperation);

        assertFalse(operation.isSpecific());
    }

}
