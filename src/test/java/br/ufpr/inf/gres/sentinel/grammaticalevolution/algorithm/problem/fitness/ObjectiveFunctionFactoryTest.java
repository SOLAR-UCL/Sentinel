package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness;

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Giovani Guizzo
 */
public class ObjectiveFunctionFactoryTest {

    public ObjectiveFunctionFactoryTest() {
    }

    @Test
    public void testCreateFunctions() {
        List<ObjectiveFunction> functions = ObjectiveFunctionFactory.createObjectiveFunctions(Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_QUANTITY, ObjectiveFunction.AVERAGE_SCORE));
        assertEquals(ObjectiveFunction.AVERAGE_CPU_TIME, functions.get(0).getName());
        assertEquals(ObjectiveFunction.AVERAGE_QUANTITY, functions.get(1).getName());
        assertEquals(ObjectiveFunction.AVERAGE_SCORE, functions.get(2).getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFunctions2() {
        ObjectiveFunction function = ObjectiveFunctionFactory.createObjectiveFunction("unknown");
    }

}
