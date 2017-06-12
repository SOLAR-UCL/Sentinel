package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import java.util.function.Function;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Giovani Guizzo
 */
public class GroupOperatorsByMutantQuantityTest {

    public GroupOperatorsByMutantQuantityTest() {
    }

    @Test
    public void testCreateGrouperFunction() {
        GroupOperatorsByMutantQuantity operation = new GroupOperatorsByMutantQuantity();

        Operator operator1 = new Operator("Operator1", "Type1");
        operator1.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));
        Operator operator2 = new Operator("Operator2", "Type2");
        operator2.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", "Program/path")));
        operator2.getGeneratedMutants().add(new Mutant("Mutant2", null, new Program("Program1", "Program/path")));
        Operator operator3 = new Operator("Operator3", "Type1");

        Function<Operator, Integer> grouperFunction = operation.createGroupingFunction();
        assertEquals(1, (int) grouperFunction.apply(operator1));
        assertEquals(2, (int) grouperFunction.apply(operator2));
        assertEquals(0, (int) grouperFunction.apply(operator3));
    }

    @Test
    public void testIsSpecific() {
        GroupOperatorsByMutantQuantity operation = new GroupOperatorsByMutantQuantity();
        assertFalse(operation.isSpecific());
    }
}
