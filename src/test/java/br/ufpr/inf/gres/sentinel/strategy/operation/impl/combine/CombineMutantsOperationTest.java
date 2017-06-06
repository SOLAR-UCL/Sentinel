package br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.impl.ConventionalGeneration;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Giovani Guizzo
 */
public class CombineMutantsOperationTest {

    @BeforeClass
    public static void setUpClass() {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeTest.IntegrationFacadeStub());
    }

    @Test
    public void doOperation() throws Exception {
        Solution solution = new Solution();

        CombineMutantsOperation operation = new CombineMutantsOperation();

        ConventionalGeneration generation = new ConventionalGeneration(2);
        operation.setGeneration(generation);

        SelectionOperation<Mutant> selectionOperation = new SelectionOperation<>();
        selectionOperation.setQuantity(4);
        selectionOperation.setSelectionType(new SequentialSelection());
        operation.setSelection(selectionOperation);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");

        Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        mutant1.getOperator().add(operator1);
        Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
        mutant2.getOperator().add(operator2);
        Mutant mutant3 = new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest());
        mutant3.getOperator().add(operator1);
        Mutant mutant4 = new Mutant("Mutant4", null, IntegrationFacade.getProgramUnderTest());
        mutant4.getOperator().add(operator2);

        solution.getMutants().add(mutant1);
        solution.getMutants().add(mutant2);
        solution.getMutants().add(mutant3);
        solution.getMutants().add(mutant4);

        operation.doOperation(solution);
        assertEquals(6, solution.getMutants().size());
    }

    @Test
    public void getAndSetGeneration() throws Exception {
        CombineMutantsOperation operation = new CombineMutantsOperation();
        ConventionalGeneration generation = new ConventionalGeneration(2);
        operation.setGeneration(generation);
        assertEquals(generation, operation.getGeneration());
    }

    @Test
    public void getAndSetSelection() throws Exception {
        CombineMutantsOperation operation = new CombineMutantsOperation();
        SelectionOperation<Mutant> selection = new SelectionOperation<>();
        operation.setSelection(selection);
        assertEquals(selection, operation.getSelection());
    }

    @Test
    public void isSpecific() throws Exception {
        CombineMutantsOperation operation = new CombineMutantsOperation();
        SelectionOperation<Mutant> selection = new SelectionOperation<>();
        operation.setSelection(selection);
        ConventionalGeneration generation = new ConventionalGeneration(2);
        operation.setGeneration(generation);
        assertFalse(operation.isSpecific());
    }

}
