/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.indictaors;

import java.util.List;
import org.uma.jmetal.qualityindicator.impl.GenericIndicator;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author ucacggu
 * @param <S>
 */
public class ScoreIndicator<S extends Solution<?>> extends GenericIndicator<S> {

    protected static final int SCORE_INDEX = 1;
    
    public ScoreIndicator() {
    }

    @Override
    public boolean isTheLowerTheIndicatorValueTheBetter() {
        return false;
    }

    @Override
    public Double evaluate(List<S> solutionList) {
        return solutionList.stream()
                .filter(solution
                        -> solution.getObjective(TimeIndicator.TIME_INDEX) > 0.0 && solution.getObjective(TimeIndicator.TIME_INDEX) <= 1.0
                && Math.abs(solution.getObjective(ScoreIndicator.SCORE_INDEX)) > 0.0 && Math.abs(solution.getObjective(ScoreIndicator.SCORE_INDEX)) <= 1.0)
                .mapToDouble(solution -> Math.abs(solution.getObjective(SCORE_INDEX)))
                .average()
                .orElse(Double.NaN);
    }
    
}
