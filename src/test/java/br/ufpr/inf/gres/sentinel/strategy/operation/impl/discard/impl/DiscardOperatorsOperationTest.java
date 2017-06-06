package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard.AbstractDiscardOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import java.io.File;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Giovani Guizzo
 */
public class DiscardOperatorsOperationTest {

    public DiscardOperatorsOperationTest() {
    }

    @Test
    public void testConstructor() {
        SelectionOperation<Operator> selectionOperation = new SelectionOperation<>();
        DiscardOperatorsOperation operation = new DiscardOperatorsOperation(selectionOperation);
        assertEquals(selectionOperation, operation.getSelection());
    }

    @Test
    public void testDoOperation() {
        Program program = new Program("Program1", new File("Program1"));

        Mutant mutant1 = new Mutant("Mutant1", new File("Mutant1"), program);
        Mutant mutant2 = new Mutant("Mutant2", new File("Mutant2"), program);
        Mutant mutant3 = new Mutant("Mutant3", new File("Mutant3"), program);
        Mutant mutant4 = new Mutant("Mutant4", new File("Mutant4"), program);
        Mutant mutant5 = new Mutant("Mutant5", new File("Mutant5"), program);

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(mutant1);
        operator1.getGeneratedMutants().add(mutant2);
        operator1.getGeneratedMutants().add(mutant3);
        Operator operator2 = new Operator("Operator2", "Type2");
        operator2.getGeneratedMutants().add(mutant4);
        operator2.getGeneratedMutants().add(mutant5);

        mutant1.getOperator().add(operator1);
        mutant2.getOperator().add(operator1);
        mutant3.getOperator().add(operator1);
        mutant4.getOperator().add(operator2);
        mutant5.getOperator().add(operator2);

        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setQuantity(1);

        AbstractDiscardOperation operation = new DiscardOperatorsOperation(selectionOp);
        operation.setSelection(selectionOp);

        Solution solution = new Solution();
        solution.getOperators().add(operator1);
        solution.getOperators().add(operator2);
        solution.getMutants().add(mutant1);
        solution.getMutants().add(mutant2);
        solution.getMutants().add(mutant3);
        solution.getMutants().add(mutant4);
        solution.getMutants().add(mutant5);

        operation.doOperation(solution);

        assertEquals(1, solution.getOperators().size());
        assertEquals(operator2, solution.getOperators().get(0));
        assertEquals(2, solution.getMutants().size());
        assertEquals(mutant4, solution.getMutants().get(0));
        assertEquals(mutant5, solution.getMutants().get(1));
    }

    @Test
    public void testObtainList() {
        Solution solution = new Solution();
        DiscardOperatorsOperation operation = new DiscardOperatorsOperation();
        assertEquals(solution.getOperators(), operation.obtainList(solution));
    }

}
