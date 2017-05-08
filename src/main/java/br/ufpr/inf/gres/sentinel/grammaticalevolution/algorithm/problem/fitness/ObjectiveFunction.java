package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness;

import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 */
public interface ObjectiveFunction<T> {

    public static final String AVERAGE_QUANTITY = "averageQuantity";
    public static final String AVERAGE_SCORE = "averageScore";
    public static final String AVERAGE_CPU_TIME = "averageCPUTime";

    public String getName();

    public Double computeFitness(Solution<T> solution);

    public default Double getWorstValue() {
        return Double.MAX_VALUE;
    }

}
