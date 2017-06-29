package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator.GroupOperatorsByMutantQuantity;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator.GroupOperatorsByType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.QuantityInGroupComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.OperatorTypeComparator;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
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
        groupOp.setGroupingFunction(new GroupOperatorsByType());
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new QuantityInGroupComparator());
        groupOp.setQuantity(1);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        Collection<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4), null);
        assertEquals(1, result.size());
        assertEquals(operator3, result.iterator().next());
    }

    @Test
    public void testDoOperation2() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupOperatorsByType());
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new QuantityInGroupComparator());
        groupOp.setQuantity(2);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        Collection<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4), null);
        assertEquals(2, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator3, iterator.next());
        assertEquals(operator4, iterator.next());
    }

    @Test
    public void testDoOperation3() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupOperatorsByType());
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new QuantityInGroupComparator());
        groupOp.setQuantity(3);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        Collection<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4), null);
        assertEquals(3, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator3, iterator.next());
        assertEquals(operator4, iterator.next());
        assertEquals(operator1, iterator.next());
    }

    @Test
    public void testDoOperation4() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(2);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupOperatorsByType());
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new QuantityInGroupComparator());
        groupOp.setQuantity(3);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        Collection<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4), null);
        assertEquals(4, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator3, iterator.next());
        assertEquals(operator4, iterator.next());
        assertEquals(operator1, iterator.next());
        assertEquals(operator2, iterator.next());
    }

    @Test
    public void testDoOperation5() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(2);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupOperatorsByType());
        groupOp.setSelectionType(new SequentialSelection());
        QuantityInGroupComparator sorter = new QuantityInGroupComparator();
        sorter.setReversed(true);
        groupOp.setSorter(sorter);
        groupOp.setQuantity(2);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        Collection<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4), null);
        assertEquals(3, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator1, iterator.next());
        assertEquals(operator2, iterator.next());
        assertEquals(operator3, iterator.next());
    }

    @Test
    public void testDoOperation6() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp2 = new GroupSelectionOperation<>();
        groupOp2.setSelectionOperation(selectionOp);
        groupOp2.setGroupingFunction(new GroupOperatorsByType());
        groupOp2.setSelectionType(new SequentialSelection());
        groupOp2.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(groupOp2);
        groupOp.setGroupingFunction(new GroupOperatorsByMutantQuantity());
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setQuantity(3);

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));
        operator1.getGeneratedMutants().add(new Mutant("Mutant2", null, new Program("Program1", "Program/path")));
        Operator operator2 = new Operator("Operator2", "Type2");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));
        operator2.getGeneratedMutants().add(new Mutant("Mutant2", null, new Program("Program1", "Program/path")));

        Operator operator3 = new Operator("Operator3", "Type1");
        Operator operator4 = new Operator("Operator4", "Type1");

        Collection<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4), null);
        assertEquals(2, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator3, iterator.next());
        assertEquals(operator2, iterator.next());
    }

    @Test
    public void testDoOperation7() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp2 = new GroupSelectionOperation<>();
        groupOp2.setSelectionOperation(selectionOp);
        groupOp2.setGroupingFunction(new GroupOperatorsByType());
        groupOp2.setSelectionType(new SequentialSelection());
        groupOp2.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(groupOp2);
        groupOp.setGroupingFunction(new GroupOperatorsByMutantQuantity());
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setQuantity(3);

        List<Operator> group1 = new ArrayList<>();

        Collection<Operator> result = groupOp.doOperation(group1, null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIsSpecific() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupOperatorsByType());
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new QuantityInGroupComparator());
        groupOp.setPercentage(1.0);

        assertFalse(groupOp.isSpecific());
    }

    @Test
    public void testPercentage() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(1);

        GroupSelectionOperation<Operator> groupOp = new GroupSelectionOperation<>();
        groupOp.setSelectionOperation(selectionOp);
        groupOp.setGroupingFunction(new GroupOperatorsByType());
        groupOp.setSelectionType(new SequentialSelection());
        groupOp.setSorter(new QuantityInGroupComparator());
        groupOp.setPercentage(1.0);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        Collection<Operator> result = groupOp.doOperation(Lists.newArrayList(operator1, operator2, operator3, operator4), null);
        assertEquals(3, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator3, iterator.next());
        assertEquals(operator4, iterator.next());
        assertEquals(operator1, iterator.next());
    }

}
