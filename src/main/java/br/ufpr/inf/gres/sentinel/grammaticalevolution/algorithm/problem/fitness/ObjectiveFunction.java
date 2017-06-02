package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness;

import java.util.Objects;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 * @param <T>
 */
public abstract class ObjectiveFunction<T> {

    /**
     *
     */
    public static final String AVERAGE_CPU_TIME = "averagecputime";

    /**
     *
     */
    public static final String AVERAGE_QUANTITY = "averagequantity";

    /**
     *
     */
    public static final String AVERAGE_SCORE = "averagescore";

    /**
     *
     * @param solution
     * @return
     */
    public abstract Double computeFitness(Solution<T> solution);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final ObjectiveFunction<?> other = (ObjectiveFunction<?>) obj;
        return Objects.equals(this.getName(), other.getName());
    }

    /**
     *
     * @return
     */
    public abstract String getName();

    /**
     *
     * @return
     */
    public Double getWorstValue() {
        return Double.MAX_VALUE;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getName());
    }

}
