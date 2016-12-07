package br.ufpr.inf.gres.sentinel.strategy.operation.impl.discard;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.impl.SelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.OperatorTypeComparator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class DiscardOperatorsOperationTest {

    public DiscardOperatorsOperationTest() {
    }

    @Test
    public void testDoOperation() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setQuantity(1);

        DiscardOperatorsOperation operation = new DiscardOperatorsOperation();
        operation.setSelection(selectionOp);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type3");
        Operator operator4 = new Operator("Operator4", "Type4");

        Solution solution = new Solution();
        solution.getOperators().add(operator1);
        solution.getOperators().add(operator2);
        solution.getOperators().add(operator3);
        solution.getOperators().add(operator4);

        operation.doOperation(solution);

        assertEquals(3, solution.getOperators().size());
        assertEquals(operator2, solution.getOperators().get(0));
        assertEquals(operator3, solution.getOperators().get(1));
        assertEquals(operator4, solution.getOperators().get(2));
    }

    @Test
    public void testDoOperation2() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setQuantity(6);

        DiscardOperatorsOperation operation = new DiscardOperatorsOperation();
        operation.setSelection(selectionOp);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type3");
        Operator operator4 = new Operator("Operator4", "Type4");

        Solution solution = new Solution();
        solution.getOperators().add(operator1);
        solution.getOperators().add(operator2);
        solution.getOperators().add(operator3);
        solution.getOperators().add(operator4);

        operation.doOperation(solution);

        assertEquals(0, solution.getOperators().size());
    }

}
