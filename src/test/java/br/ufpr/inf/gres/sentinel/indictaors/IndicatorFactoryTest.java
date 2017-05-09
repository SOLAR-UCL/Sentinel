package br.ufpr.inf.gres.sentinel.indictaors;

import org.junit.Assert;
import org.junit.Test;
import org.uma.jmetal.qualityindicator.impl.GenericIndicator;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistance;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.util.front.imp.ArrayFront;

/**
 *
 * @author Giovani Guizzo
 */
public class IndicatorFactoryTest {

    public IndicatorFactoryTest() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateIndicator() {
        GenericIndicator indicator = IndicatorFactory.createIndicator(IndicatorFactory.IGD, new ArrayFront());
        Assert.assertNotNull(indicator);
        Assert.assertTrue(indicator instanceof InvertedGenerationalDistance);

        indicator = IndicatorFactory.createIndicator(IndicatorFactory.HYPERVOLUME, new ArrayFront());
        Assert.assertNotNull(indicator);
        Assert.assertTrue(indicator instanceof PISAHypervolume);

        IndicatorFactory.createIndicator("Unknown", new ArrayFront());
    }

}
