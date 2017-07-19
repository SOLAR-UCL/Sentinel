package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl.AverageCPUTime;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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

    @Test
    public void testHashCode() {
        AverageCPUTime averageCPUTime = new AverageCPUTime<>();
        assertEquals((int) averageCPUTime.getName().hashCode(), (int) averageCPUTime.hashCode());
    }

}
