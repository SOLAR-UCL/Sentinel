package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import java.util.function.Function;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class GroupOperatorsByTypeTest {

    public GroupOperatorsByTypeTest() {
    }

    @Test
    public void testCreateGrouperFunction() {
        GroupOperatorsByType operation = new GroupOperatorsByType();

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");

        Function<Operator, String> grouperFunction = operation.createGrouperFunction();
        assertEquals("Type1", grouperFunction.apply(operator1));
        assertEquals("Type1", grouperFunction.apply(operator2));
        assertEquals("Type2", grouperFunction.apply(operator3));
        assertEquals("Type3", grouperFunction.apply(operator4));
    }

}
