package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import java.util.function.Function;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GroupMutantsByOperatorTypeTest {

    @Test
    public void createGroupingFunction() throws Exception {
        GroupMutantsByOperatorType operation = new GroupMutantsByOperatorType();
        Function<Mutant, String> function = operation.createGroupingFunction();

        Mutant mutant1 = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Mutant mutant2 = new Mutant("Mutant2", null, new Program("Program1", "Program/path"));

        Operator operator1 = new Operator("Operator1", "Type1");

        mutant1.setOperator(operator1);

        mutant2.setOperator(operator1);

        assertEquals("Type1", function.apply(mutant1));
        assertEquals("Type1", function.apply(mutant2));
    }

    @Test
    public void createGroupingFunction2() throws Exception {
        GroupMutantsByOperatorType operation = new GroupMutantsByOperatorType();
        Function<Mutant, String> function = operation.createGroupingFunction();

        Mutant mutant1 = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Mutant mutant2 = new Mutant("Mutant2", null, new Program("Program1", "Program/path"));

        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type3");

        mutant1.setOperator(operator3);

        mutant2.setOperator(operator2);

        assertEquals("Type3", function.apply(mutant1));
        assertEquals("Type2", function.apply(mutant2));
    }

    @Test
    public void isSpecific() throws Exception {
        GroupMutantsByOperatorType operation = new GroupMutantsByOperatorType();
        assertFalse(operation.isSpecific());
    }

}
