package br.ufpr.inf.gres.sentinel.indictaors;

import org.uma.jmetal.qualityindicator.impl.GenericIndicator;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistance;
import org.uma.jmetal.util.front.Front;

/**
 *
 * @author Giovani Guizzo
 */
public class IndicatorFactory {

    /**
     *
     */
    public static final String HYPERVOLUME = "HYPERVOLUME";

    /**
     *
     */
    public static final String IGD = "IGD";

    /**
     *
     * @param indicatorName
     * @param referenceFront
     * @return
     */
    public static GenericIndicator createIndicator(String indicatorName, Front referenceFront) {
        switch (indicatorName.toUpperCase()) {
            case HYPERVOLUME:
                HypervolumeCalculator hypervolumeCalculator = new HypervolumeCalculator();
                hypervolumeCalculator.addParetoFront(referenceFront);
                return hypervolumeCalculator;
            case IGD:
                return new InvertedGenerationalDistance(referenceFront);
            default:
                throw new IllegalArgumentException("Unknown or unsuported indicator: " + indicatorName);
        }
    }

}
