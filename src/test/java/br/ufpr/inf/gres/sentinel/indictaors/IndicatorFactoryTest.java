package br.ufpr.inf.gres.sentinel.indictaors;

import org.junit.Assert;
import org.junit.Test;
import org.uma.jmetal.qualityindicator.impl.GenericIndicator;
import org.uma.jmetal.qualityindicator.impl.Hypervolume;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistance;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.point.impl.ArrayPoint;

/**
 *
 * @author Giovani Guizzo
 */
public class IndicatorFactoryTest {

    public IndicatorFactoryTest() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateIndicator() {
        ArrayFront arrayFront = new ArrayFront(1, 2);
        arrayFront.setPoint(0, new ArrayPoint(new double[]{1.0, 1.0}));
        
        GenericIndicator indicator = IndicatorFactory.createIndicator(IndicatorFactory.IGD, arrayFront);
        Assert.assertNotNull(indicator);
        Assert.assertTrue(indicator instanceof InvertedGenerationalDistance);

        indicator = IndicatorFactory.createIndicator(IndicatorFactory.HYPERVOLUME, arrayFront);
        Assert.assertNotNull(indicator);
        Assert.assertTrue(indicator instanceof Hypervolume);
        
        indicator = IndicatorFactory.createIndicator(IndicatorFactory.SCORE_TIME_RATIO, arrayFront);
        Assert.assertNotNull(indicator);
        Assert.assertTrue(indicator instanceof ScoreTimeRatio);
        
        indicator = IndicatorFactory.createIndicator(IndicatorFactory.SCORE, arrayFront);
        Assert.assertNotNull(indicator);
        Assert.assertTrue(indicator instanceof ScoreIndicator);
        
        indicator = IndicatorFactory.createIndicator(IndicatorFactory.TIME, arrayFront);
        Assert.assertNotNull(indicator);
        Assert.assertTrue(indicator instanceof TimeIndicator);

        IndicatorFactory.createIndicator("Unknown", arrayFront);
    }

}
