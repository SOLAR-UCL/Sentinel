package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl.AverageCPUTime;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Giovani Guizzo
 */
public class ObjectiveFunctionTest {

    public ObjectiveFunctionTest() {
    }

    @Test
    public void testEquals() {
        AverageCPUTime averageCPUTime = new AverageCPUTime<>();
        assertFalse(averageCPUTime.equals(null));
        assertFalse(averageCPUTime.equals(new Object()));
        assertTrue(averageCPUTime.equals(averageCPUTime));
    }

}
