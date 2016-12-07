package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.GroupingFunction;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.OperatorQuantityInGroupComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.OperatorTypeComparator;
import com.google.common.collect.Lists;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class GroupSelectionOperationTest {

    public GroupSelectionOperationTest() {
    }

    @Test
    public void testDoOperation() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupingFunction<>("Nothing", false, Operator::getType));
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new OperatorQuantityInGroupComparator());
        groupOp.setQuantity(1);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        List<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4));
        assertEquals(1, result.size());
        assertEquals(operator3, result.get(0));
    }

    @Test
    public void testDoOperation2() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupingFunction<>("Nothing", false, Operator::getType));
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new OperatorQuantityInGroupComparator());
        groupOp.setQuantity(2);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        List<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4));
        assertEquals(2, result.size());
        assertEquals(operator3, result.get(0));
        assertEquals(operator4, result.get(1));
    }

    @Test
    public void testDoOperation3() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupingFunction<>("Nothing", false, Operator::getType));
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new OperatorQuantityInGroupComparator());
        groupOp.setQuantity(3);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        List<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4));
        assertEquals(3, result.size());
        assertEquals(operator3, result.get(0));
        assertEquals(operator4, result.get(1));
        assertEquals(operator1, result.get(2));
    }

    @Test
    public void testDoOperation4() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(2);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupingFunction<>("Nothing", false, Operator::getType));
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new OperatorQuantityInGroupComparator());
        groupOp.setQuantity(3);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        List<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4));
        assertEquals(6, result.size());
        assertEquals(operator3, result.get(0));
        assertEquals(operator3, result.get(1));
        assertEquals(operator4, result.get(2));
        assertEquals(operator4, result.get(3));
        assertEquals(operator1, result.get(4));
        assertEquals(operator2, result.get(5));
    }

    @Test
    public void testDoOperation5() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(2);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupingFunction<>("Nothing", false, Operator::getType));
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new OperatorQuantityInGroupComparator().reversed());
        groupOp.setQuantity(2);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        List<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4));
        assertEquals(4, result.size());
        assertEquals(operator1, result.get(0));
        assertEquals(operator2, result.get(1));
        assertEquals(operator3, result.get(2));
        assertEquals(operator3, result.get(3));
    }

    @Test
    public void testDoOperation6() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp2 = new GroupSelectionOperation<>();
        groupOp2.setSelectionOperation(selectionOp);
        groupOp2.setGroupingFunction(new GroupingFunction<>("Nothing", false, Operator::getType));
        groupOp2.setSelectionType(new SequentialSelection());
        groupOp2.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(groupOp2);
        groupOp.setGroupingFunction(new GroupingFunction<>("Group by Mutant Quantity", false, (Operator operator) -> operator.getGeneratedMutants().size()));
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setQuantity(3);

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, null));
        operator1.getGeneratedMutants().add(new Mutant("Mutant2", null, null));
        Operator operator2 = new Operator("Operator2", "Type2");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, null));
        operator2.getGeneratedMutants().add(new Mutant("Mutant2", null, null));

        Operator operator3 = new Operator("Operator3", "Type1");
        Operator operator4 = new Operator("Operator4", "Type1");

        List<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4));
        assertEquals(3, result.size());
        assertEquals(operator3, result.get(0));
        assertEquals(operator2, result.get(1));
        assertEquals(operator3, result.get(2));
    }

}
