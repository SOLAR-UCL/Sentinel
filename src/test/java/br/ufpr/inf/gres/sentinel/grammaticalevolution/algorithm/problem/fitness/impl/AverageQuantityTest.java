package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import org.junit.Test;
import org.uma.jmetal.util.point.util.PointSolution;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Giovani Guizzo
 */
public class AverageQuantityTest {

    public AverageQuantityTest() {
    }

    @Test
    public void testGetName() {
        assertEquals(ObjectiveFunction.AVERAGE_QUANTITY, new AverageQuantity().getName());
    }

    @Test
    public void testCompute() {
        PointSolution pointSolution = new PointSolution(1);
    }

}
