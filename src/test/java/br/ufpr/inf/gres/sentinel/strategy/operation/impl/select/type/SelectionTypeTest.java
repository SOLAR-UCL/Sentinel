package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.RandomSelection;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Giovani Guizzo
 */
public class SelectionTypeTest {

    public SelectionTypeTest() {
    }

    @Test
    public void testDoOperation() {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        RandomSelection<Integer> operation = new RandomSelection<>();
        Collection<Integer> result = operation.doOperation(input, null);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testDoOperation2() {
        List<Integer> input = new ArrayList<>();
        RandomSelection<Integer> operation = new RandomSelection<>();
        Collection<Integer> result = operation.doOperation(input, null);
        assertTrue(result.isEmpty());
    }

}
