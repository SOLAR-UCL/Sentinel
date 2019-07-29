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
     */
    public static final String SCORE_TIME_RATIO = "SCORE_TIME_RATIO";

    /**
     *
     */
    public static final String SCORE = "SCORE";

    /**
     *
     */
    public static final String TIME = "TIME";

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
            case SCORE_TIME_RATIO:
                return new ScoreTimeRatio();
            case SCORE:
                return new ScoreIndicator();
            case TIME:
                return new TimeIndicator();
            default:
                throw new IllegalArgumentException("Unknown or unsuported indicator: " + indicatorName);
        }
    }

}
