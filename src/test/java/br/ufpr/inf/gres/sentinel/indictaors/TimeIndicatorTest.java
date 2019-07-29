/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.indictaors;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.point.util.PointSolution;

/**
 *
 * @author ucacggu
 */
public class TimeIndicatorTest {
    
    public TimeIndicatorTest() {
    }

    @Test
    public void testIsTheLowerTheIndicatorValueTheBetter() {
        TimeIndicator instance = new TimeIndicator();
        assertEquals(true, instance.isTheLowerTheIndicatorValueTheBetter());
    }

    /**
     * Test of evaluate method, of class TimeIndicator.
     */
    @Test
    public void testEvaluate() {
        TimeIndicator instance = new TimeIndicator();

        List<Solution> solutionList = new ArrayList<>();

        Solution solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 0.5);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.5);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 0.5);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.5);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.5);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.25);
        solutionList.add(solution);

        assertEquals(0.4375, instance.evaluate(solutionList), 0.01);
    }
    
    @Test
    public void testEvaluate2() {
        TimeIndicator instance = new TimeIndicator();

        List<Solution> solutionList = new ArrayList<>();

        Solution solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 1);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 1);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.25);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.5);
        solutionList.add(solution);

        assertEquals(0.6875, instance.evaluate(solutionList), 0.01);
    }
    
    @Test
    public void testEvaluate3() {
        TimeIndicator instance = new TimeIndicator();

        List<Solution> solutionList = new ArrayList<>();

        assertEquals(Double.NaN, instance.evaluate(solutionList), 0.01);
    }
    
    @Test
    public void testEvaluate4() {
        TimeIndicator instance = new TimeIndicator();

        List<Solution> solutionList = new ArrayList<>();

        Solution solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 1);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 1);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.25);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.5);
        solutionList.add(solution);
        
        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 12314);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.7165273);
        solutionList.add(solution);
        
        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 0.7165273);
        solution.setObjective(TimeIndicator.TIME_INDEX, 24524);
        solutionList.add(solution);
        
        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 0.0);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.7165273);
        solutionList.add(solution);
        
        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, 0.7165273);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.0);
        solutionList.add(solution);

        assertEquals(0.6875, instance.evaluate(solutionList), 0.01);
    }
    
    /**
     * Test of evaluate method, of class TimeIndicator.
     */
    @Test
    public void testEvaluate5() {
        TimeIndicator instance = new TimeIndicator();

        List<Solution> solutionList = new ArrayList<>();

        Solution solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, -0.5);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.5);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, -0.5);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.5);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, -1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.5);
        solutionList.add(solution);

        solution = new PointSolution(2);
        solution.setObjective(ScoreIndicator.SCORE_INDEX, -1);
        solution.setObjective(TimeIndicator.TIME_INDEX, 0.25);
        solutionList.add(solution);

        assertEquals(0.4375, instance.evaluate(solutionList), 0.01);
    }
    
}
